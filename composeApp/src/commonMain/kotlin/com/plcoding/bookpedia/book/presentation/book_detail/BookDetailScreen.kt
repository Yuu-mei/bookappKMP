@file:OptIn(ExperimentalLayoutApi::class)

package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.languages
import cmp_bookpedia.composeapp.generated.resources.pages
import cmp_bookpedia.composeapp.generated.resources.rating
import cmp_bookpedia.composeapp.generated.resources.star_icon
import cmp_bookpedia.composeapp.generated.resources.synopsis
import cmp_bookpedia.composeapp.generated.resources.synopsis_unavailable
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBackground
import com.plcoding.bookpedia.book.presentation.book_detail.components.BookChip
import com.plcoding.bookpedia.book.presentation.book_detail.components.ChipSize
import com.plcoding.bookpedia.book.presentation.book_detail.components.TitledContent
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import java.util.Locale.getDefault
import kotlin.math.round

@Composable
fun BookDetailScreen(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    BlurredImageBackground(
        modifier = Modifier.fillMaxSize(),
        imageURL = state.book?.imageURL,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(BookDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick)
        }
    ) {
        if (state.book != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.book.averageRating?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.rating)
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(rating * 10.0) / 10.0}"
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = stringResource(Res.string.star_icon),
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                    state.book.numPages?.let { pages ->
                        TitledContent(
                            title = stringResource(Res.string.pages)
                        ) {
                            BookChip {
                                Text(
                                    text = "$pages"
                                )
                            }
                        }
                    }
                }

                if (state.book.languages.isNotEmpty()) {
                    TitledContent(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        title = stringResource(Res.string.languages)
                    ) {
                        FlowRow(
                            modifier = Modifier.wrapContentSize(Alignment.Center),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            state.book.languages.forEach { language ->
                                BookChip(
                                    modifier = Modifier.padding(2.dp),
                                    size = ChipSize.SMALL
                                ) {
                                    Text(
                                        text = language.uppercase(getDefault()),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp),
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge
                )

                if (state.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        text = state.book.description
                            ?: stringResource(Res.string.synopsis_unavailable),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) Color.Black.copy(alpha = 0.4f) else Color.Black
                    )
                }
            }
        }
    }
}