package co.hitech.billar_app.data.model

/**
 * Data class representing a game session
 * @param sessionId Unique identifier for the session
 * @param tableId ID of the table being used
 * @param startTime Session start timestamp in milliseconds
 * @param endTime Session end timestamp in milliseconds (null if ongoing)
 * @param totalCost Total cost accumulated
 * @param players List of players in the session
 * @param carambolas Number of carambolas (carom shots)
 * @param entryCount Entry count
 */
data class GameSession(
    val sessionId: String,
    val tableId: String,
    val startTime: Long,
    val endTime: Long? = null,
    val totalCost: Double = 0.0,
    val players: List<Player> = emptyList(),
    val carambolas: Int = 0,
    val entryCount: Int = 0
)
