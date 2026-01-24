package co.hitech.billar_app.utils

import androidx.compose.ui.graphics.Color
import co.hitech.billar_app.utils.Constants.PLAYER_1_COLOR
import co.hitech.billar_app.utils.Constants.PLAYER_2_COLOR
import co.hitech.billar_app.utils.Constants.PLAYER_3_COLOR
import co.hitech.billar_app.utils.Constants.PLAYER_4_COLOR
import co.hitech.billar_app.utils.Constants.PLAYER_5_COLOR
import co.hitech.billar_app.utils.Constants.PLAYER_6_COLOR

/**
 * Extension functions and utility helpers
 */

/**
 * Get player color by index
 */
fun getPlayerColor(index: Int): Color {
    return when (index) {
        0 -> Color(PLAYER_1_COLOR)
        1 -> Color(PLAYER_2_COLOR)
        2 -> Color(PLAYER_3_COLOR)
        3 -> Color(PLAYER_4_COLOR)
        4 -> Color(PLAYER_5_COLOR)
        5 -> Color(PLAYER_6_COLOR)
        else -> Color(PLAYER_1_COLOR)
    }
}

/**
 * Format currency value
 */
fun Double.formatCurrency(): String {
    return String.format("$%.2f", this)
}

/**
 * Format elapsed time in milliseconds to HH:MM format
 */
fun Long.formatElapsedTime(): String {
    val totalSeconds = this / 1000
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    return String.format("%02d:%02d", hours, minutes)
}
