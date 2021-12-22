package ch.kra.mycellar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import ch.kra.mycellar.R

@Composable
fun WineDetailCore(
    wineName: String,
    wineType: Int,
    offeredBy: String,
    quantity: String,
    wineTypes: List<String>,
    changeWineName: (String) -> Unit,
    changeWineType: (Int) -> Unit,
    changeOfferedBy: (String) -> Unit,
    changeQuantity: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var expend by remember {
            mutableStateOf(false)
        }

        val focusManager = LocalFocusManager.current

        OutlinedTextField(
            value = wineName,
            onValueChange = {
                changeWineName(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(MaterialTheme.colors.onPrimary),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary)) {
                            append(LocalContext.current.getString(R.string.wine_name))
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" *")
                        }
                    }
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = wineTypes[wineType - 1],
                onValueChange = {

                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(MaterialTheme.colors.onPrimary),
                label = {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary)) {
                                append(LocalContext.current.getString(R.string.wine_type))
                            }
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append(" *")
                            }
                        }
                    )
                },
                readOnly = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colors.onPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .clickable { expend = !expend }
                    )
                }
            )
            WineDropDown(
                expend = expend,
                wineTypes = wineTypes,
                dismiss = { expend = false },
                onItemSelected = { changeWineType(it) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = offeredBy,
            onValueChange = {
                changeOfferedBy(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(MaterialTheme.colors.onPrimary),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary)) {
                            append(LocalContext.current.getString(R.string.offered_by))
                        }
                    }
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                changeQuantity(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(MaterialTheme.colors.onPrimary),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = MaterialTheme.colors.onPrimary)) {
                            append(LocalContext.current.getString(R.string.quantity))
                        }
                        withStyle(style = SpanStyle(color = Color.Red)) {
                            append(" *")
                        }
                    }
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.onPrimary,
                unfocusedBorderColor = MaterialTheme.colors.onPrimary,
                cursorColor = MaterialTheme.colors.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun WineDropDown(
    expend: Boolean,
    wineTypes: List<String>,
    dismiss: () -> Unit,
    onItemSelected: (Int) -> Unit
) {
    DropdownMenu(
        expanded = expend,
        onDismissRequest = { dismiss() },
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
    ) {
        wineTypes.forEach { type ->
            DropdownMenuItem(
                onClick = {
                    onItemSelected(wineTypes.indexOf(type) + 1)
                    dismiss()
                }
            ) {
                Text(
                    text = type,
                    color = MaterialTheme.colors.onPrimary,
                )
            }
        }
    }
}