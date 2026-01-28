package co.hitech.billar_app.presentation.setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.hitech.billar_app.ui.theme.AccentPurple
import co.hitech.billar_app.ui.theme.BackgroundDark
import co.hitech.billar_app.ui.theme.CardBackground
import co.hitech.billar_app.utils.Constants

/**
 * Game setup screen where users configure number of players and delay time
 */
@Composable
fun GameSetupScreen(
    onStartGame: (playerCount: Int, delayTime: Int) -> Unit
) {
    var selectedPlayerCount by remember { mutableStateOf(Constants.DEFAULT_PLAYER_COUNT) }
    var delayTime by remember { mutableStateOf(Constants.DEFAULT_DELAY_TIME) }
    var showPlayerDialog by remember { mutableStateOf(false) }
    var dialogPlayerRange by remember { mutableStateOf(IntRange.EMPTY) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Title
            Text(
                text = "POS MANAGER\nBILLIARDS",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            // Player selection buttons
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "SELECCIONAR JUGADORES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                // 2 Players button
                PlayerSelectionButton(
                    text = "2 JUGADORES",
                    isSelected = selectedPlayerCount == 2,
                    ballColors = listOf(Color.White, Color(0xFFFFD700)),
                    onClick = { selectedPlayerCount = 2 }
                )

                // 3-4 Players button
                PlayerSelectionButton(
                    text = "3 O 4 JUGADORES",
                    isSelected = selectedPlayerCount in 3..4,
                    ballColors = listOf(
                        Color(0xFF90EE90),
                        Color(0xFFADD8E6)
                    ),
                    onClick = {
                        dialogPlayerRange = 3..4
                        showPlayerDialog = true
                    }
                )

                // 5-8 Players button
                PlayerSelectionButton(
                    text = "5 A 8 JUGADORES",
                    isSelected = selectedPlayerCount in 5..8,
                    ballColors = listOf(
                        Color(0xFFFFB6C1),
                        Color(0xFFDDA0DD),
                        Color(0xFFFF6347),
                        Color(0xFF87CEEB)
                    ),
                    onClick = {
                        dialogPlayerRange = 5..8
                        showPlayerDialog = true
                    }
                )
            }

            // Delay time control
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "RETARDO EN SEG",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Minus button
                    IconButton(
                        onClick = {
                            delayTime = (delayTime - Constants.DELAY_TIME_STEP)
                                .coerceAtLeast(Constants.MIN_DELAY_TIME)
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(CardBackground)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Disminuir",
                            tint = Color.White
                        )
                    }

                    // Time display
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(CardBackground.copy(alpha = 0.6f))
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$delayTime SEG",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    // Plus button
                    IconButton(
                        onClick = {
                            delayTime = (delayTime + Constants.DELAY_TIME_STEP)
                                .coerceAtMost(Constants.MAX_DELAY_TIME)
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(CardBackground)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Incrementar",
                            tint = Color.White
                        )
                    }
                }
            }

            // Start button
            Button(
                onClick = { onStartGame(selectedPlayerCount, delayTime) },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AccentPurple
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "INICIAR",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Player selection dialog
    if (showPlayerDialog) {
        PlayerCountDialog(
            playerRange = dialogPlayerRange,
            onDismiss = { showPlayerDialog = false },
            onSelect = { count ->
                selectedPlayerCount = count
                showPlayerDialog = false
            }
        )
    }
}

/**
 * Button for selecting player count range
 */
@Composable
private fun PlayerSelectionButton(
    text: String,
    isSelected: Boolean,
    ballColors: List<Color>,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(72.dp)
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = AccentPurple,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) CardBackground.copy(alpha = 0.8f) else CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) AccentPurple else Color.White
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ballColors.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(1.dp, Color.White.copy(alpha = 0.3f), CircleShape)
                    )
                }
            }
        }
    }
}

/**
 * Dialog for selecting specific player count
 */
@Composable
private fun PlayerCountDialog(
    playerRange: IntRange,
    onDismiss: () -> Unit,
    onSelect: (Int) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Seleccionar número de jugadores",
                color = Color.White
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                playerRange.forEach { count ->
                    Button(
                        onClick = { onSelect(count) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CardBackground
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$count jugadores",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = AccentPurple)
            }
        },
        containerColor = BackgroundDark,
        shape = RoundedCornerShape(16.dp)
    )
}
