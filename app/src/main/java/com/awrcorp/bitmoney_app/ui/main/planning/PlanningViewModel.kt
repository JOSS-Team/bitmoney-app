package com.awrcorp.bitmoney_app.ui.main.planning

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class PlanningViewModel (private val appRepository: AppRepository): ViewModel() {
    val userId = appRepository.id
    val plans = appRepository.getPlans(userId)
    fun addPlanning(name: String, amount: Int, category: String, isPlan: Boolean, user: Int) =
            appRepository.addPlanning(name, amount, category, isPlan, user)
}