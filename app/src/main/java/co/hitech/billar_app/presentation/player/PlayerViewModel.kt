package co.hitech.billar_app.presentation.player

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.hitech.billar_app.data.model.Player
import co.hitech.billar_app.domain.usecase.AddPlayerUseCase
import co.hitech.billar_app.domain.usecase.RemovePlayerUseCase
import co.hitech.billar_app.domain.usecase.UpdatePlayerScoreUseCase
import co.hitech.billar_app.utils.getPlayerColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel for managing player state and actions
 */
class PlayerViewModel(
    private val updatePlayerScoreUseCase: UpdatePlayerScoreUseCase,
    private val addPlayerUseCase: AddPlayerUseCase,
    private val removePlayerUseCase: RemovePlayerUseCase
) : ViewModel() {
    
    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        // Initialize with 2 players
        initializePlayers()
    }
    
    private fun initializePlayers() {
        val initialPlayers = listOf(
            Player(
                id = UUID.randomUUID().toString(),
                name = "Jugador 1",
                score = 0,
                color = getPlayerColor(0)
            ),
            Player(
                id = UUID.randomUUID().toString(),
                name = "Jugador 2",
                score = 0,
                color = getPlayerColor(1)
            )
        )
        _players.value = initialPlayers
    }
    
    /**
     * Add a new player
     */
    fun addPlayer() {
        val currentPlayers = _players.value
        if (currentPlayers.size < 6) {
            val newPlayerName = addPlayerUseCase(currentPlayers.size)
            val newPlayer = Player(
                id = UUID.randomUUID().toString(),
                name = newPlayerName,
                score = 0,
                color = getPlayerColor(currentPlayers.size)
            )
            _players.value = currentPlayers + newPlayer
        }
    }
    
    /**
     * Remove the last player
     */
    fun removePlayer() {
        val currentPlayers = _players.value
        if (removePlayerUseCase(currentPlayers.size)) {
            _players.value = currentPlayers.dropLast(1)
        }
    }
    
    /**
     * Update player score
     */
    fun updateScore(playerId: String, scoreChange: Int) {
        val currentPlayers = _players.value
        val updatedPlayers = currentPlayers.map { player ->
            if (player.id == playerId) {
                val newScore = (player.score + scoreChange).coerceAtLeast(0)
                player.copy(score = newScore)
            } else {
                player
            }
        }
        _players.value = updatedPlayers
        
        // TODO: Sync with server when sessionId is available
        // viewModelScope.launch {
        //     updatePlayerScoreUseCase(sessionId, playerId, newScore)
        // }
    }
    
    /**
     * Reset all player scores
     */
    fun resetScores() {
        val currentPlayers = _players.value
        val resetPlayers = currentPlayers.map { it.copy(score = 0) }
        _players.value = resetPlayers
    }
    
    /**
     * Get player by ID
     */
    fun getPlayer(playerId: String): Player? {
        return _players.value.find { it.id == playerId }
    }
}
