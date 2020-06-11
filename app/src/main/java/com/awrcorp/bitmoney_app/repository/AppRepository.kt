package com.awrcorp.bitmoney_app.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.network.AuthResponse
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Income
import com.awrcorp.bitmoney_app.vo.Outcome
import com.awrcorp.bitmoney_app.vo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Path

class AppRepository private constructor(private val context: Context) {

    private val api = ApiClient.instance
    val id = Anicantik.getInstance(context).getId()

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

    fun register(name: String, email: String, password: String): LiveData<Int> {
        val responseCode = MutableLiveData<Int>()
        api.register(name, email, password).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val userId = response.body()?.userId
                if (userId != null) {
                    responseCode.value = response.body()?.userId
                } else {
                    //account not found
                    responseCode.value = 404
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                //network unavailable or request timeout
                responseCode.value = 408
            }
        })
        return responseCode
    }

    fun getUser(userId : Int) : LiveData<User> {
        val user = MutableLiveData<User>()
        api.getUser(userId).enqueue(object : Callback<User>{
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val uid = response.body()?.userId
                if (uid != null) {
                    user.value = response.body()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        return user
    }

    fun updateUser(userId : Int, name: String, email: String, password: String, balance : Int) : LiveData<String> {
        val message = MutableLiveData<String>()
        api.updateUser(userId, name, email, password, balance).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val uid = response.body()?.userId
                if (uid != null) {
                    message.value = "Update successful"
                } else {
                    message.value = "update failed"
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                message.value = "update failed, check your connection"

            }
        })
        return message
    }

    fun updateBalance(userId : Int, balance : Int) : LiveData<Int> {
        val message = MutableLiveData<Int>()
        api.updateBalance(userId, balance).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val uid = response.body()?.userId
                if (uid != null) {
                    message.value = balance
                } else {
                    message.value = 0
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                message.value = 1
                t.printStackTrace()
            }
        })
        return message
    }

    fun addOutcome(name: String, amount: Int, category: String, date: String, isPlan: Boolean, user: Int) : LiveData<String> {
        val message = MutableLiveData<String>()
        api.addOutcome(name, amount, category, date, isPlan, user).enqueue(object : Callback<Outcome>{
            override fun onResponse(call: Call<Outcome>, response: Response<Outcome>) {
                val outcomeId = response.body()?.outcomeId
                if(outcomeId != null){
                    message.value = "Success"
                } else
                    message.value = "failure"
            }

            override fun onFailure(call: Call<Outcome>, t: Throwable) {
                message.value = "failure"
            }
        })
        return message
    }

    fun deleteOutcome(outcomeId: Int) : Unit {
        api.deleteOutcome(outcomeId).enqueue(object : Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun addPlanning(name: String, amount: Int, category: String, isPlan: Boolean, user: Int) : LiveData<String> {
        val message = MutableLiveData<String>()
        api.addPlanning(name, amount, category, isPlan, user).enqueue(object : Callback<Outcome>{
            override fun onResponse(call: Call<Outcome>, response: Response<Outcome>) {
                val outcomeId = response.body()?.outcomeId
                if(outcomeId != null){
                    message.value = "Success"
                } else
                    message.value = "failure"
            }

            override fun onFailure(call: Call<Outcome>, t: Throwable) {
                message.value = "failure"
            }
        })
        return message
    }

    fun getPlans(userId: Int) : LiveData<List<Outcome>> {
        val plans = MutableLiveData<List<Outcome>>()
        var planList: MutableList<Outcome> = mutableListOf()
        api.getOutcomes(userId).enqueue(object : Callback<List<Outcome>> {
            override fun onResponse(call: Call<List<Outcome>>, response: Response<List<Outcome>>) {
                val planResponse = response.body()
                if (planResponse != null){
                    planResponse.forEach {
                        if (it.isPlan && it.user == userId){
                            planList.add(it)
                        }
                    }
                    plans.value = planList
                }
            }

            override fun onFailure(call: Call<List<Outcome>>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        return plans
    }

    fun getHistories(userId: Int) : LiveData<List<Outcome>> {
        val histories = MutableLiveData<List<Outcome>>()
        var historyList: MutableList<Outcome> = mutableListOf()
        api.getOutcomes(userId).enqueue(object : Callback<List<Outcome>> {
            override fun onResponse(call: Call<List<Outcome>>, response: Response<List<Outcome>>) {
                val historyResponse = response.body()
                if (historyResponse != null){
                    historyResponse.forEach {
                        if (!it.isPlan && it.user == userId){
                            historyList.add(it)
                        }
                    }
                    histories.value = historyList
                }
            }

            override fun onFailure(call: Call<List<Outcome>>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        return histories
    }

    fun addIncome(name: String, amount: Int, date: String, user: Int) : LiveData<Int> {
        val responseCode = MutableLiveData<Int>()
        api.addIncome(name, amount, date, user).enqueue(object : Callback<Income> {
            override fun onResponse(call: Call<Income>, response: Response<Income>) {
                val userId = response.body()?.incomeId
                if (userId != null) {
                    responseCode.value = response.body()?.amount
                } else {
                    //account not found
                    responseCode.value = 404
                }
            }
            override fun onFailure(call: Call<Income>, t: Throwable) {
                //network unavailable or request timeout
                responseCode.value = 408
            }
        })
        return responseCode
    }

    fun getIncomes(userId: Int) : LiveData<List<Income>> {
        val incomes = MutableLiveData<List<Income>>()
        var incomeList : MutableList<Income> = mutableListOf()
        api.getIncomes(userId).enqueue(object : Callback<List<Income>> {
            override fun onResponse(call: Call<List<Income>>, response: Response<List<Income>>) {
                val incomeResponse = response.body()
                if (incomeResponse != null){
                    incomeResponse.forEach {
                        if (it.user == userId){
                            incomeList.add(it)
                        }
                    }
                    incomes.value = incomeList
                }
            }

            override fun onFailure(call: Call<List<Income>>, t: Throwable) {
                Log.e(TAG, t.toString())
            }
        })
        return incomes
    }
}