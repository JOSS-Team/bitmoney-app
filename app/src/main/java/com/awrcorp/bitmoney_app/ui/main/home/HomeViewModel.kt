package com.awrcorp.bitmoney_app.ui.main.home

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class HomeViewModel(private val appRepository: AppRepository): ViewModel() {
    private val userId = appRepository.id
    val histories = appRepository.getHistories(userId)
    val user = appRepository.getUser(userId)
}