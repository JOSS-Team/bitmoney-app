package com.awrcorp.bitmoney_app.ui.main.wallet

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class WalletViewModel(private val appRepository: AppRepository): ViewModel() {
    private val userId = appRepository.id
    val incomes = appRepository.getIncomes(userId)
}