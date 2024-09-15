package com.prafull.contestifyme.features.problemsFeature.data.local

import androidx.room.Database
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
abstract class ProblemsDatabase : RoomDatabase() {
    abstract fun problemsDao(): ProblemsDao
}