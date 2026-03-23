package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book

sealed interface BookListAction {
    data class OnSearchQueryChanged(val query: String): BookListAction
    data class OnAuthorFilterChanged(val author: String) : BookListAction
    data class OnLanguageFilterChanged(val lang: String) : BookListAction
    data class OnFromDateFilterChanged(val year: String) : BookListAction
    data class OnToDateFilterChanged(val year: String) : BookListAction
    class OnApplyFilters() : BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class OnTabSelected(val selectedIndex: Int): BookListAction
}