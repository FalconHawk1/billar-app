package co.hitech.billar_app.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class for time formatting
 */
object TimeFormatter {
    
    /**
     * Format time in HH:MM format
     */
    fun formatTime(hours: Int, minutes: Int): String {
        return String.format("%02d:%02d", hours, minutes)
    }
    
    /**
     * Format elapsed time from milliseconds
     */
    fun formatElapsedTime(milliseconds: Long): String {
        val totalSeconds = milliseconds / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        return formatTime(hours.toInt(), minutes.toInt())
    }
    
    /**
     * Format timestamp to readable string
     */
    fun formatTimestamp(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(Date(timestamp))
    }
    
    /**
     * Get current time in HH:MM format
     */
    fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        return formatTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE))
    }
}
