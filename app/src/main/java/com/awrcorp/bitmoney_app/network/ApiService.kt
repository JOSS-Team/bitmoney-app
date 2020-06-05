package com.awrcorp.bitmoney_app.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("login/")
    fun login(@Field("email") email: String,
              @Field("password") password: String): Call<AuthResponse>

    @FormUrlEncoded
    @POST("user/")
    fun register(@Field("name") name: String,
                 @Field("email") email: String,
                 @Field("password") password: String)
}