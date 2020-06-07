package com.awrcorp.bitmoney_app.ui.main.home

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class HomeViewModel(private val appRepository: AppRepository): ViewModel() {
    fun getUser(userId: Int) = appRepository.getUser(userId)
}