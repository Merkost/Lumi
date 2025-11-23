package com.merkost.lumi.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.merkost.lumi.data.local.dao.MovieDao
import com.merkost.lumi.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "lumi_database"
    }
}
