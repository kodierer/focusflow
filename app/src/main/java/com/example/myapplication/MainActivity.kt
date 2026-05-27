package com.example.myapplication

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.screens.FocusTimerScreen
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel = TimerViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Keep screen awake intelligently during focus sessions (huge productivity win)
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.isRunning && state.isWorkSession) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }

        setContent {
            MyApplicationTheme {
                FocusTimerScreen(viewModel)
            }
        }
    }
}
