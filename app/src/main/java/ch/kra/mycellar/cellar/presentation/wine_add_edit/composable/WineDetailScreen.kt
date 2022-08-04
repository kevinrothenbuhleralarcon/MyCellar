package ch.kra.mycellar.cellar.presentation.wine_add_edit.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.kra.mycellar.R
import ch.kra.mycellar.cellar.data.local.entity.Wine
import ch.kra.mycellar.cellar.domain.WineType
import ch.kra.mycellar.cellar.presentation.wine_add_edit.WineDetailViewModel
import ch.kra.mycellar.ui.WineDetailCore
import ch.kra.mycellar.util.Resource

@Composable
fun WineDetailScreen(
    wineId: Int,
    viewModel: WineDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.surface,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            WineDetailHeader(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                navigateBack = navigateBack
            )

            if (wineId > -1) {
                val wine = produceState<Resource<Wine>>(initialValue = Resource.Loading()) {
                    value = viewModel.getWine(wineId = wineId)
                }.value

                WineWrapper(
                    wine = wine,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 60.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    loadingModifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .padding(
                            top = 60.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    navigateBack = navigateBack
                )
            } else {
                addWine(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 60.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                    navigateBack = navigateBack
                )
            }
        }
    }
}

@Composable
private fun WineDetailHeader(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    Row(
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
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navigateBack()
                }
        )
    }
}

@Composable
private fun WineWrapper(
    wine: Resource<Wine>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    navigateBack: () -> Unit
) {
    when (wine) {
        is Resource.Success -> {
            WineDetail(
                wine = wine.data!!,
                modifier = modifier,
                navigateBack = navigateBack
            )
        }
        is Resource.Error -> {
            Text(
                text = wine.message!!,
                color = Color.Red,
                modifier = modifier
            )
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colors.primary,
                modifier = loadingModifier
            )
        }
    }
}

@Composable
private fun WineDetail(
    wine: Wine,
    modifier: Modifier = Modifier,
    viewModel: WineDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    var wineName by remember {
        mutableStateOf(wine.wineName)
    }

    var wineType by remember {
        mutableStateOf(wine.wineType)
    }

    var offeredBy by remember {
        mutableStateOf(wine.offeredBy)
    }

    var quantity by remember {
        mutableStateOf(wine.quantity.toString())
    }

    var openDialog by remember {
        mutableStateOf(false)
    }

    val wineTypes = mutableListOf<String>()
    enumValues<WineType>().forEach {
        wineTypes.add(stringResource(id = it.resId))
    }
    wineTypes.removeLast() //remove "all" type

    Column(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
    ) {
        WineDetailCore(
            wineName = wineName,
            wineType = wineType,
            offeredBy = offeredBy,
            quantity = quantity,
            wineTypes = wineTypes,
            changeWineName = { wineName = it },
            changeWineType = { wineType = it },
            changeOfferedBy = { offeredBy = it },
            changeQuantity = { quantity = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (viewModel.isEntryValid(
                            wineName,
                            quantity
                        )
                    ) {
                        viewModel.updateWine(
                            wineId = wine.id,
                            wineName = wineName,
                            wineType = wineType,
                            offeredBy = offeredBy,
                            quantity = quantity
                        )
                        navigateBack()
                    }
                },
                colors =
                ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                modifier = Modifier
                    .fillMaxWidth(0.5f)
            )
            {
                Text(
                    text = LocalContext.current.getString(R.string.save),
                    color = MaterialTheme.colors.onSurface
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = {
                    openDialog = true
                },
                colors =
                ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = LocalContext.current.getString(R.string.delete),
                    color = MaterialTheme.colors.onSurface
                )
            }

            if (openDialog) {
                AlertDialog(
                    onDismissRequest = { },
                    title = {
                        Text(text = LocalContext.current.getString(R.string.dialog_confirmation_title))
                    },
                    text = {
                        Text(text = LocalContext.current.getString(R.string.dialog_confirmation_message))
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                openDialog = false
                                viewModel.deleteWine(wine)
                                navigateBack()
                            })
                        {
                            Text(text = LocalContext.current.getString(R.string.dialog_confirmation_positive))
                        }
                    },
                    dismissButton =  {
                        Button(
                            onClick = { openDialog = false }
                        ) {
                            Text(text = LocalContext.current.getString(R.string.dialog_confirmation_negative))
                        }
                    }
                )
            }

        }
    }
}

@Composable
private fun addWine(
    modifier: Modifier = Modifier,
    viewModel: WineDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    var wineName by remember {
        mutableStateOf("")
    }

    var wineType by remember {
        mutableStateOf(WineType.RED_WINE)
    }

    var offeredBy by remember {
        mutableStateOf("")
    }

    var quantity by remember {
        mutableStateOf("")
    }

    val wineTypes = mutableListOf<String>()
    enumValues<WineType>().forEach {
        wineTypes.add(stringResource(id = it.resId))
    }
    wineTypes.removeLast() //remove "all" type

    Column(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
    ) {
        WineDetailCore(
            wineName = wineName,
            wineType = wineType,
            offeredBy = offeredBy,
            quantity = quantity,
            wineTypes = wineTypes,
            changeWineName = { wineName = it },
            changeWineType = { wineType = it },
            changeOfferedBy = { offeredBy = it },
            changeQuantity = { quantity = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel.isEntryValid(
                        wineName,
                        quantity
                    )
                ) {
                    viewModel.addWine(
                        wineName,
                        wineType,
                        quantity,
                        offeredBy
                    )
                    navigateBack()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = LocalContext.current.getString(R.string.save),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}

