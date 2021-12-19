package ch.kra.mycellar.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.kra.mycellar.R
import ch.kra.mycellar.database.Wine
import ch.kra.mycellar.ui.viewmodel.WineViewModel

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
        WineList(onClick = navigate)
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
private fun WineList(
    viewModel: WineViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val wineList by remember {
        viewModel.listWine
    }
    LazyColumn(contentPadding = PaddingValues(8.dp)) {
        val itemCount = if (wineList.size % 2 == 0) {
            wineList.size / 2
        } else {
            wineList.size / 2 +1
        }
        items(itemCount) {
            WineListRow(rowNumber = it, wineList = wineList, onClick = onClick)
        }
    }
}

@Composable
private fun WineListRow(
    rowNumber: Int,
    wineList: List<Wine>,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        WineCard(
            wine = wineList[rowNumber * 2],
            painter = painterResource(id = R.drawable.red_wine),
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(150.dp)
        )

        if (wineList.size >= rowNumber * 2 + 2) {
            WineCard(
                wine = wineList[rowNumber * 2 + 1],
                painter = painterResource(id = R.drawable.white_wine),
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        } else {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }


    }
}

@Composable
private fun WineCard(
    wine: Wine,
    painter: Painter,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(8.dp)
            .clickable { onClick(1) }
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3f,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
        )

        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = wine.wineName)
            Text(text = wine.offeredBy)
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
                Text(text = wine.quantity.toString())
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