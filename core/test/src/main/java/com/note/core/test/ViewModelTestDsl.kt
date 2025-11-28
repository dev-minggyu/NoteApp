package com.note.core.test

import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.note.feature.common.ui.base.BaseViewModel
import com.note.feature.common.ui.base.contract.UiAction
import com.note.feature.common.ui.base.contract.UiEvent
import com.note.feature.common.ui.base.contract.UiMutation
import com.note.feature.common.ui.base.contract.UiState
import kotlinx.coroutines.test.runTest

/**
 * BaseViewModel 테스트 DSL
 */
inline fun <reified State : UiState, reified Action : UiAction, reified Event : UiEvent, reified Mutation : UiMutation> testViewModel(
    noinline createViewModel: () -> BaseViewModel<State, Action, Event, Mutation>,
    block: ViewModelTestBuilder<State, Action, Event, Mutation>.() -> Unit
) {
    ViewModelTestBuilder<State, Action, Event, Mutation>()
        .apply(block)
        .execute(createViewModel)
}

class ViewModelTestBuilder<State : UiState, Action : UiAction, Event : UiEvent, Mutation : UiMutation> {
    private var givenBlock: (suspend GivenScope.() -> Unit)? = null
    private var whenBlock: (suspend WhenScope<Action>.() -> Unit)? = null
    private var thenBlock: (suspend ThenScope<State, Action, Event>.() -> Unit)? = null

    fun givenBlock(block: suspend GivenScope.() -> Unit) {
        givenBlock = block
    }

    fun whenBlock(block: suspend WhenScope<Action>.() -> Unit) {
        whenBlock = block
    }

    fun thenBlock(block: suspend ThenScope<State, Action, Event>.() -> Unit) {
        thenBlock = block
    }

    fun execute(
        createViewModel: () -> BaseViewModel<State, Action, Event, Mutation>
    ) = runTest {
        val givenScope = GivenScope()

        // Given
        givenBlock?.invoke(givenScope)

        // ViewModel 생성 (Given 이후)
        val viewModel = createViewModel()

        val whenScope = WhenScope(viewModel)
        val thenScope = ThenScope(viewModel)

        // When & Then
        viewModel.uiState.test {
            val initialState = awaitItem() // 초기값 저장

            // When
            whenBlock?.invoke(whenScope)

            // Then
            thenScope.turbine = this
            thenScope.initialState = initialState
            thenBlock?.invoke(thenScope)

            cancelAndIgnoreRemainingEvents()
        }
    }
}

/**
 * Given 단계 - Mock 설정
 */
class GivenScope {
    suspend fun mock(block: suspend () -> Unit) {
        block()
    }
}

/**
 * When 단계 - Action 실행
 */
class WhenScope<Action : UiAction>(
    private val viewModel: BaseViewModel<*, Action, *, *>
) {
    fun action(block: BaseViewModel<*, Action, *, *>.() -> Unit) {
        viewModel.block()
    }
}

/**
 * Then 단계 - 검증
 */
class ThenScope<State : UiState, Action : UiAction, Event : UiEvent>(
    private val viewModel: BaseViewModel<State, Action, Event, *>
) {
    internal var turbine: ReceiveTurbine<State>? = null
    internal var initialState: State? = null

    /**
     * 초기 상태만 검증
     */
    suspend fun initialState(block: suspend State.() -> Unit) {
        val state = initialState ?: error("Initial state not available")
        state.block()
    }

    /**
     * 상태 검증
     */
    suspend fun state(block: suspend StateScope<State>.() -> Unit) {
        val initial = initialState ?: error("Initial state not available")
        val current = turbine?.awaitItem() ?: error("No state available")
        StateScope(initial, current).block()
    }

    /**
     * 모든 중간 상태를 소비하고 마지막 상태만 검증
     */
    suspend fun lastState(block: suspend StateScope<State>.() -> Unit) {
        val initial = initialState ?: error("Initial state not available")
        var lastState: State? = null
        while (true) {
            try {
                val state = turbine?.awaitItem() ?: break
                lastState = state
            } catch (_: Throwable) { // Turbine AssertionError = Error
                break
            }
        }
        StateScope(initial, lastState ?: error("No final state available")).block()
    }

    /**
     * 로딩 상태 검증
     */
    suspend fun loadingState(isLoading: State.() -> Boolean, block: suspend StateScope<State>.() -> Unit) {
        val initial = initialState ?: error("Initial state not available")
        val item = turbine?.awaitItem() ?: error("No state available")
        val finalItem = if (!item.isLoading()) {
            turbine?.awaitItem() ?: error("No loading state available")
        } else {
            item
        }
        StateScope(initial, finalItem).block()
    }

    /**
     * 이벤트 검증
     */
    suspend fun event(block: suspend Event.() -> Unit) {
        viewModel.uiEvent.test {
            val event = awaitItem()
            event.block()
        }
    }

    /**
     * 상태 변경이 없음을 검증
     */
    suspend fun noStateChange() {
        turbine?.expectNoEvents()
    }
}

/**
 * 상태 검증 스코프
 * - initial: 최초 초기 상태
 * - current: 현재 상태
 */
class StateScope<State : UiState>(
    val initial: State,
    val current: State
)