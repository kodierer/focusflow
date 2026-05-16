package com.example.myapplication.ui.screens
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.TimerState
import com.example.myapplication.TimerViewModel
import kotlin.math.floor
@Composable
fun FocusTimerScreen(viewModel: TimerViewModel) {
    val state by viewModel.state.collectAsState()
    val backgroundColor by animateColorAsState(
        targetValue = if (state.isWorkSession) {
            Color(0xFF1E88E5)
        } else {
            Color(0xFF43A047)
        },
        label = "backgroundColor"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (state.isWorkSession) "FOKUS Zeit" else "Pause",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            TimerDisplay(state)
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = {
                        if (state.isRunning) viewModel.pauseTimer()
                        else viewModel.startTimer()
                    },
                    containerColor = Color.White,
                    contentColor = backgroundColor,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = if (state.isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (state.isRunning) "Pause" else "Start",
                        modifier = Modifier.size(30.dp)
                    )
                }
                FloatingActionButton(
                    onClick = { viewModel.toggleSession() },
                    containerColor = Color.White,
                    contentColor = backgroundColor,
                    modifier = Modifier.size(60.dp)
                ) {
                    Text(
                        text = if (state.isWorkSession) "☕" else "🎯",
                        fontSize = 24.sp
                    )
                }
                FloatingActionButton(
                    onClick = { viewModel.resetTimer() },
                    containerColor = Color.White,
                    contentColor = backgroundColor,
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Reset",
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            SettingsSection(state, viewModel)
            Spacer(modifier = Modifier.height(40.dp))
            StatsSection(state)
        }
    }
}
@Composable
fun TimerDisplay(state: TimerState) {
    val minutes = state.timeLeft / 60
    val seconds = state.timeLeft % 60
    Box(
        modifier = Modifier
            .size(260.dp)
            .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                color = Color.White,
                fontSize = 80.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun SettingsSection(state: TimerState, viewModel: TimerViewModel) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Einstellungen",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            SettingItem(
                label = "Arbeitsdauer",
                value = state.workMinutes.toString(),
                onIncrease = { viewModel.setWorkMinutes((state.workMinutes + 1).coerceAtMost(60)) },
                onDecrease = { viewModel.setWorkMinutes((state.workMinutes - 1).coerceAtLeast(1)) },
                isEnabled = !state.isRunning
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingItem(
                label = "Pausendauer",
                value = state.breakMinutes.toString(),
                onIncrease = { viewModel.setBreakMinutes((state.breakMinutes + 1).coerceAtMost(30)) },
                onDecrease = { viewModel.setBreakMinutes((state.breakMinutes - 1).coerceAtLeast(1)) },
                isEnabled = !state.isRunning
            )
        }
    }
}
@Composable
fun SettingItem(
    label: String,
    value: String,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    isEnabled: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.9f),
            fontSize = 14.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDecrease,
                enabled = isEnabled,
                modifier = Modifier.size(36.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.3f),
                    disabledContainerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Text("-", color = Color.White, fontSize = 18.sp)
            }
            Text(
                text = "${value} min",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.width(50.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Button(
                onClick = onIncrease,
                enabled = isEnabled,
                modifier = Modifier.size(36.dp),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.3f),
                    disabledContainerColor = Color.White.copy(alpha = 0.1f)
                )
            ) {
                Text("+", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}
@Composable
fun StatsSection(state: TimerState) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        color = Color.White.copy(alpha = 0.15f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(
                title = "Sessions",
                value = state.sessionsCompleted.toString(),
                icon = "🏆"
            )
            StatCard(
                title = "Fokus Zeit",
                value = "${state.totalFocusMinutes} min",
                icon = "⏱️"
            )
            StatCard(
                title = "Heute",
                value = "${(state.totalFocusMinutes / 60)} h",
                icon = "📊"
            )
        }
    }
}
@Composable
fun StatCard(title: String, value: String, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(80.dp)
    ) {
        Text(
            text = icon,
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        Text(
            text = title,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 11.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
