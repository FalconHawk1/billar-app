package co.hitech.billar_app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Request model for starting a game session
 */
data class SessionRequest(
    @SerializedName("table_id")
    val tableId: String,
    @SerializedName("start_time")
    val startTime: Long,
    @SerializedName("player_names")
    val playerNames: List<String>
)

/**
 * Request model for updating session data
 */
data class UpdateSessionRequest(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("end_time")
    val endTime: Long? = null,
    @SerializedName("total_cost")
    val totalCost: Double,
    @SerializedName("carambolas")
    val carambolas: Int,
    @SerializedName("entry_count")
    val entryCount: Int
)

/**
 * Request model for updating player scores
 */
data class PlayerScoreRequest(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("player_id")
    val playerId: String,
    @SerializedName("score")
    val score: Int
)
