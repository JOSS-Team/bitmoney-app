package com.awrcorp.bitmoney_app.ui.main.wallet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.injection.Injection
import com.awrcorp.bitmoney_app.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class WalletViewModelFactory private constructor(private val appRepository: AppRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        private var instance: WalletViewModelFactory? = null
        fun getInstance(context: Context): WalletViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: WalletViewModelFactory(Injection.provideRepository(context))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return WalletViewModel(appRepository) as T
    }
}