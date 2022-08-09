package com.example.workoutapp.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.WorkoutApp
import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.service.IWorkoutVideoService

class WorkoutCollectionsViewModel : ViewModel() {

    private val workoutCollections = MutableLiveData<List<WorkoutGroup>>()
    val repository : IWorkoutVideoService = WorkoutApp.instance.services

    init {
        repository.getWorkoutCollections(object:
            IWorkoutVideoService.WorkoutGroupSearchCallback {
            override fun onWorkoutGroupsFound(returnedWorkoutGroups: List<WorkoutGroup>) {
                workoutCollections.value = returnedWorkoutGroups
            }

        })
    }

    fun getWorkoutCollections() : LiveData<List<WorkoutGroup>> {
        return workoutCollections
    }
}