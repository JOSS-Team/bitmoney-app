package com.awrcorp.bitmoney_app.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.network.AuthResponse
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Outcome
import com.awrcorp.bitmoney_app.vo.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository private constructor(private val context: Context) {

    private val api = ApiClient.instance

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

    fun updateUser(userId : Int, name: String, email: String, password: String, balance : Int, photo : String) : LiveData<String> {
        val message = MutableLiveData<String>()
        api.updateUser(userId, name, email, password, balance, photo).enqueue(object : Callback<User> {
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

    fun getPlans(userId: Int) : LiveData<List<Outcome>> {
        val plans = MutableLiveData<List<Outcome>>()
        val planList: List<Outcome> = emptyList()
        api.getOutcomes(userId).enqueue(object : Callback<List<Outcome>> {
            override fun onResponse(call: Call<List<Outcome>>, response: Response<List<Outcome>>) {
                val planResponse = response.body()
                if (planResponse != null){
                    planResponse.forEach {
                        if (it.isPlan){
                            planList.toMutableList().add(it)
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
}