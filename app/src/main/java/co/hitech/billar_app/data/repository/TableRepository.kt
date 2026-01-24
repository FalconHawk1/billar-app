package co.hitech.billar_app.data.repository

import co.hitech.billar_app.data.model.TableConfig
import co.hitech.billar_app.data.remote.dto.*

/**
 * Repository interface for table operations
 */
interface TableRepository {
    
    /**
     * Start a new game session
     */
    suspend fun startSession(tableId: String, playerNames: List<String>): Result<SessionResponse>
    
    /**
     * Update session data
     */
    suspend fun updateSession(
        sessionId: String,
        totalCost: Double,
        carambolas: Int,
        entryCount: Int
    ): Result<SessionResponse>
    
    /**
     * End a game session
     */
    suspend fun endSession(
        sessionId: String,
        endTime: Long,
        totalCost: Double,
        carambolas: Int,
        entryCount: Int
    ): Result<SessionResponse>
    
    /**
     * Update player score
     */
    suspend fun updatePlayerScore(
        sessionId: String,
        playerId: String,
        score: Int
    ): Result<SessionResponse>
    
    /**
     * Get table configuration from API
     */
    suspend fun getTableConfig(tableId: String): Result<TableConfig>
}
