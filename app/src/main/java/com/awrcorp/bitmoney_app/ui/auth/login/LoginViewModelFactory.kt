package com.awrcorp.bitmoney_app.ui.auth.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.injection.Injection
import com.awrcorp.bitmoney_app.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class LoginViewModelFactory private constructor(private val appRepository: AppRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        private var instance: LoginViewModelFactory? = null
        fun getInstance(context: Context): LoginViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: LoginViewModelFactory(Injection.provideRepository(context))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(appRepository) as T
    }
}