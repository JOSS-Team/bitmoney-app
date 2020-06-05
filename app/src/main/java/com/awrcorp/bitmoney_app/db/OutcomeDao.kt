package com.awrcorp.bitmoney_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.awrcorp.bitmoney_app.vo.Outcome

@Dao
interface OutcomeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllOutcomes(outcome : List<Outcome>)

    @Query("SELECT * FROM outcome")
    fun getOutcomes() : LiveData<List<Outcome>>
}