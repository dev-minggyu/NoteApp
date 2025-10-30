package com.note.app.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState
import kotlinx.coroutines.launch

abstract class BaseActivity<T : ViewBinding, Action : UiAction, Mutation : UiMutation, State : UiState, Event : UiEvent, VM : BaseViewModel<State, Action, Event, Mutation>> :
    AppCompatActivity() {
    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateViewBinding()
        setContentView(binding.root)
        initView()
        observeState()
        observeEvent()
    }

    abstract fun inflateViewBinding(): T

    abstract fun initView()

    private fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun observeEvent() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEvent.collect { event ->
                    handleEvent(event)
                }
            }
        }
    }

    abstract fun renderState(state: State)

    abstract fun handleEvent(event: Event)

    fun sendEvent(action: Action) {
        viewModel.sendAction(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}