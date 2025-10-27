package com.note.app.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.note.app.base.BaseActivity
import com.note.app.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
class MainActivity :
    BaseActivity<ActivityMainBinding, MainContract.Event, MainContract.Mutation, MainContract.State, MainContract.Effect, MainViewModel>() {
    override val viewModel: MainViewModel by viewModel()

    override fun inflateViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.btnLoadData.setOnClickListener { sendEvent(MainContract.Event.LoadData) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = MainAdapter { id -> sendEvent(MainContract.Event.ClickItem(id)) }
    }

    override fun renderState(state: MainContract.State) {
        binding.progressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        (binding.recyclerView.adapter as? MainAdapter)?.submitList(state.data)
        state.error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleEffect(effect: MainContract.Effect) {
        when (effect) {
            is MainContract.Effect.ShowToast -> {
                Toast.makeText(this, effect.message, Toast.LENGTH_SHORT).show()
            }

            is MainContract.Effect.NavigateToDetail -> {
                Toast.makeText(this, "상세 화면으로 이동: ${effect.id}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}