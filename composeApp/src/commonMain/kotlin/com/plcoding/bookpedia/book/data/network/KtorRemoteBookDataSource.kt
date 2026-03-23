package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDTO
import com.plcoding.bookpedia.book.data.dto.SearchedResponseDTO
import com.plcoding.bookpedia.book.domain.SearchFilters
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {
    override suspend fun searchBooks(query: String, resultLimit: Int?): Result<SearchedResponseDTO, DataError.Remote>{
        return safeCall<SearchedResponseDTO> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ){
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }

    override suspend fun searchBooksWithFilters(
        filters: SearchFilters,
        resultLimit: Int?
    ): Result<SearchedResponseDTO, DataError.Remote> {
        return safeCall<SearchedResponseDTO> {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                parameter("q", filters.query)
                if (resultLimit != null) parameter("limit", resultLimit)
                if (filters.language.isNotEmpty()) parameter("language", filters.language)
                if (filters.author.isNotEmpty()) parameter("author", filters.author)
                if (filters.yearTo.isNotEmpty() && filters.yearFrom.isNotEmpty()) parameter(
                    "published_in",
                    "${filters.yearFrom}-${filters.yearTo}"
                )
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }

    override suspend fun getBookDetails(bookWorkID: String): Result<BookWorkDTO, DataError.Remote> {
        return safeCall<BookWorkDTO> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkID.json"
            )
        }
    }
}