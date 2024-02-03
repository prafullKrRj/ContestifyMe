package com.prafull.contestifyme.features.problemsFeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prafull.contestifyme.features.problemsFeature.data.local.entities.ProblemsEntity
import com.prafull.contestifyme.features.problemsFeature.data.local.entities.ProblemsTypeConverter


@Database(
    entities = [ProblemsEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ProblemsTypeConverter::class)
abstract class ProblemsDatabase: RoomDatabase() {
    abstract fun problemsDao(): ProblemsDao

    companion object {
        @Volatile
        private var INSTANCE: ProblemsDatabase? = null
        fun getDatabase(context: Context) : ProblemsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, ProblemsDatabase::class.java, "problems_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}