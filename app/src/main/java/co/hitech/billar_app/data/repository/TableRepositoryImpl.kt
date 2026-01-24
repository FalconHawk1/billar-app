package co.hitech.billar_app.data.repository

import co.hitech.billar_app.data.model.TableConfig
import co.hitech.billar_app.data.remote.api.BilliardApiService
import co.hitech.billar_app.data.remote.dto.*

/**
 * Implementation of TableRepository
 */
class TableRepositoryImpl(
    private val apiService: BilliardApiService
) : TableRepository {
    
    override suspend fun startSession(
        tableId: String,
        playerNames: List<String>
    ): Result<SessionResponse> {
        return try {
            val request = SessionRequest(
                tableId = tableId,
                startTime = System.currentTimeMillis(),
                playerNames = playerNames
            )
            val response = apiService.startSession(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to start session: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updateSession(
        sessionId: String,
        totalCost: Double,
        carambolas: Int,
        entryCount: Int
    ): Result<SessionResponse> {
        return try {
            val request = UpdateSessionRequest(
                sessionId = sessionId,
                totalCost = totalCost,
                carambolas = carambolas,
                entryCount = entryCount
            )
            val response = apiService.updateSession(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update session: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun endSession(
        sessionId: String,
        endTime: Long,
        totalCost: Double,
        carambolas: Int,
        entryCount: Int
    ): Result<SessionResponse> {
        return try {
            val request = UpdateSessionRequest(
                sessionId = sessionId,
                endTime = endTime,
                totalCost = totalCost,
                carambolas = carambolas,
                entryCount = entryCount
            )
            val response = apiService.endSession(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to end session: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun updatePlayerScore(
        sessionId: String,
        playerId: String,
        score: Int
    ): Result<SessionResponse> {
        return try {
            val request = PlayerScoreRequest(
                sessionId = sessionId,
                playerId = playerId,
                score = score
            )
            val response = apiService.updatePlayerScore(request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to update score: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getTableConfig(tableId: String): Result<TableConfig> {
        return try {
            val response = apiService.getTableConfig(tableId)
            if (response.isSuccessful && response.body()?.data != null) {
                val data = response.body()!!.data!!
                val config = TableConfig(
                    tableId = data.tableId,
                    cameraUrl = data.cameraUrl,
                    pricePerMinute = data.pricePerMinute,
                    tableName = data.tableName,
                    isActive = data.isActive
                )
                Result.success(config)
            } else {
                Result.failure(Exception("Failed to get config: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
