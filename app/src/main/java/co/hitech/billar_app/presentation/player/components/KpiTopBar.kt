package co.hitech.billar_app.presentation.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.hitech.billar_app.ui.theme.TableGreen
import co.hitech.billar_app.ui.theme.WoodBrown
import co.hitech.billar_app.ui.theme.CardBackground

/**
 * Top bar displaying KPIs and session controls
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KpiTopBar(
    time: String,
    cost: String,
    entryCount: Int,
    carambolas: Int,
    onCloseSession: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(CardBackground.copy(alpha = 0.9f))
            .padding(16.dp)
    ) {
        // Header with title and close button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Mesa de Jugadores",
                    style = MaterialTheme.typography.headlineSmall,
                    color = TableGreen,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Bienvenido, Player User",
                    style = MaterialTheme.typography.bodySmall,
                    color = WoodBrown
                )
            }
            
            Button(
                onClick = onCloseSession,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text("Cerrar Sesión")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // KPI Cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            KpiCard(
                title = "Tiempo",
                value = time,
                icon = Icons.Default.AccessTime,
                modifier = Modifier.weight(1f)
            )
            KpiCard(
                title = "Costo",
                value = cost,
                icon = Icons.Default.AttachMoney,
                modifier = Modifier.weight(1f)
            )
            KpiCard(
                title = "Entrada",
                value = entryCount.toString(),
                icon = Icons.Default.Person,
                modifier = Modifier.weight(1f)
            )
            KpiCard(
                title = "Carambolas",
                value = carambolas.toString(),
                icon = Icons.Default.Star,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Individual KPI card
 */
@Composable
fun KpiCard(
    title: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = TableGreen,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = WoodBrown,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = TableGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
