package com.awrcorp.bitmoney_app.ui.main.profile

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository
import com.awrcorp.bitmoney_app.utils.Anicantik

class ProfileViewModel(private val appRepository: AppRepository): ViewModel() {
    fun getUser(userId: Int) = appRepository.getUser(userId)
}