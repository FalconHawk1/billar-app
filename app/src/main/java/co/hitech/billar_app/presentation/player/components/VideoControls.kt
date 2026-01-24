package co.hitech.billar_app.presentation.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.*

/**
 * Video player controls component
 */
@Composable
fun VideoControls(
    isPlaying: Boolean,
    isRecording: Boolean,
    onPlayPause: () -> Unit,
    onRewind: () -> Unit,
    onRecord: () -> Unit,
    onGoToLive: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.7f))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left controls
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Play/Pause button
            IconButton(onClick = onPlayPause) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            // Rewind button
            IconButton(onClick = onRewind) {
                Icon(
                    imageVector = Icons.Default.Replay,
                    contentDescription = "Rewind",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            // Record button
            IconButton(onClick = onRecord) {
                Icon(
                    imageVector = Icons.Default.FiberManualRecord,
                    contentDescription = "Record",
                    tint = if (isRecording) Color.Red else Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        
        // Center - Timestamp
        Text(
            text = getCurrentTimestamp(),
            color = Color.White,
            fontSize = 14.sp
        )
        
        // Right - Go to Live button
        IconButton(onClick = onGoToLive) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Sensors,
                    contentDescription = "Go to Live",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "EN VIVO",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
    }
}

/**
 * Get current timestamp string
 */
@Composable
private fun getCurrentTimestamp(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(Date())
}
