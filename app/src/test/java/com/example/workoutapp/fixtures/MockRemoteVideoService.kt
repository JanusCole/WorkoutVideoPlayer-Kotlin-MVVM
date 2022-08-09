package com.example.workoutapp.fixtures

import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.models.WorkoutVideo
import com.example.workoutapp.service.IWorkoutVideoService
import com.google.gson.Gson

class MockRemoteVideoService(val baseURL: String): IWorkoutVideoService {

    private var videoCache :MutableList<WorkoutVideo> = mutableListOf()

    override fun getWorkoutGroups(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val workoutVideoGroups = Gson().fromJson(mockVideoGroupData, Array<WorkoutGroup>::class.java).toList()
        callback.onWorkoutGroupsFound(workoutVideoGroups)
    }

    override fun getWorkoutPrograms(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val workoutVideoGroups = Gson().fromJson(mockVideoProgramsData, Array<WorkoutGroup>::class.java).toList()
        callback.onWorkoutGroupsFound(workoutVideoGroups)
    }

    override fun getWorkoutCollections(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val workoutVideoGroups = Gson().fromJson(mockVideoCollectionsData, Array<WorkoutGroup>::class.java).toList()
        callback.onWorkoutGroupsFound(workoutVideoGroups)
    }

    override fun getVideos(callback: IWorkoutVideoService.WorkoutVideosSearchCallback) {
        val workoutVideos = Gson().fromJson(mockVideoData, Array<WorkoutVideo>::class.java).toList()
        videoCache.clear()
        videoCache.addAll(workoutVideos)
        callback.onWorkoutVideosFound(workoutVideos)
    }

    override fun getVideo(videoId: String): WorkoutVideo? {
        return videoCache.find {
            it.id == videoId
        }
    }
}