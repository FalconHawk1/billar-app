package co.hitech.billar_app.data.model

import androidx.compose.ui.graphics.Color

/**
 * Data class representing a billiard player
 * @param id Unique identifier for the player
 * @param name Player name/identifier
 * @param score Current score
 * @param color Background color for the player card
 */
data class Player(
    val id: String,
    val name: String,
    val score: Int = 0,
    val color: Color = Color.White
)
