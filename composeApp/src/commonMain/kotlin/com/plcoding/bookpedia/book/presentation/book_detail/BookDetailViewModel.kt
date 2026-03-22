package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    private val bookID = savedStateHandle.toRoute<Route.BookDetail>().id

    init {
        fetchBookDescription()
    }

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            is BookDetailAction.OnFavoriteClick -> {}

            else -> Unit
        }
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookID)
                .onSuccess { desc ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(description = desc),
                            isLoading = false
                        )
                    }
                }
        }
    }
}