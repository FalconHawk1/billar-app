package co.hitech.billar_app.domain.usecase

import co.hitech.billar_app.data.remote.dto.SessionResponse
import co.hitech.billar_app.data.repository.TableRepository

/**
 * Use case for starting a new game session
 */
class StartGameSessionUseCase(
    private val repository: TableRepository
) {
    suspend operator fun invoke(
        tableId: String,
        playerNames: List<String>
    ): Result<SessionResponse> {
        return repository.startSession(tableId, playerNames)
    }
}
