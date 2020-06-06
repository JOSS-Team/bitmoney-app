package com.awrcorp.bitmoney_app.injection

import android.content.Context
import com.awrcorp.bitmoney_app.repository.AppRepository

object Injection {
    fun provideRepository(context: Context): AppRepository {
        return AppRepository.getInstance(context)
    }
}