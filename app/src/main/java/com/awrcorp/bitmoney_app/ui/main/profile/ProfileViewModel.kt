package com.awrcorp.bitmoney_app.ui.main.profile

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository
import com.awrcorp.bitmoney_app.utils.Anicantik

class ProfileViewModel(private val appRepository: AppRepository): ViewModel() {
    val userId = appRepository.id
    val user = appRepository.getUser(userId)
    fun updateUser(userId : Int, name: String, email: String, password: String, balance : Int) =
            appRepository.updateUser(userId, name, email, password, balance)
}