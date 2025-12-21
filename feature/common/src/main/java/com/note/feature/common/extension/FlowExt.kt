package com.note.feature.common.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * Action Flow를 StateFlow로 변환.
 *
 * - 모든 Action은 병렬로 처리
 * - 같은 key를 가진 Action이 들어오면 이전 작업을 취소하고 새 작업 시작
 *
 * ## 사용 예시
 * ```kotlin
 * val state: StateFlow<MainState> = uiAction
 *     .reduceToState(
 *         initialState = MainState()
 *         processor = ::processAction,
 *         reducer = ::reduceMutation,
 *         scope = viewModelScope,
 *     )
 * ```
 *
 * @param initialState 초기 상태
 * @param processor Action을 Mutation Flow로 변환하는 함수
 * @param reducer State와 Mutation을 받아 새로운 State를 반환하는 함수
 * @param scope StateFlow의 생명주기를 관리할 CoroutineScope
 * @param actionKey Action의 key를 결정하는 함수 (기본: Action 타입별로 구분)
 * @param started StateFlow의 시작/중지 전략 (기본 : 구독 종료 5초 후 중지)
 *
 * @return 상태를 관리하는 StateFlow
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <State, Action : Any, Mutation> Flow<Action>.reduceToState(
    initialState: State,
    processor: (Action) -> Flow<Mutation>,
    reducer: (State, Mutation) -> State,
    scope: CoroutineScope,
    actionKey: (Action) -> Any = { it::class },
    started: SharingStarted = SharingStarted.WhileSubscribed(5_000)
): StateFlow<State> = this
    .flatMapLatestByKey(actionKey, processor)
    .runningFold(initialState, reducer)
    .stateIn(scope, started, initialState)

/**
 * 키 기반 flatMapLatest.
 * - 서로 다른 key는 병렬로 실행
 * - 같은 key가 들어오면 이전 작업을 취소하고 새 작업을 시작
 */
@OptIn(ExperimentalCoroutinesApi::class)
fun <T, R> Flow<T>.flatMapLatestByKey(
    keySelector: (T) -> Any,
    transform: (T) -> Flow<R>
): Flow<R> = channelFlow {
    val jobs = ConcurrentHashMap<Any, Job>()

    collect { value ->
        val key = keySelector(value)

        jobs[key]?.cancel()

        val job = launch {
            transform(value).collect { send(it) }
        }

        jobs[key] = job

        job.invokeOnCompletion {
            jobs.remove(key, job)
        }
    }
}