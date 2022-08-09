package com.example.workoutapp.service

import com.example.workoutapp.models.WorkoutGroup
import com.example.workoutapp.models.WorkoutVideo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.converter.gson.GsonConverterFactory

class RemoteWorkoutVideoService(val baseURL: String): IWorkoutVideoService {

    private var videoCache :MutableList<WorkoutVideo> = mutableListOf()

    interface WorkoutVideoService {
        @get:GET("videos.json")
        val getWorkoutVideos: Call<List<WorkoutVideo>>
    }

    interface WorkoutGroupsService {
        @get:GET("groups.json")
        val getWorkoutGroups: Call<List<WorkoutGroup>>
    }

    interface WorkoutProgramsService {
        @get:GET("programs.json")
        val getWorkoutPrograms: Call<List<WorkoutGroup>>
    }

    interface WorkoutCollectionsService {
        @get:GET("collections.json")
        val getWorkoutCollections: Call<List<WorkoutGroup>>
    }

    override fun getWorkoutGroups(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val retrofit = buildRetrofit()
        val workoutProgramsClient: WorkoutGroupsService =
            retrofit.create(WorkoutGroupsService::class.java)
        getGroups(workoutProgramsClient.getWorkoutGroups, callback)
    }

    override fun getWorkoutPrograms(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val retrofit = buildRetrofit()
        val workoutProgramsClient: WorkoutProgramsService =
            retrofit.create(WorkoutProgramsService::class.java)
        getGroups(workoutProgramsClient.getWorkoutPrograms, callback)
    }

    override fun getWorkoutCollections(callback: IWorkoutVideoService.WorkoutGroupSearchCallback) {
        val retrofit = buildRetrofit()
        val workoutCollectionsClient: WorkoutCollectionsService =
            retrofit.create(WorkoutCollectionsService::class.java)
        getGroups(workoutCollectionsClient.getWorkoutCollections, callback)
    }

    override fun getVideos(callback: IWorkoutVideoService.WorkoutVideosSearchCallback) {
        val retrofit = buildRetrofit()
        val workoutVideosClient: WorkoutVideoService =
            retrofit.create(WorkoutVideoService::class.java)
        val workoutVideoResults: Call<List<WorkoutVideo>> = workoutVideosClient.getWorkoutVideos

        workoutVideoResults.enqueue(object : Callback<List<WorkoutVideo>> {
            override fun onResponse(call: Call<List<WorkoutVideo>>, response: Response<List<WorkoutVideo>>) {
                videoCache.clear()
                videoCache.addAll(response.body()!!)
                callback.onWorkoutVideosFound(response.body()!!)
            }
            override fun onFailure(call: Call<List<WorkoutVideo>>, t: Throwable) {
            }
        })
    }

    override fun getVideo(
        videoId: String
    ): WorkoutVideo? {
        return videoCache.find {
            it.id == videoId
        }
    }

    private fun getGroups(workoutGroupResults: Call<List<WorkoutGroup>>, callback: IWorkoutVideoService.WorkoutGroupSearchCallback): List<WorkoutGroup> {

        val videoResults: MutableList<WorkoutGroup> = mutableListOf()

        workoutGroupResults.enqueue(object : Callback<List<WorkoutGroup>> {
            override fun onResponse(call: Call<List<WorkoutGroup>>, response: Response<List<WorkoutGroup>>) {
                callback.onWorkoutGroupsFound(response.body()!!)
                videoResults.addAll(response.body()!!)
            }

            override fun onFailure(call: Call<List<WorkoutGroup>>, t: Throwable) {
            }
        })

        return videoResults
    }

    private fun buildRetrofit(): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = retrofitBuilder.build()
        return retrofit
    }
}