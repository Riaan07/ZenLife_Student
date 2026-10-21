package com.csiq6823.zenlife.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WellnessLog::class], version = 1, exportSchema = false)
abstract class ZenLifeDatabase : RoomDatabase() {
    abstract fun wellnessDao(): WellnessDao

    companion object {
        @Volatile private var INSTANCE: ZenLifeDatabase? = null
        fun getDatabase(context: Context): ZenLifeDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ZenLifeDatabase::class.java,
                    "zenlife_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
