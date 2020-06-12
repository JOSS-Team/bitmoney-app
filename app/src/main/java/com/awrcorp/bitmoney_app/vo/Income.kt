package com.awrcorp.bitmoney_app.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Income(
        @PrimaryKey(autoGenerate = false)
        var incomeId : Int,
        var name : String,
        var amount : Int,
        var date : String,
        var user : Int
)