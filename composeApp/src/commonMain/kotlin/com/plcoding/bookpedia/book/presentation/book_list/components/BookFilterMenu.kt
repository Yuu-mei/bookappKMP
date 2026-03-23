package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.apply_filters
import cmp_bookpedia.composeapp.generated.resources.author_hint
import cmp_bookpedia.composeapp.generated.resources.author_title
import cmp_bookpedia.composeapp.generated.resources.close_hint
import cmp_bookpedia.composeapp.generated.resources.date_title
import cmp_bookpedia.composeapp.generated.resources.filters_title
import cmp_bookpedia.composeapp.generated.resources.lang_select
import cmp_bookpedia.composeapp.generated.resources.language_title
import cmp_bookpedia.composeapp.generated.resources.year_from_title
import cmp_bookpedia.composeapp.generated.resources.year_to_title
import com.plcoding.bookpedia.book.domain.SearchFilters
import com.plcoding.bookpedia.book.domain.supportedLanguages
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookFilterMenu(
    searchFilters: SearchFilters,
    onAuthorFilterChanged: (String) -> Unit,
    onLanguageFilterChanged: (String) -> Unit,
    onFromDateFilterChanged: (String) -> Unit,
    onToDateFilterChanged: (String) -> Unit,
    onApplyFilters: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var fromDateShow by remember { mutableStateOf(false) }
    var toDateShow by remember { mutableStateOf(false) }
    val fromDatePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val toDatePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val calendar = Calendar.getInstance()

    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        ModalDrawerSheet {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
            ) {
                Spacer(Modifier.height(12.dp))
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(Res.string.filters_title),
                    style = MaterialTheme.typography.headlineSmall
                )
                HorizontalDivider()

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(Res.string.author_title),
                    style = MaterialTheme.typography.titleMedium
                )
                OutlinedTextField(
                    modifier = Modifier
                        .background(
                            shape = RoundedCornerShape(75),
                            color = DesertWhite
                        )
                        .minimumInteractiveComponentSize(),
                    value = searchFilters.author,
                    onValueChange = { onAuthorFilterChanged(it) },
                    shape = RoundedCornerShape(75),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        cursorColor = DarkBlue,
                        focusedBorderColor = SandYellow
                    ),
                    placeholder = {
                        Text(
                            text = stringResource(Res.string.author_hint)
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = searchFilters.author.isNotBlank()
                        ) {
                            IconButton(
                                onClick = { onAuthorFilterChanged("") }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = stringResource(Res.string.close_hint),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(Res.string.language_title),
                    style = MaterialTheme.typography.titleMedium
                )
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = DesertWhite
                            )
                            .clickable(
                                onClick = { expanded = true }
                            ),
                        text = searchFilters.language.ifEmpty { stringResource(Res.string.lang_select) },
                        style = MaterialTheme.typography.titleSmall
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        supportedLanguages.forEach { lang ->
                            DropdownMenuItem(
                                text = { Text(text = stringResource(lang.name)) },
                                onClick = {
                                    onLanguageFilterChanged(lang.code)
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))


                Text(
                    modifier = Modifier.padding(12.dp),
                    text = stringResource(Res.string.date_title),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "${stringResource(Res.string.year_from_title)} ${searchFilters.yearFrom}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            IconButton(
                                onClick = { fromDateShow = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = stringResource(Res.string.year_from_title)
                                )
                            }
                        }
                        if (fromDateShow) {
                            DatePickerDialog(
                                onDismissRequest = { fromDateShow = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        val selectedYear =
                                            fromDatePickerState.selectedDateMillis?.let {
                                                calendar.timeInMillis = it
                                                calendar.get(Calendar.YEAR)
                                            }.toString()
                                        onFromDateFilterChanged(selectedYear)
                                        fromDateShow = false
                                    }) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        fromDateShow = false
                                    }) {
                                        Text("Cancel")
                                    }
                                }
                            ) {
                                DatePicker(state = fromDatePickerState)
                            }
                        }
                    }

                    Column() {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(12.dp),
                                text = "${stringResource(Res.string.year_to_title)} ${searchFilters.yearTo}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            IconButton(
                                onClick = { toDateShow = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    contentDescription = stringResource(Res.string.year_from_title)
                                )
                            }
                        }
                        if (toDateShow) {
                            DatePickerDialog(
                                onDismissRequest = { toDateShow = false },
                                confirmButton = {
                                    TextButton(onClick = {
                                        val selectedYear =
                                            toDatePickerState.selectedDateMillis?.let {
                                                calendar.timeInMillis = it
                                                calendar.get(Calendar.YEAR)
                                            }.toString()
                                        onToDateFilterChanged(selectedYear)
                                        toDateShow = false
                                    }) {
                                        Text("OK")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = {
                                        toDateShow = false
                                    }) {
                                        Text("Cancel")
                                    }
                                }
                            ) {
                                DatePicker(state = toDatePickerState)
                            }
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        onApplyFilters()
                    },
                    shape = RoundedCornerShape(100),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SandYellow,
                        contentColor = DesertWhite
                    )
                ) {
                    Text(
                        text = stringResource(Res.string.apply_filters),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }
    }
}