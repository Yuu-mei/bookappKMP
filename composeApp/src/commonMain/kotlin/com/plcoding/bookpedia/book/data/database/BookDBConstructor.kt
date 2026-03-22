package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDBConstructor : RoomDatabaseConstructor<FavoriteBookDB> {
    override fun initialize(): FavoriteBookDB
}