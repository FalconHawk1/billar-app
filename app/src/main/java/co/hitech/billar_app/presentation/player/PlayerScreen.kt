package co.hitech.billar_app.presentation.player

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.hitech.billar_app.domain.usecase.*
import co.hitech.billar_app.presentation.camera.CameraViewModel
import co.hitech.billar_app.presentation.player.components.CameraView
import co.hitech.billar_app.presentation.player.components.KpiTopBar
import co.hitech.billar_app.presentation.player.components.PlayerCard
import co.hitech.billar_app.presentation.session.GameSessionViewModel
import co.hitech.billar_app.ui.theme.BackgroundDark

/**
 * Main player screen
 */
@Composable
fun PlayerScreen(
    playerViewModel: PlayerViewModel = viewModel(
        factory = PlayerViewModelFactory()
    ),
    sessionViewModel: GameSessionViewModel = viewModel(
        factory = GameSessionViewModelFactory()
    ),
    cameraViewModel: CameraViewModel = viewModel()
) {
    val players by playerViewModel.players.collectAsState()
    val elapsedTime by sessionViewModel.elapsedTime.collectAsState()
    val totalCost by sessionViewModel.totalCost.collectAsState()
    val carambolas by sessionViewModel.carambolas.collectAsState()
    val entryCount by sessionViewModel.entryCount.collectAsState()
    
    val cameraUrl by cameraViewModel.cameraUrl.collectAsState()
    val cameraState by cameraViewModel.cameraState.collectAsState()
    val isLive by cameraViewModel.isLive.collectAsState()
    val isRecording by cameraViewModel.isRecording.collectAsState()
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar with KPIs
            KpiTopBar(
                time = elapsedTime,
                cost = totalCost,
                entryCount = entryCount,
                carambolas = carambolas,
                onCloseSession = {
                    sessionViewModel.endSession()
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Main content area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Left side - First half of players
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val leftPlayers = players.take((players.size + 1) / 2)
                    items(leftPlayers) { player ->
                        PlayerCard(
                            player = player,
                            onScoreChange = { change ->
                                playerViewModel.updateScore(player.id, change)
                            }
                        )
                    }
                }
                
                // Center - Camera view
                CameraView(
                    cameraUrl = cameraUrl,
                    cameraState = cameraState,
                    isLive = isLive,
                    isRecording = isRecording,
                    onPlayPause = { cameraViewModel.togglePlayPause() },
                    onRewind = { cameraViewModel.rewind() },
                    onRecord = { cameraViewModel.toggleRecording() },
                    onGoToLive = { cameraViewModel.goToLive() },
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                )
                
                // Right side - Second half of players
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val rightPlayers = players.drop((players.size + 1) / 2)
                    items(rightPlayers) { player ->
                        PlayerCard(
                            player = player,
                            onScoreChange = { change ->
                                playerViewModel.updateScore(player.id, change)
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Factory for PlayerViewModel
 */
class PlayerViewModelFactory : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        // Simplified version without repository dependency
        // Repository functionality is optional and not required for basic operation
        val dummyRepository = object : co.hitech.billar_app.data.repository.TableRepository {
            override suspend fun startSession(tableId: String, playerNames: List<String>) =
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun updateSession(sessionId: String, totalCost: Double, carambolas: Int, entryCount: Int) =
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun endSession(sessionId: String, endTime: Long, totalCost: Double, carambolas: Int, entryCount: Int) =
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun updatePlayerScore(sessionId: String, playerId: String, score: Int) =
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun getTableConfig(tableId: String) =
                Result.failure<co.hitech.billar_app.data.model.TableConfig>(Exception("Not implemented"))
        }

        val updatePlayerScoreUseCase = UpdatePlayerScoreUseCase(dummyRepository)
        val addPlayerUseCase = AddPlayerUseCase()
        val removePlayerUseCase = RemovePlayerUseCase()
        
        return PlayerViewModel(
            updatePlayerScoreUseCase,
            addPlayerUseCase,
            removePlayerUseCase
        ) as T
    }
}

/**
 * Factory for GameSessionViewModel
 */
class GameSessionViewModelFactory : androidx.lifecycle.ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        // Simplified version without repository dependency
        val dummyRepository = object : co.hitech.billar_app.data.repository.TableRepository {
            override suspend fun startSession(tableId: String, playerNames: List<String>) = 
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun updateSession(sessionId: String, totalCost: Double, carambolas: Int, entryCount: Int) = 
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun endSession(sessionId: String, endTime: Long, totalCost: Double, carambolas: Int, entryCount: Int) = 
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun updatePlayerScore(sessionId: String, playerId: String, score: Int) = 
                Result.failure<co.hitech.billar_app.data.remote.dto.SessionResponse>(Exception("Not implemented"))
            override suspend fun getTableConfig(tableId: String) = 
                Result.failure<co.hitech.billar_app.data.model.TableConfig>(Exception("Not implemented"))
        }
        
        val startGameSessionUseCase = StartGameSessionUseCase(dummyRepository)
        val endGameSessionUseCase = EndGameSessionUseCase(dummyRepository)
        
        return GameSessionViewModel(
            startGameSessionUseCase,
            endGameSessionUseCase
        ) as T
    }
}
