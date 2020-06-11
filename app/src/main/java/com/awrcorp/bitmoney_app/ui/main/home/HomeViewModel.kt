package com.awrcorp.bitmoney_app.ui.main.home

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class HomeViewModel(private val appRepository: AppRepository): ViewModel() {
    val userId = appRepository.id
    val histories = appRepository.getHistories(userId)
    val user = appRepository.getUser(userId)
    fun updateUser(userId: Int, nama: String, email: String, password: String, balance: Int) =
            appRepository.updateUser(userId, nama, email, password, balance)
    fun addOutcome(name: String, amount: Int, category: String, date: String, isPlan: Boolean, user: Int) =
            appRepository.addOutcome(name, amount, category, date, isPlan, user)
}