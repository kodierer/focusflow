@file:OptIn(ExperimentalMaterial3Api::class)

package com.kodierer.focusflow.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kodierer.focusflow.TimerState
import com.kodierer.focusflow.TimerViewModel
import com.kodierer.focusflow.ui.theme.*
import kotlinx.coroutines.launch
@Composable
fun FocusTimerScreen(viewModel: TimerViewModel) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Beautiful animated background colors (premium focus palette)
    val targetBg = if (state.isWorkSession) FocusBlue else BreakTeal
    val backgroundColor by animateColorAsState(
        targetValue = targetBg,
        animationSpec = tween(600, easing = EaseInOutCubic),
        label = "backgroundColor"
    )

    // Settings sheet state
    var showSettingsSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Keep screen on during active focus sessions (great UX for productivity apps)
    LaunchedEffect(state.isRunning, state.isWorkSession) {
        // We set the flag at Activity level for simplicity; here we can trigger if needed
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .systemBarsPadding()   // respect status/navigation bars nicely
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // === Premium Header ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "FocusFlow",
                        color = TextOnColor,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = if (state.isWorkSession) "Fokus-Phase" else "Erholungsphase",
                        color = TextOnColorSecondary,
                        fontSize = 13.sp
                    )
                }
                // Settings button - opens beautiful bottom sheet
                IconButton(
                    onClick = { showSettingsSheet = true },
                    modifier = Modifier
                        .size(44.dp)
                        .background(SurfaceGlass, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Einstellungen",
                        tint = TextOnColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // === STUNNING ANIMATED PROGRESS RING ===
            Spacer(Modifier.height(12.dp))
            TimerProgressCircle(
                state = state,
                modifier = Modifier.size(280.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // === Elegant Control Bar ===
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play / Pause - primary action, slightly larger
                ControlButton(
                    icon = if (state.isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                    label = if (state.isRunning) "Pause" else "Start",
                    containerColor = Color.White,
                    contentColor = backgroundColor,
                    size = 68.dp,
                    onClick = {
                        if (state.isRunning) viewModel.pauseTimer() else viewModel.startTimer()
                    }
                )

                // Mode Switch - very clear
                ControlButton(
                    icon = if (state.isWorkSession) Icons.Filled.Coffee else Icons.Filled.EmojiObjects,
                    label = if (state.isWorkSession) "Pause" else "Fokus",
                    containerColor = Color.White.copy(alpha = 0.95f),
                    contentColor = backgroundColor,
                    size = 58.dp,
                    onClick = { viewModel.toggleSession() }
                )

                // Reset
                ControlButton(
                    icon = Icons.Filled.Refresh,
                    label = "Reset",
                    containerColor = Color.White.copy(alpha = 0.9f),
                    contentColor = backgroundColor,
                    size = 58.dp,
                    onClick = { viewModel.resetTimer() }
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            // === Today's Achievement Card (more prominent & motivating) ===
            AchievementCard(state = state)

            Spacer(modifier = Modifier.height(20.dp))

            // === Quick Duration Settings (still handy) ===
            QuickDurationCard(state = state, viewModel = viewModel)

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // === Beautiful Settings Bottom Sheet ===
    if (showSettingsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSettingsSheet = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            SettingsBottomSheetContent(
                state = state,
                viewModel = viewModel,
                onDismiss = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) showSettingsSheet = false
                    }
                }
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}
// ============================================================
// BEAUTIFUL NEW TIMER VISUALS & COMPONENTS (replacing old basic UI)
// ============================================================

@Composable
fun TimerProgressCircle(
    state: TimerState,
    modifier: Modifier = Modifier
) {
    val minutes = state.timeLeft / 60
    val seconds = state.timeLeft % 60
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    // Calculate progress (1.0 = full, 0.0 = done)
    val totalSeconds = (if (state.isWorkSession) state.workMinutes else state.breakMinutes) * 60
    val progress = if (totalSeconds > 0) state.timeLeft.toFloat() / totalSeconds else 0f

    // Subtle breathing pulse when timer is active (delight factor)
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (state.isRunning) 1.012f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val ringColor = if (state.isWorkSession) FocusBlueLight else BreakTealLight
    val trackColor = Color.White.copy(alpha = 0.22f)

    Box(
        modifier = modifier.scale(if (state.isRunning) pulseScale else 1f),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(258.dp)
                .background(Color.White.copy(alpha = 0.1f), CircleShape)
        )

        Canvas(modifier = Modifier.size(258.dp)) {
            val stroke = 20.dp.toPx()
            val d = size.minDimension - stroke
            val tl = Offset((size.width - d) / 2, (size.height - d) / 2)

            // Track
            drawArc(
                color = trackColor,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = tl,
                size = Size(d, d),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            // Progress
            drawArc(
                color = ringColor,
                startAngle = -90f,
                sweepAngle = progress * 360f,
                useCenter = false,
                topLeft = tl,
                size = Size(d, d),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = formattedTime,
                color = TextOnColor,
                fontSize = 68.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-2).sp
            )
            Text(
                text = if (state.isWorkSession) "FOKUS" else "PAUSE",
                color = TextOnColorSecondary,
                fontSize = 12.sp,
                letterSpacing = 4.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun ControlButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    containerColor: Color,
    contentColor: Color,
    size: androidx.compose.ui.unit.Dp,
    onClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable(onClick = onClick)) {
        Surface(
            onClick = onClick,
            shape = CircleShape,
            color = containerColor,
            shadowElevation = 8.dp,
            modifier = Modifier.size(size)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(icon, label, tint = contentColor, modifier = Modifier.size(size * 0.40f))
            }
        }
        Spacer(Modifier.height(5.dp))
        Text(label, color = TextOnColorSecondary, fontSize = 10.5.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun AchievementCard(state: TimerState) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        color = SurfaceGlass,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 15.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatItem(value = "${state.sessionsCompleted}", label = "Sessions", icon = Icons.Filled.EmojiEvents)
            VerticalDivider(color = TextOnColor.copy(alpha = 0.18f), modifier = Modifier.height(36.dp))
            StatItem(value = "${state.totalFocusMinutes}", label = "Minuten", icon = Icons.Filled.Timer)
            if (state.currentStreak > 1) {
                VerticalDivider(color = TextOnColor.copy(alpha = 0.18f), modifier = Modifier.height(36.dp))
                StatItem(value = "${state.currentStreak} Tage", label = "Streak 🔥", icon = Icons.Filled.Whatshot)
            } else {
                VerticalDivider(color = TextOnColor.copy(alpha = 0.18f), modifier = Modifier.height(36.dp))
                StatItem(value = "${state.totalFocusMinutes / 60}h", label = "Heute", icon = Icons.Filled.TrendingUp)
            }
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, tint = TextOnColor, modifier = Modifier.size(18.dp))
        Spacer(Modifier.height(3.dp))
        Text(value, color = TextOnColor, fontSize = 17.sp, fontWeight = FontWeight.Bold)
        Text(label, color = TextOnColorSecondary, fontSize = 10.sp)
    }
}

@Composable
fun QuickDurationCard(state: TimerState, viewModel: TimerViewModel) {
    Surface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 2.dp),
        color = SurfaceGlass,
        shape = RoundedCornerShape(18.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Schnelle Anpassung", color = TextOnColor, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(10.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DurationAdjuster("Fokus", state.workMinutes, !state.isRunning,
                    { viewModel.setWorkMinutes((state.workMinutes - 1).coerceAtLeast(5)) },
                    { viewModel.setWorkMinutes((state.workMinutes + 1).coerceAtMost(60)) })
                DurationAdjuster("Pause", state.breakMinutes, !state.isRunning,
                    { viewModel.setBreakMinutes((state.breakMinutes - 1).coerceAtLeast(1)) },
                    { viewModel.setBreakMinutes((state.breakMinutes + 1).coerceAtMost(30)) })
            }
        }
    }
}

@Composable
private fun DurationAdjuster(label: String, value: Int, enabled: Boolean, onMinus: () -> Unit, onPlus: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, color = TextOnColorSecondary, fontSize = 11.sp)
        Spacer(Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            SmallRoundButton("−", enabled, onMinus)
            Text("$value min", color = TextOnColor, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.widthIn(min = 58.dp), textAlign = TextAlign.Center)
            SmallRoundButton("+", enabled, onPlus)
        }
    }
}

@Composable
private fun SmallRoundButton(text: String, enabled: Boolean, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = onClick, enabled = enabled, modifier = Modifier.size(30.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color.White.copy(alpha = 0.18f), contentColor = TextOnColor)
    ) { Text(text, fontSize = 15.sp, fontWeight = FontWeight.Bold) }
}

// ===== Settings Bottom Sheet =====
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheetContent(state: TimerState, viewModel: TimerViewModel, onDismiss: () -> Unit) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 22.dp, vertical = 8.dp)) {
        Text("Einstellungen", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(16.dp))

        Text("Dauer anpassen", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            DurationSettingCard(
                title = "Fokus", value = state.workMinutes, unit = "min",
                onDec = { viewModel.setWorkMinutes((state.workMinutes-1).coerceAtLeast(5)) },
                onInc = { viewModel.setWorkMinutes((state.workMinutes+1).coerceAtMost(60)) },
                enabled = !state.isRunning,
                modifier = Modifier.weight(1f)
            )
            DurationSettingCard(
                title = "Pause", value = state.breakMinutes, unit = "min",
                onDec = { viewModel.setBreakMinutes((state.breakMinutes-1).coerceAtLeast(1)) },
                onInc = { viewModel.setBreakMinutes((state.breakMinutes+1).coerceAtMost(30)) },
                enabled = !state.isRunning,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(18.dp))
        HorizontalDivider()
        Spacer(Modifier.height(12.dp))

        Text("Erlebnis & Feedback", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(6.dp))

        SettingRow(Icons.Filled.Notifications, "Benachrichtigungen", "Session-Ende Alarm", true) {}
        SettingRow(Icons.Filled.Vibration, "Haptik", "Starkes Feedback bei Wechsel", true) {}

        Spacer(Modifier.height(28.dp))
        Button(onClick = onDismiss, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = FocusBlue)) {
            Text("Schließen", color = Color.White, fontWeight = FontWeight.SemiBold)
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun DurationSettingCard(
    title: String,
    value: Int,
    unit: String,
    onDec: () -> Unit,
    onInc: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = modifier
    ) {
        Column(Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("$value $unit", fontSize = 19.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                SmallRoundButton("−", enabled, onDec)
                SmallRoundButton("+", enabled, onInc)
            }
        }
    }
}

@Composable
private fun SettingRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, subtitle: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}
