package com.awrcorp.bitmoney_app.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Outcome(
        @PrimaryKey(autoGenerate = false)
        var outcomeId : Int,
        var name : String,
        var category : String,
        var amount : Int,
        var date : String,
        var isPlan : Boolean = false,
        var user : Int
)