package com.awrcorp.bitmoney_app.ui.main.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.injection.Injection
import com.awrcorp.bitmoney_app.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory private constructor(private val appRepository: AppRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        private var instance: ProfileViewModelFactory? = null
        fun getInstance(context: Context): ProfileViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ProfileViewModelFactory(Injection.provideRepository(context))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(appRepository) as T
    }
}