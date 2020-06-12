package com.awrcorp.bitmoney_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.awrcorp.bitmoney_app.vo.Income
import com.awrcorp.bitmoney_app.vo.Outcome
import com.awrcorp.bitmoney_app.vo.User

@Database(
        entities = [User::class, Income::class, Outcome::class],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getIncomeDao(): IncomeDao
    abstract fun getOutcomeDao(): OutcomeDao

    object DatabaseBuilder {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = buldDatabase(context)
                }
            }
            return INSTANCE!!
        }

        private fun buldDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "BitMoney.db"
                ).build()
    }
}