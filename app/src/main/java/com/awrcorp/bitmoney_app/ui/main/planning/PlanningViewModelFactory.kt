package com.awrcorp.bitmoney_app.ui.main.planning

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.injection.Injection
import com.awrcorp.bitmoney_app.repository.AppRepository

@Suppress("UNCHECKED_CAST")
class PlanningViewModelFactory private constructor(private val appRepository: AppRepository)
    : ViewModelProvider.NewInstanceFactory(){

    companion object {
        private var instance: PlanningViewModelFactory? = null
        fun getInstance(context: Context): PlanningViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: PlanningViewModelFactory(Injection.provideRepository(context))
                }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PlanningViewModel(appRepository) as T
    }
}