package com.awrcorp.bitmoney_app.ui.auth.register

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.injection.Injection
import com.awrcorp.bitmoney_app.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class RegisterViewModelFactory private constructor(private val appRepository: AppRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        private var instance: RegisterViewModelFactory? = null
        fun getInstance(context: Context): RegisterViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: RegisterViewModelFactory(Injection.provideRepository(context))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RegisterViewModel(appRepository) as T
    }
}