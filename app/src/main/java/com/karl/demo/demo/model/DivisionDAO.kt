package com.karl.demo.demo.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.karl.demo.demo.entity.DivisionEntity

@Dao
interface DivisionDAO {
    @Query("DELETE FROM division")
    fun deleteAll()

    @Insert
    fun insertDivision(entity: DivisionEntity)

    @Query("SELECT * FROM division")
    fun getCurrentDivision(): MutableList<DivisionEntity>
}