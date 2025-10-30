package com.note.app.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.note.app.base.BaseActivity
import com.note.app.databinding.ActivityInitBinding
import com.note.app.ui.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class InitActivity :
    BaseActivity<ActivityInitBinding, InitContract.Action, InitContract.Mutation, InitContract.State, InitContract.Event, InitViewModel>() {

    override val viewModel: InitViewModel by viewModel()

    private var isReady = false

    override fun inflateViewBinding(): ActivityInitBinding {
        return ActivityInitBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setupSplashScreen()
    }

    override fun initView() {

    }

    override fun renderState(state: InitContract.State) {
        isReady = state.isInitialized
        state.error?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun handleEvent(event: InitContract.Event) {
        when (event) {
            is InitContract.Event.NavigateToMain -> {
                navigateToMain()
            }

            is InitContract.Event.ShowError -> {
                Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupSplashScreen() {
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                return if (isReady) {
                    content.viewTreeObserver.removeOnPreDrawListener(this)
                    true
                } else {
                    false
                }
            }
        })
    }
}