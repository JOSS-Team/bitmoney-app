package com.awrcorp.bitmoney_app.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class LoginViewModel(private val appRepository: AppRepository): ViewModel() {

    fun login(email: String, password: String) = appRepository.login(email, password)
}