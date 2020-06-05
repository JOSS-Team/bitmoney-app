package com.awrcorp.bitmoney_app.network

data class AuthResponse(
        var status: Boolean,
        var response: String,
        var message: String?,
        var userId: Int? = null
)