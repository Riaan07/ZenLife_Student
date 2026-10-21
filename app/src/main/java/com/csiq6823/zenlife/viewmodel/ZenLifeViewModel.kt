package com.csiq6823.zenlife.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.csiq6823.zenlife.ZenLifeApplication
import com.csiq6823.zenlife.data.WellnessLog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ZenLifeViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = (application as ZenLifeApplication).database.wellnessDao()
    val allLogs: LiveData<List<WellnessLog>> = dao.getAllLogs().asLiveData()

    // 5-minute cooldown between logging the same activity type (Walking / Scrolling)
    private val COOLDOWN_PERIOD_MS = 5 * 60 * 1000

    fun logActivity(type: String) {
        viewModelScope.launch {
            val currentLogs = dao.getAllLogs().first()
            val dynamicRecentLog = currentLogs.firstOrNull { it.type == type }
            val currentTime = System.currentTimeMillis()

            // Enforce sequential interval limits
            if (dynamicRecentLog != null && (currentTime - dynamicRecentLog.timestamp) < COOLDOWN_PERIOD_MS) {
                if (type == "WALKING" || type == "DOOM_SCROLLING") {
                    val remainingSeconds = (COOLDOWN_PERIOD_MS - (currentTime - dynamicRecentLog.timestamp)) / 1000
                    Toast.makeText(
                        getApplication(),
                        "⏳ Active session exists. Wait ${remainingSeconds}s for time to lapse.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }
            }

            // Assign customized interval chunks per explicit rule targets
            val duration = when (type) {
                "WALKING" -> 15
                "DOOM_SCROLLING" -> 5
                "HYDRATION" -> 1 // Increments count index unit by 1 glass
                else -> 0
            }

            // Calculate active metabolic expenditure metrics (e.g., 4 kcal per minute of walking)
            val kcal = if (type == "WALKING") duration * 4 else 0

            dao.insertLog(
                WellnessLog(
                    type = type,
                    durationMinutes = duration,
                    caloriesBurned = kcal
                )
            )
            Toast.makeText(getApplication(), "✅ Logged $type session!", Toast.LENGTH_SHORT).show()
        }
    }

    fun clearLogs() {
        viewModelScope.launch { dao.clearAllLogs() }
    }
}