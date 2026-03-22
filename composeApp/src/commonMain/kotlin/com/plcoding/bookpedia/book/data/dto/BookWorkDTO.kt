package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDTOSerializer::class)
data class BookWorkDTO(
    val description: String? = null
)
