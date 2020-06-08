package com.awrcorp.bitmoney_app.ui.main.planning

import androidx.lifecycle.ViewModel
import com.awrcorp.bitmoney_app.repository.AppRepository

class PlanningViewModel (private val appRepository: AppRepository): ViewModel() {
    private val userId = appRepository.id
    val plans = appRepository.getPlans(userId)
}