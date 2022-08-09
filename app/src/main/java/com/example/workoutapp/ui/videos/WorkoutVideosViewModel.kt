package com.example.workoutapp.ui.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.WorkoutApp
import com.example.workoutapp.models.WorkoutVideo
import com.example.workoutapp.service.IWorkoutVideoService

class WorkoutVideosViewModel : ViewModel() {

    private val workoutVideos = MutableLiveData<List<WorkoutVideo>>()
    val workoutVideoService : IWorkoutVideoService = WorkoutApp.instance.services

    init {
        workoutVideoService.getVideos(object:
            IWorkoutVideoService.WorkoutVideosSearchCallback {
            override fun onWorkoutVideosFound(returnedWorkoutVideos: List<WorkoutVideo>) {
                workoutVideos.value = returnedWorkoutVideos
            }

        })
    }
    fun getWorkoutVideos() : LiveData<List<WorkoutVideo>> {
        return workoutVideos
    }
}