package co.hitech.billar_app.data.model

/**
 * Data class representing table configuration
 * @param tableId Unique identifier for the table
 * @param cameraUrl URL of the IP camera stream (RTSP or HTTP)
 * @param pricePerMinute Cost per minute of play
 * @param tableName Display name of the table
 * @param isActive Whether the table is currently active
 */
data class TableConfig(
    val tableId: String,
    val cameraUrl: String,
    val pricePerMinute: Double,
    val tableName: String = "Mesa 1",
    val isActive: Boolean = true
)
