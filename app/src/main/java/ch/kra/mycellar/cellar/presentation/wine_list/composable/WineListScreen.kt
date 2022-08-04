package ch.kra.mycellar.cellar.presentation.wine_list.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.kra.mycellar.R
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.presentation.wine_list.WineListEvent
import ch.kra.mycellar.cellar.presentation.wine_list.WineListViewModel
import ch.kra.mycellar.core.UIEvent
import ch.kra.mycellar.ui.WineDropDown

@Composable
fun WineListScreen(
    navigate: (UIEvent.Navigate) -> Unit,
    listViewModel: WineListViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        listViewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Navigate -> {
                    navigate(event)
                }

                is UIEvent.PopBackStack -> {
                    //Nothing
                }
            }
        }
    }

    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            WineListScreenToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
            )
            WineList(
                modifier = Modifier
                    .padding(top = 60.dp),
            )
        }
    }
}

@Composable
private fun WineListScreenToolbar(
    modifier: Modifier = Modifier,
    listViewModel: WineListViewModel = hiltViewModel(),
) {
    var expend by remember {
        mutableStateOf(false)
    }

    val wineListState = listViewModel.wineListState.value

    val wineTypes = mutableListOf<String>()
    enumValues<WineType>().forEach {
        wineTypes.add(stringResource(id = it.resId))
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Text(
            text = stringResource(id = wineListState.wineType.resId),
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.offset(16.dp, 16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(30.dp)
                    .offset((-16).dp, 16.dp)
                    .clickable {
                        expend = true
                    }
            )
            WineDropDown(
                expend = expend,
                wineTypes = wineTypes,
                dismiss = { expend = false },
                onItemSelected = { listViewModel.onEvent(WineListEvent.WineTypeSelected(it)) }
            )

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(30.dp)
                    .offset((-16).dp, 16.dp)
                    .clickable { listViewModel.onEvent(WineListEvent.AddNewWine) }
            )
        }
    }
}

@Composable
private fun WineList(
    modifier: Modifier = Modifier,
    listViewModel: WineListViewModel = hiltViewModel(),
) {
    val wineListState = listViewModel.wineListState.value
    //val wineList = listViewModel.listWine.collectAsState(initial = emptyList()).value
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        val itemCount = if (wineListState.wineList.size % 2 == 0) {
            wineListState.wineList.size / 2
        } else {
            wineListState.wineList.size / 2 + 1
        }
        items(itemCount) {
            WineListRow(rowNumber = it, wineList = wineListState.wineList, onClick = { wineId ->
                listViewModel.onEvent(WineListEvent.WineSelected(wineId))
            })
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
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(150.dp)
        )

        if (wineList.size >= rowNumber * 2 + 2) {
            WineCard(
                wine = wineList[rowNumber * 2 + 1],
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
    modifier: Modifier = Modifier,
    listViewModel: WineListViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick(wine.id) }
            .padding(5.dp),
        elevation = 5.dp
    ) {
        Image(
            painter = painterResource(id = wine.wineType.drawableId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5f,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = wine.wineName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onPrimary
            )
            Text(
                text = wine.offeredBy ?: "",
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            listViewModel.onEvent(
                                WineListEvent.ChangeQuantity(
                                    wine = wine,
                                    isAdd = true
                                )
                            )
                        }
                )
                Text(
                    text = wine.quantity.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onPrimary
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            listViewModel.onEvent(
                                WineListEvent.ChangeQuantity(
                                    wine = wine,
                                    isAdd = false
                                )
                            )
                        }
                )
            }
        }
    }
}