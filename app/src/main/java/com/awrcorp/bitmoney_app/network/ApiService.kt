package com.awrcorp.bitmoney_app.network

import com.awrcorp.bitmoney_app.vo.Income
import com.awrcorp.bitmoney_app.vo.Outcome
import com.awrcorp.bitmoney_app.vo.User
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login/")
    fun login(@Field("email") email: String,
              @Field("password") password: String): Call<AuthResponse>

    @FormUrlEncoded
    @POST("user/")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String) : Call<User>

    @FormUrlEncoded
    @PUT("user/{userId}")
    fun updateUser(@Path("userId") userId : Int,
                   @Field("name") name: String,
                   @Field("email") email: String,
                   @Field("password") password: String,
                   @Field("balance") balance: Int) : Call<User>

    @FormUrlEncoded
    @PUT("user/{userId}")
    fun updateBalance(@Path("userId") userId : Int,
                      @Field("balance") balance: Int) : Call<User>

    @GET("user/{userId}")
    fun getUser(@Path("userId") userId : Int) : Call<User>

    @FormUrlEncoded
    @POST("income/")
    fun addIncome(@Field("name") name: String,
                  @Field("amount") amount: Int,
                  @Field("date") date: String,
                  @Field("user") user: Int) : Call<Income>

    @DELETE("income/{incomeId}")
    fun deleteIncome(@Path("incomeId") incomeId : Int) : Call<Unit>

    @GET("incomes/{userId}")
    fun getIncomes(@Path("userId") userId : Int) : Call<List<Income>>

    @FormUrlEncoded
    @POST("outcome/")
    fun addOutcome(@Field("name") name: String,
                   @Field("amount") amount: Int,
                   @Field("category") category: String,
                   @Field("date") date: String,
                   @Field("isPlan") isPlan: Boolean,
                   @Field("user") user: Int) : Call<Outcome>

    @FormUrlEncoded
    @POST("outcome/")
    fun addPlanning(@Field("name") name: String,
                   @Field("amount") amount: Int,
                   @Field("category") category: String,
                   @Field("isPlan") isPlan: Boolean,
                   @Field("user") user: Int) : Call<Outcome>

    @DELETE("outcome/{outcomeId}")
    fun deleteOutcome(@Path("outcomeId") outcomeId : Int) : Call<Unit>

    @GET("outcomes/{userId}")
    fun getOutcomes(@Path("userId") userId : Int) : Call<List<Outcome>>
}