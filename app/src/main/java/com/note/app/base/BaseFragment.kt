package com.note.app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.note.app.base.contract.UiEvent
import com.note.app.base.contract.UiAction
import com.note.app.base.contract.UiMutation
import com.note.app.base.contract.UiState
import kotlinx.coroutines.launch

abstract class BaseFragment<T : ViewBinding, Event : UiAction, Mutation : UiMutation, State : UiState, Effect : UiEvent, VM : BaseViewModel<Event, Mutation, State, Effect>> : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    abstract val viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = inflateViewBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateViewBinding(inflater: LayoutInflater, container: ViewGroup?): T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeState()
        observeEffect()
    }

    abstract fun initView()

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    renderState(state)
                }
            }
        }
    }

    private fun observeEffect() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiEffect.collect { effect ->
                    handleEffect(effect)
                }
            }
        }
    }

    abstract fun renderState(state: State)

    abstract fun handleEffect(effect: Effect)

    fun sendEvent(event: Event) {
        viewModel.sendEvent(event)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}