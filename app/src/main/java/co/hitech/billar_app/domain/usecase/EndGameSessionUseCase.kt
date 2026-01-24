package co.hitech.billar_app.domain.usecase

import co.hitech.billar_app.data.remote.dto.SessionResponse
import co.hitech.billar_app.data.repository.TableRepository

/**
 * Use case for ending a game session
 */
class EndGameSessionUseCase(
    private val repository: TableRepository
) {
    suspend operator fun invoke(
        sessionId: String,
        totalCost: Double,
        carambolas: Int,
        entryCount: Int
    ): Result<SessionResponse> {
        val endTime = System.currentTimeMillis()
        return repository.endSession(
            sessionId = sessionId,
            endTime = endTime,
            totalCost = totalCost,
            carambolas = carambolas,
            entryCount = entryCount
        )
    }
}
