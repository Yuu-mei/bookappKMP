package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDTO
import com.plcoding.bookpedia.book.data.dto.SearchedResponseDTO
import com.plcoding.bookpedia.book.domain.SearchFilters
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchedResponseDTO, DataError.Remote>

    suspend fun searchBooksWithFilters(
        filters: SearchFilters,
        resultLimit: Int? = null
    ): Result<SearchedResponseDTO, DataError.Remote>

    suspend fun getBookDetails(bookWorkID: String): Result<BookWorkDTO, DataError.Remote>
}