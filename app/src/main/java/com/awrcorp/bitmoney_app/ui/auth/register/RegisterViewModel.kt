package com.awrcorp.bitmoney_app.ui.auth.register

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class RegisterViewModel(private val appRepository: AppRepository): ViewModel() {
    fun register(name: String, email: String, password: String) = appRepository.register(name, email, password)
}