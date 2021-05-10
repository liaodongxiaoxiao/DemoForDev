package com.karl.demo.demo.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.karl.demo.demo.entity.DivisionEntity

@Database(entities = arrayOf(DivisionEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun divisionDao(): DivisionDAO
}