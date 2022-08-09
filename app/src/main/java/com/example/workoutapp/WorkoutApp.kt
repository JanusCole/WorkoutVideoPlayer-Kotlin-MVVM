package com.example.workoutapp

import android.app.Application
import com.example.workoutapp.service.IWorkoutVideoService
import com.example.workoutapp.service.RemoteWorkoutVideoService

class WorkoutApp: Application() {

    companion object {
        lateinit var instance: WorkoutApp
            private set
    }

    lateinit var services: IWorkoutVideoService

    override fun onCreate() {
        WorkoutApp.instance = this
        super.onCreate()

        services = RemoteWorkoutVideoService(BuildConfig.BASE_URL)
    }

}