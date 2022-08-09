package com.example.workoutapp.models

import android.os.Parcelable
import com.example.workoutapp.R
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WorkoutVideo(var title: String,
                        var id: String,
                        var instructor: String,
                        var description: String,
                        var rating: Int,
                        var numberOfRatings: Int,
                        var difficulty: Float,
                        var level: WorkoutLevel,
                        var workoutGroup: String,
                        var workoutProgram: String,
                        var workoutCollection: String,
                        var workoutEquipment: WorkoutEquipment) : Parcelable

enum class WorkoutLevel(val value: String) {
    @SerializedName("beginner")
    BEGINNER("Beginner"),

    @SerializedName("intermediate")
    INTERMEDIATE("Intermediate"),

    @SerializedName("expert")
    EXPERT("Expert")
}

enum class WorkoutEquipment(val value: String, val icon: Int) {
    @SerializedName("boxing")
    BOXING("Boxing", R.drawable.ic_baseline_sports_mma_24),

    @SerializedName("bike")
    BIKE("Bike", R.drawable.ic_baseline_pedal_bike_24),

    @SerializedName("treadmill")
    TREADMILL("Treadmill", R.drawable.ic_baseline_directions_run_24),

    @SerializedName("pilates")
    PILATES("Pilates", R.drawable.baseline_sports_gymnastics_24),

    @SerializedName("weights")
    WEIGHTS("Weights", R.drawable.ic_baseline_fitness_center_24),

    @SerializedName("yogamat")
    YOGAMAT("Yoga Mat", R.drawable.ic_baseline_self_improvement_24)
}