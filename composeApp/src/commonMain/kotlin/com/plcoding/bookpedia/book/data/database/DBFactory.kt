package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabase

expect class DBFactory {
    fun create(): RoomDatabase.Builder<FavoriteBookDB>
}