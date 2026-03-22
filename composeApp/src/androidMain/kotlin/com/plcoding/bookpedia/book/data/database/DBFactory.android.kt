package com.plcoding.bookpedia.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DBFactory(
    private val context: Context
) {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDB> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(FavoriteBookDB.DB_NAME)

        return Room.databaseBuilder(context = appContext, name = dbFile.absolutePath)
    }
}