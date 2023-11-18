package com.example.contestifyme.startOnBoard.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [AppUser::class],
    version = 1,
    exportSchema = false
)
abstract class OnBoardDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: OnBoardDatabase? = null
        fun getDatabase(context: Context): OnBoardDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, OnBoardDatabase::class.java, "onboard_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}