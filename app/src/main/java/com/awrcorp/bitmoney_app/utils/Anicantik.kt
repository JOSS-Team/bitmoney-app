package com.awrcorp.bitmoney_app.utils

import android.annotation.SuppressLint
import android.content.Context
import com.awrcorp.bitmoney_app.R

class Anicantik (context: Context) {
    companion object {
        private var instance: Anicantik? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Anicantik(context)
        }
    }

    private val sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferense_file_key), Context.MODE_PRIVATE)

    fun saveId(token: Int) {
        sharedPreferences.edit().putInt("id", token).apply()
    }

    fun getId() : Int = sharedPreferences.getInt("id", 0)

    @SuppressLint("CommitPrefEdits")
    fun removeId() {
        sharedPreferences.edit().remove("id").apply()
    }
}