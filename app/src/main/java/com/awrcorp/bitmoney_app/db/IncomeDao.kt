package com.awrcorp.bitmoney_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awrcorp.bitmoney_app.vo.Income

@Dao
interface IncomeDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllIncomes(income : List<Income>)

    @Query("SELECT * FROM income")
    fun getIncomes() : LiveData<List<Income>>
}