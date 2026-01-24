package co.hitech.billar_app.domain.usecase

import co.hitech.billar_app.data.remote.dto.SessionResponse
import co.hitech.billar_app.data.repository.TableRepository

/**
 * Use case for updating player score
 */
class UpdatePlayerScoreUseCase(
    private val repository: TableRepository
) {
    suspend operator fun invoke(
        sessionId: String,
        playerId: String,
        score: Int
    ): Result<SessionResponse> {
        return repository.updatePlayerScore(sessionId, playerId, score)
    }
}
