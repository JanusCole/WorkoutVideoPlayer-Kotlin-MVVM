package com.example.workoutapp.ui.videos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.workoutapp.WorkoutApp
import com.example.workoutapp.models.WorkoutVideo
import com.example.workoutapp.service.IWorkoutVideoService

class WorkoutVideoViewModel : ViewModel() {

    private val workoutVideo = MutableLiveData<WorkoutVideo>()
    val workoutVideoService : IWorkoutVideoService = WorkoutApp.instance.services

    fun getVideo (videoId: String) {
        workoutVideo.value = workoutVideoService.getVideo(videoId)
    }
    fun getWorkoutVideo() : LiveData<WorkoutVideo> {
        return workoutVideo
    }
}