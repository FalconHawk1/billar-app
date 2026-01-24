package co.hitech.billar_app.domain.usecase

/**
 * Use case for removing a player from the game
 * This is a local operation that validates if a player can be removed
 */
class RemovePlayerUseCase {
    operator fun invoke(currentPlayerCount: Int): Boolean {
        // Ensure at least 2 players remain
        return currentPlayerCount > 2
    }
}
