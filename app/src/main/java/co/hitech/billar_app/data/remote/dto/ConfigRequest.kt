package co.hitech.billar_app.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Request model for getting table configuration
 */
data class ConfigRequest(
    @SerializedName("table_id")
    val tableId: String
)
