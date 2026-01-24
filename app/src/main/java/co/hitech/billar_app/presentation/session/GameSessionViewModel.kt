package co.hitech.billar_app.presentation.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.hitech.billar_app.domain.usecase.EndGameSessionUseCase
import co.hitech.billar_app.domain.usecase.StartGameSessionUseCase
import co.hitech.billar_app.utils.formatCurrency
import co.hitech.billar_app.utils.formatElapsedTime
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * ViewModel for managing game session state, time, and cost
 */
class GameSessionViewModel(
    private val startGameSessionUseCase: StartGameSessionUseCase,
    private val endGameSessionUseCase: EndGameSessionUseCase
) : ViewModel() {
    
    private val _sessionId = MutableStateFlow<String?>(null)
    val sessionId: StateFlow<String?> = _sessionId.asStateFlow()
    
    private val _isSessionActive = MutableStateFlow(false)
    val isSessionActive: StateFlow<Boolean> = _isSessionActive.asStateFlow()
    
    private val _startTime = MutableStateFlow(0L)
    private val _elapsedTimeMillis = MutableStateFlow(0L)
    val elapsedTime: StateFlow<String> = MutableStateFlow("00:00")
    
    private val _pricePerMinute = MutableStateFlow(5.0)
    val pricePerMinute: StateFlow<Double> = _pricePerMinute.asStateFlow()
    
    private val _totalCost = MutableStateFlow(0.0)
    val totalCost: StateFlow<String> = MutableStateFlow("$0.00")
    
    private val _carambolas = MutableStateFlow(0)
    val carambolas: StateFlow<Int> = _carambolas.asStateFlow()
    
    private val _entryCount = MutableStateFlow(0)
    val entryCount: StateFlow<Int> = _entryCount.asStateFlow()
    
    private var timerJob: Job? = null
    
    init {
        // Auto-start session
        startSession("table_1", listOf("Jugador 1", "Jugador 2"))
    }
    
    /**
     * Start a new game session
     */
    fun startSession(tableId: String, playerNames: List<String>) {
        viewModelScope.launch {
            _sessionId.value = UUID.randomUUID().toString()
            _startTime.value = System.currentTimeMillis()
            _isSessionActive.value = true
            _elapsedTimeMillis.value = 0L
            
            startTimer()
            
            // TODO: Call API when backend is ready
            // val result = startGameSessionUseCase(tableId, playerNames)
        }
    }
    
    /**
     * End the current session
     */
    fun endSession() {
        viewModelScope.launch {
            _isSessionActive.value = false
            timerJob?.cancel()
            
            val sessionId = _sessionId.value ?: return@launch
            
            // TODO: Call API when backend is ready
            // endGameSessionUseCase(
            //     sessionId = sessionId,
            //     totalCost = _totalCost.value,
            //     carambolas = _carambolas.value,
            //     entryCount = _entryCount.value
            // )
        }
    }
    
    /**
     * Start the timer
     */
    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_isSessionActive.value) {
                delay(1000L) // Update every second
                _elapsedTimeMillis.value = System.currentTimeMillis() - _startTime.value
                updateDisplayValues()
            }
        }
    }
    
    /**
     * Update display values (time and cost)
     */
    private fun updateDisplayValues() {
        // Update elapsed time
        (elapsedTime as MutableStateFlow).value = _elapsedTimeMillis.value.formatElapsedTime()
        
        // Calculate and update cost
        val elapsedMinutes = _elapsedTimeMillis.value / 60000.0
        val cost = elapsedMinutes * _pricePerMinute.value
        _totalCost.value = cost
        (totalCost as MutableStateFlow).value = cost.formatCurrency()
    }
    
    /**
     * Set price per minute
     */
    fun setPricePerMinute(price: Double) {
        _pricePerMinute.value = price
        updateDisplayValues()
    }
    
    /**
     * Increment carambolas count
     */
    fun incrementCarambolas() {
        _carambolas.value += 1
    }
    
    /**
     * Decrement carambolas count
     */
    fun decrementCarambolas() {
        if (_carambolas.value > 0) {
            _carambolas.value -= 1
        }
    }
    
    /**
     * Increment entry count
     */
    fun incrementEntryCount() {
        _entryCount.value += 1
    }
    
    /**
     * Decrement entry count
     */
    fun decrementEntryCount() {
        if (_entryCount.value > 0) {
            _entryCount.value -= 1
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }
}
