package co.hitech.billar_app.utils

/**
 * Application constants
 */
object Constants {
    
    // Default values
    const val DEFAULT_TABLE_ID = "table_1"
    const val DEFAULT_PRICE_PER_MINUTE = 5.0
    const val DEFAULT_CAMERA_URL = "http://192.168.0.12:8080/video"
    const val DEFAULT_API_BASE_URL = "http://localhost:8080/"
    
    // Player colors
    const val PLAYER_1_COLOR = 0xFFFFFFFF  // White
    const val PLAYER_2_COLOR = 0xFFFFD700  // Gold/Yellow
    const val PLAYER_3_COLOR = 0xFF90EE90  // Light Green
    const val PLAYER_4_COLOR = 0xFFADD8E6  // Light Blue
    const val PLAYER_5_COLOR = 0xFFFFB6C1  // Light Pink
    const val PLAYER_6_COLOR = 0xFFDDA0DD  // Plum
    const val PLAYER_7_COLOR = 0xFFFF6347  // Tomato
    const val PLAYER_8_COLOR = 0xFF87CEEB  // Sky Blue
    
    // Score button colors
    const val SCORE_MINUS_COLOR = 0xFFEF5350  // Red
    const val SCORE_PLUS_1_COLOR = 0xFF66BB6A  // Green
    const val SCORE_PLUS_5_COLOR = 0xFF42A5F5  // Blue
    
    // Theme colors
    const val BACKGROUND_DARK = 0xFF1a1f2e
    const val CARD_BACKGROUND = 0xFF2a2f3e
    const val ACCENT_PURPLE = 0xFF9C27B0
    const val ACCENT_GOLD = 0xFFFFD700
    
    // Video player
    const val VIDEO_STREAM_TIMEOUT = 10000L
    const val VIDEO_BUFFER_SIZE = 1024 * 1024 * 2 // 2MB
    
    // Time format
    const val TIME_FORMAT_PATTERN = "HH:mm"
    
    // Session
    const val MIN_PLAYERS = 2
    const val MAX_PLAYERS = 8
    
    // Game Setup
    const val DEFAULT_PLAYER_COUNT = 2
    const val DEFAULT_DELAY_TIME = 30
    const val MIN_DELAY_TIME = 0
    const val MAX_DELAY_TIME = 300
    const val DELAY_TIME_STEP = 5
}
