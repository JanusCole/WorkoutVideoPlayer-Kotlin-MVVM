package com.example.workoutapp.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.WorkoutApp
import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.service.IWorkoutVideoService

class WorkoutProgramsViewModel : ViewModel() {

    private val workoutPrograms = MutableLiveData<List<WorkoutGroup>>()
    private val workoutVideoService : IWorkoutVideoService = WorkoutApp.instance.services

    init {
        workoutVideoService.getWorkoutPrograms(object:
            IWorkoutVideoService.WorkoutGroupSearchCallback {
            override fun onWorkoutGroupsFound(returnedWorkoutGroups: List<WorkoutGroup>) {
                workoutPrograms.value = returnedWorkoutGroups
            }
        })
    }

    fun getWorkoutPrograms() : LiveData<List<WorkoutGroup>> {
        return workoutPrograms
    }
}