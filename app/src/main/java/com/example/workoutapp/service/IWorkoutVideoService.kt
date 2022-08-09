package com.example.workoutapp.service

import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.models.WorkoutVideo

interface IWorkoutVideoService {
    fun getWorkoutGroups(callback: WorkoutGroupSearchCallback)
    fun getWorkoutPrograms(callback: WorkoutGroupSearchCallback)
    fun getWorkoutCollections(callback: WorkoutGroupSearchCallback)
    fun getVideos(callback: WorkoutVideosSearchCallback)
    fun getVideo(videoId: String) : WorkoutVideo?

    interface WorkoutVideosSearchCallback {
        fun onWorkoutVideosFound (returnedWorkoutVideos : List<WorkoutVideo>)
    }

    interface WorkoutGroupSearchCallback {
        fun onWorkoutGroupsFound (returnedWorkoutGroups : List<WorkoutGroup>)
    }
}