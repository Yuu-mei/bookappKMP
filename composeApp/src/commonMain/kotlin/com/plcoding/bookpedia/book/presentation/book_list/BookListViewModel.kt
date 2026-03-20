package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(private val bookRepository: BookRepository) : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    private var cachedBooks = emptyList<Book>()
    private var searchJob: Job? = null

    init {
        if(cachedBooks.isEmpty()){
            observeSearchQuery()
        }
    }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {

            }

            is BookListAction.OnSearchQueryChanged -> {
                _state.update { state ->
                    state.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> {
                _state.update { state ->
                    state.copy(selectedTabIndex = action.selectedIndex)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResult = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        bookRepository
            .searchBooks(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResult = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        searchResult = emptyList(),
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}