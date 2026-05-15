package com.example.myapplication.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(onBackClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Einstellungen",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E88E5)
                )
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.Info,
                        contentDescription = "Zurück",
                        tint = Color.Gray
                    )
                }
            }

            // General Settings
            SettingsCategoryHeader("Allgemein")

            SettingToggleItem(
                label = "Benachrichtigungen",
                description = "Bei Session-Ende",
                isEnabled = true,
                onToggle = {}
            )

            SettingToggleItem(
                label = "Vibration",
                description = "Haptisches Feedback",
                isEnabled = true,
                onToggle = {}
            )

            SettingToggleItem(
                label = "Sounds",
                description = "Alarme und Pieptöne",
                isEnabled = true,
                onToggle = {}
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Display Settings
            SettingsCategoryHeader("Anzeige")

            SettingToggleItem(
                label = "Dark Mode",
                description = "Dunkles Design",
                isEnabled = false,
                onToggle = {}
            )

            SettingToggleItem(
                label = "Keep Screen On",
                description = "Bildschirm bleibt an",
                isEnabled = true,
                onToggle = {}
            )

            Divider(modifier = Modifier.padding(vertical = 16.dp))

            // Timer Settings
            SettingsCategoryHeader("Timer-Standard")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SettingSliderItem(
                    label = "Standard Arbeitsdauer",
                    value = 25,
                    min = 10,
                    max = 60,
                    unit = "min"
                )

                SettingSliderItem(
                    label = "Standard Pausendauer",
                    value = 5,
                    min = 1,
                    max = 30,
                    unit = "min"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // About Section
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFF1E88E5).copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "FocusFlow",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E88E5)
                    )
                    Text(
                        text = "Version 1.0.0",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Steigere deine Produktivität mit der Pomodoro-Technik",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Light
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Delete Data Button
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text(
                    "Alle Daten zurücksetzen",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SettingsCategoryHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1E88E5),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun SettingToggleItem(
    label: String,
    description: String,
    isEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Switch(
                checked = isEnabled,
                onCheckedChange = onToggle,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}

@Composable
fun SettingSliderItem(
    label: String,
    value: Int,
    min: Int,
    max: Int,
    unit: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
            Text(
                text = "$value $unit",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E88E5)
            )
        }

        Slider(
            value = value.toFloat(),
            onValueChange = {},
            valueRange = min.toFloat()..max.toFloat(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}


