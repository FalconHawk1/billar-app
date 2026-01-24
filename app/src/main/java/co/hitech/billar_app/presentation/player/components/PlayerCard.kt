package co.hitech.billar_app.presentation.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.hitech.billar_app.data.model.Player
import co.hitech.billar_app.ui.theme.ScoreMinusColor
import co.hitech.billar_app.ui.theme.ScorePlus1Color
import co.hitech.billar_app.ui.theme.ScorePlus5Color

/**
 * Player card component showing player info and score controls
 */
@Composable
fun PlayerCard(
    player: Player,
    onScoreChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = player.color.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Player name
            Text(
                text = player.name,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Score display
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(40.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = player.score.toString(),
                    style = MaterialTheme.typography.displayMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Score control buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ScoreButton(
                    text = "-1",
                    color = ScoreMinusColor,
                    onClick = { onScoreChange(-1) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                ScoreButton(
                    text = "+1",
                    color = ScorePlus1Color,
                    onClick = { onScoreChange(1) },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            ScoreButton(
                text = "+5",
                color = ScorePlus5Color,
                onClick = { onScoreChange(5) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Score control button
 */
@Composable
fun ScoreButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        modifier = modifier.height(40.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}
