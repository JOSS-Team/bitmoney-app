package com.awrcorp.bitmoney_app.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
        @PrimaryKey(autoGenerate = false)
        var userId: Int? = null,
        var name: String? = null,
        var email: String? = null,
        var password: String? = null,
        var balance: Int = 0,
        var photo: String? = null
)