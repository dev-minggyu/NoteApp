package com.note.feature.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

/**
 * Flow<Action>을 StateFlow<State>로 변환하는 MVI 확장 함수.
 *
 * Action을 받아 Processor를 통해 Mutation을 생성하고, Reducer로 State를 갱신해서 StateFlow로 반환한다.
 *
 * ## Action 타입
 * - **일반 Action** : 사용자 클릭, 버튼 이벤트 등 (flatMapLatest 처리), 새로운 Action이 오면 이전 작업 취소
 *
 * - **Stream Action** : DB 관찰, 실시간 데이터 등 (merge 처리), 지속적으로 데이터를 emit하며 취소되지 않음
 *
 * ## 동작방식
 * 1. **eventFlow** : 일반 Action을 `flatMapLatest`로 처리 (최신 요청만 처리)
 * 2. **streamFlow** : Stream Action을 `merge`로 처리 (모든 이벤트 유지)
 * 3. 두 Flow를 병합하여 `runningFold`로 State를 누적 업데이트
 *
 * ## 사용 예시
 * ```kotlin
 * val state: StateFlow<MainState> = uiAction
 *     .reduceToState(
 *         initialState = MainState()
 *         streamIntents = setOf(MainContract.Action.Stream.ObserveNote)
 *         processor = ::processAction,
 *         reducer = ::reduceMutation,
 *         scope = viewModelScope,
 *     )
 * ```
 *
 * ## 주의사항
 * - Stream Action의 Processor Flow는 앱 생명주기 동안 계속 실행!
 *
 * @param State UI 상태 타입
 * @param Action 사용자 의도/이벤트 타입
 * @param Mutation 상태 변경 명령 타입
 *
 * @param initialState 초기 상태값
 * @param streamIntents 지속적으로 관찰할 Stream Action Set
 * @param processor Action을 받아 Mutation Flow를 생성하는 함수
 * @param reducer 현재 State와 Mutation을 받아 새로운 State를 생성하는 순수 함수
 * @param scope StateFlow의 생명주기를 관리할 CoroutineScope
 * @param started StateFlow의 시작/중지 전략 (기본 : 구독 종료 5초 후 중지)
 *
 * @return 상태를 관리하는 StateFlow
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <State, Action, Mutation> Flow<Action>.reduceToState(
    initialState: State,
    streamIntents: Set<Action> = emptySet(),
    processor: (Action) -> Flow<Mutation>,
    reducer: (State, Mutation) -> State,
    scope: CoroutineScope,
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<State> {

    // 일반 Processor Action의 경우 flatMapLatest 처리한 eventFlow 생성
    val eventFlow = this
        .filter { intent -> intent !in streamIntents }
        .flatMapLatest { intent ->
            processor(intent)
        }

    // Processor의 동작 형태가 Stream인 Action의 경우 eventFlow와 별도의 streamFlow 생성
    val streamFlow = if (streamIntents.isEmpty()) {
        emptyFlow()
    } else {
        merge(
            *streamIntents.map { intent ->
                processor(intent)
            }.toTypedArray()
        )
    }

    return merge(streamFlow, eventFlow)
        .runningFold(initialState, reducer)
        .stateIn(scope, started, initialState)
}