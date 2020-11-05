package com.evaluation.tests.dao

import android.content.Context
import androidx.room.Room
import com.evaluation.database.AppDatabase
import com.evaluation.utils.DATABASE_NAME

object RoomMocks {

    internal fun appDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

}