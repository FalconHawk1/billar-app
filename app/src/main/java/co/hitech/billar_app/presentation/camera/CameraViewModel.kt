package co.hitech.billar_app.presentation.camera

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    
    private val _cameraUrl = MutableStateFlow("")
    val cameraUrl: StateFlow<String> = _cameraUrl.asStateFlow()
    
    private val _cameraState = MutableStateFlow<CameraState>(CameraState.Idle)
    val cameraState: StateFlow<CameraState> = _cameraState.asStateFlow()
    
    private val _isLive = MutableStateFlow(true)
    val isLive: StateFlow<Boolean> = _isLive.asStateFlow()
    
    private val _isRecording = MutableStateFlow(false)
    val isRecording: StateFlow<Boolean> = _isRecording.asStateFlow()
    
    /**
     * Set camera URL
     */
    fun setCameraUrl(url: String) {
        _cameraUrl.value = url
        if (url.isNotEmpty()) {
            _cameraState.value = CameraState.Loading
        }
    }
    
    /**
     * Play the camera stream
     */
    fun play() {
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
}
