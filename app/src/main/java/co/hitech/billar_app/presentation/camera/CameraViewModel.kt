package co.hitech.billar_app.presentation.camera

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Camera state sealed class
 */
sealed class CameraState {
    object Idle : CameraState()
    object Loading : CameraState()
    object Playing : CameraState()
    object Paused : CameraState()
    data class Error(val message: String) : CameraState()
}

/**
 * ViewModel for managing camera stream state and controls
 */
class CameraViewModel : ViewModel() {
    
    companion object {
        private const val TAG = "CameraViewModel"
    }

    private val _cameraUrl = MutableStateFlow("")
    val cameraUrl: StateFlow<String> = _cameraUrl.asStateFlow()
    
    private val _cameraState = MutableStateFlow<CameraState>(CameraState.Idle)
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()
    
    private val _isLive = MutableStateFlow(true)
    val isLive: StateFlow<Boolean> = _isLive.asStateFlow()
    
    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()
    
    private val _isMaximized = MutableStateFlow(false)
    val isMaximized: StateFlow<Boolean> = _isMaximized.asStateFlow()

    /**
     * Set camera URL
     */
    fun setCameraUrl(url: String) {
        Log.d("TAGI", "Setting camera URL: $url")
        _cameraUrl.value = url
        if (url.isNotEmpty()) {
            Log.d("TAGI", "URL is not empty, setting state to Loading")
            _cameraState.value = CameraState.Loading

            // Auto-transition to Playing state after brief delay
            // In real implementation, this would happen when ExoPlayer is ready
            viewModelScope.launch {
                delay(1500) // Simulate loading time
                if (_cameraState.value is CameraState.Loading) {
                    Log.d("TAGI", "Auto-transitioning to Playing state")
                    _cameraState.value = CameraState.Playing
                    _isLive.value = true
                }
            }
        } else {
            Log.w("TAGI", "URL is empty!")
            _cameraState.value = CameraState.Idle
        }
    }
    
    /**
     * Play the camera stream
     */
    fun play() {
        Log.d(TAG, "Playing camera stream")
        _cameraState.value = CameraState.Playing
        _isLive.value = true
    }
    
    /**
     * Pause the camera stream
     */
    fun pause() {
        _cameraState.value = CameraState.Paused
        _isLive.value = false
    }
    
    /**
     * Toggle play/pause
     */
    fun togglePlayPause() {
        when (_cameraState.value) {
            is CameraState.Playing -> pause()
            is CameraState.Paused, is CameraState.Idle -> play()
            else -> {}
        }
    }
    
    /**
     * Start/stop recording
     */
    fun toggleRecording() {
        _isRecording.value = !_isRecording.value
    }
    
    /**
     * Rewind (seek back)
     */
    fun rewind() {
        _isLive.value = false
        // TODO: Implement actual seek functionality with ExoPlayer
    }
    
    /**
     * Go to live
     */
    fun goToLive() {
        _isLive.value = true
        // TODO: Implement seek to live with ExoPlayer
    }
    
    /**
     * Handle camera error
     */
    fun onError(message: String) {
        _cameraState.value = CameraState.Error(message)
    }
    
    /**
     * Handle camera ready
     */
    fun onReady() {
        _cameraState.value = CameraState.Playing
    }

    /**
     * Toggle maximize/minimize camera view
     */
    fun toggleMaximize() {
        Log.d(TAG, "Toggling maximize: ${!_isMaximized.value}")
        _isMaximized.value = !_isMaximized.value
    }
}
