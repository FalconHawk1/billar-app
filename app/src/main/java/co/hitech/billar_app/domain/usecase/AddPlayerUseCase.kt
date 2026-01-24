package co.hitech.billar_app.domain.usecase

/**
 * Use case for adding a new player to the game
 * This is a local operation that doesn't involve the repository
 */
class AddPlayerUseCase {
    operator fun invoke(currentPlayerCount: Int): String {
        val newPlayerNumber = currentPlayerCount + 1
        return "Jugador $newPlayerNumber"
    }
}
