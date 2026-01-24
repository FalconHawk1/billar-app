package co.hitech.billar_app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Response model for session operations
 */
data class SessionResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("session_id")
    val sessionId: String? = null,
    @SerializedName("data")
    val data: SessionData? = null
)

/**
 * Session data contained in response
 */
data class SessionData(
    @SerializedName("session_id")
    val sessionId: String,
    @SerializedName("table_id")
    val tableId: String,
    @SerializedName("start_time")
    val startTime: Long,
    @SerializedName("end_time")
    val endTime: Long? = null,
    @SerializedName("total_cost")
    val totalCost: Double
)
