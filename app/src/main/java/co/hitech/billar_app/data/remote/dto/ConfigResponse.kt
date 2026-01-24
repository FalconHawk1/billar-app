package co.hitech.billar_app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Response model for table configuration
 */
data class ConfigResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ConfigData? = null
)

/**
 * Configuration data contained in response
 */
data class ConfigData(
    @SerializedName("table_id")
    val tableId: String,
    @SerializedName("table_name")
    val tableName: String,
    @SerializedName("camera_url")
    val cameraUrl: String,
    @SerializedName("price_per_minute")
    val pricePerMinute: Double,
    @SerializedName("is_active")
    val isActive: Boolean
)
