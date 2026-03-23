package com.plcoding.bookpedia.book.domain

data class SearchFilters(
    val query: String,
    val author: String = "",
    val language: String = "",
    val yearFrom: String = "",
    val yearTo: String = ""
)
