package com.awrcorp.bitmoney_app.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awrcorp.bitmoney_app.db.AppDatabase
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.network.AuthResponse
import com.awrcorp.bitmoney_app.vo.Income
import com.awrcorp.bitmoney_app.vo.Outcome
import com.awrcorp.bitmoney_app.vo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository private constructor(private val context: Context) {

    private val api = ApiClient.instance
    private val db = AppDatabase.DatabaseBuilder.getInstance(context)

    companion object {
        private var instance: AppRepository? = null

        fun getInstance(context: Context): AppRepository =
                instance ?: synchronized(this) {
                    instance ?: AppRepository(context)
                }
    }

    fun login(email: String, password: String): LiveData<Int> {
        val responseCode = MutableLiveData<Int>()
        api.login(email, password).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val success = response.body()?.status
                if (success != null && success == 1) {
                    responseCode.value = response.body()?.userId
                } else {
                    //account not found
                    responseCode.value = 404
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                //network unavailable or request timeout
                responseCode.value = 408
            }
        })
        return responseCode
    }

    fun register(name: String, email: String, password: String) {
        TODO("Not yet implemented")
    }

    //UserDao
    fun saveUser(user : User) = db.getUserDao().saveUser(user)
    fun getUser() : LiveData<User> = db.getUserDao().getUser()

    //IncomeDao
    fun saveAllIncomes(income : List<Income>) = db.getIncomeDao().saveAllIncomes(income)
    fun getIncomes() = db.getIncomeDao().getIncomes()

    //OutcomeDao
    fun saveAllOutcomes(outcome : List<Outcome>) = db.getOutcomeDao().saveAllOutcomes(outcome)
    fun getOutcomes() : LiveData<List<Outcome>> = db.getOutcomeDao().getOutcomes()
}