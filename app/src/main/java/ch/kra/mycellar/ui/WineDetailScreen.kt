package ch.kra.mycellar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ch.kra.mycellar.R
import ch.kra.mycellar.ui.viewmodel.WineViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WineDetailScreen(
    wineId: Int,
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
            WineDetailCore(
                wineId = wineId,
                modifier = Modifier
                    .fillMaxSize()
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
private fun WineDetailCore(
    wineId: Int,
    modifier: Modifier = Modifier,
    viewModel: WineViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    Column(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
            .padding(16.dp)
    ) {
        var wineName by remember {
            mutableStateOf("")
        }
        var wineType by remember {
            mutableStateOf(0)
        }
        var offerdBy by remember {
            mutableStateOf("")
        }
        var quantity by remember {
            mutableStateOf(0)
        }

        if (wineId > 0) {
            LaunchedEffect(key1 = true) {
                viewModel.getWine(wineId = wineId).collectLatest {
                    wineName = it.wineName
                    wineType = it.wineType
                    offerdBy = it.offeredBy
                    quantity = it.quantity
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = wineName,
                onValueChange = {
                    wineName = it
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(MaterialTheme.colors.onPrimary),
                label = {
                    Text(
                        text = LocalContext.current.getString(R.string.wine_name_req),
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}