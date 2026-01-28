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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.hitech.billar_app.ui.theme.TableGreen
import co.hitech.billar_app.ui.theme.BackgroundLight
import co.hitech.billar_app.ui.theme.CardBackground
import co.hitech.billar_app.ui.theme.WoodBrown
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
            .background(BackgroundLight)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Title
            Text(
                text = "PROBILLAR MANAGER",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = TableGreen,
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
                    color = WoodBrown
                )

                // 2 Players button
                PlayerSelectionButton(
                    text = "2 JUGADORES",
                    isSelected = selectedPlayerCount == 2,
                    ballDrawables = listOf("ball_1", "ball_2"),
                    onClick = { selectedPlayerCount = 2 }
                )

                // 3-4 Players button
                PlayerSelectionButton(
                    text = "3 O 4 JUGADORES",
                    isSelected = selectedPlayerCount in 3..4,
                    ballDrawables = listOf("ball_3", "ball_4"),
                    onClick = {
                        dialogPlayerRange = 3..4
                        showPlayerDialog = true
                    }
                )

                // 5-8 Players button
                PlayerSelectionButton(
                    text = "5 A 8 JUGADORES",
                    isSelected = selectedPlayerCount in 5..8,
                    ballDrawables = listOf("ball_5", "ball_8"),
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
                    color = WoodBrown
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
                            .background(WoodBrown)
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
                            .background(CardBackground)
                            .border(2.dp, TableGreen, RoundedCornerShape(12.dp))
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$delayTime SEG",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = TableGreen
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
                            .background(WoodBrown)
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
                    containerColor = TableGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "INICIAR",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
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
    ballDrawables: List<String>,
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
                        width = 3.dp,
                        color = TableGreen,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) CardBackground else CardBackground.copy(alpha = 0.8f)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        )
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
                color = if (isSelected) TableGreen else WoodBrown
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ballDrawables.forEach { drawableName ->
                    androidx.compose.foundation.Image(
                        painter = painterResource(
                            id = androidx.compose.ui.platform.LocalContext.current.resources.getIdentifier(
                                drawableName,
                                "drawable",
                                androidx.compose.ui.platform.LocalContext.current.packageName
                            )
                        ),
                        contentDescription = drawableName,
                        modifier = Modifier.size(32.dp)
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
                color = TableGreen,
                fontWeight = FontWeight.Bold
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
                            containerColor = TableGreen
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "$count jugadores",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar", color = WoodBrown, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = CardBackground,
        shape = RoundedCornerShape(16.dp)
    )
}
