package com.example.workoutapp.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.WorkoutApp
import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.service.IWorkoutVideoService

class WorkoutGroupsViewModel : ViewModel() {

    private val workoutGroups: MutableLiveData<List<WorkoutGroup>> = MutableLiveData()
    val workoutVideoService : IWorkoutVideoService = WorkoutApp.instance.services

    init {
        workoutVideoService.getWorkoutGroups(object:
            IWorkoutVideoService.WorkoutGroupSearchCallback {
            override fun onWorkoutGroupsFound(returnedWorkoutGroups: List<WorkoutGroup>) {
                workoutGroups.value = returnedWorkoutGroups
            }

        })
    }

    fun getWorkoutGroups() : LiveData<List<WorkoutGroup>> {
        return workoutGroups
    }
}