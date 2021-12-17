package ch.kra.mycellar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ch.kra.mycellar.R

@Composable
fun WineListScreen(
    navigate: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        WineListScreenToolbar(navigate = navigate)
        WineCard(
            onClick = navigate,
            modifier = Modifier
                .fillMaxWidth(0.5f)
        )
    }
}

@Composable
private fun WineListScreenToolbar(
    navigate: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.List,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    /* TODO Sorting */
                }
        )

        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(30.dp)
                .clickable { navigate(0) }
        )
    }
}

@Composable
private fun WineCard(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Red,
                        Color.White
                    )
                )
            )
            .clickable { onClick(1) }
    ) {
        Column {
            Text(text = "Pino Gris")
            Text(text = "Didier")
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            /* TODO add wine */
                        }
                )
                Text(text = "50")
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription =null,
                    tint = Color.Black,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            /* TODO substract wine */
                        }
                )
            }
        }
    }
}