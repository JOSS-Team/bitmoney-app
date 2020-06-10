package com.awrcorp.bitmoney_app.ui.main.wallet

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class WalletViewModel(private val appRepository: AppRepository): ViewModel() {
    val userId = appRepository.id
    val user = appRepository.getUser(userId)
    val incomes = appRepository.getIncomes(userId)
    fun addIncome(name: String, amount: Int, date: String, user : Int) =
            appRepository.addIncome(name, amount, date, user)
    fun updateUser(userId: Int, nama: String, email: String, password: String, balance: Int) =
            appRepository.updateUser(userId, nama, email, password, balance)
}