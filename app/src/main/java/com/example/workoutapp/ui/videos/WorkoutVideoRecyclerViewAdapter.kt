package com.example.workoutapp.ui.videos

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.BuildConfig
import com.example.workoutapp.R
import com.example.workoutapp.models.WorkoutVideo
import com.google.android.youtube.player.*

class WorkoutVideoRecyclerViewAdapter : RecyclerView.Adapter<WorkoutVideoRecyclerViewAdapter.WorkoutVideoViewHolder> () {

    private var items : List<WorkoutVideo> = ArrayList()
    private var onWorkoutVideoItemClickedListener: OnWorkoutVideoItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutVideoViewHolder {
        return WorkoutVideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.workout_video_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorkoutVideoViewHolder, position: Int) {
        val integer = position
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                onWorkoutVideoItemClickedListener!!.onWorkoutVideoItemClicked(items.get(integer))
            }

        })
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setWorkoutVideosList (workoutVideosList : List<WorkoutVideo>) {
        items = workoutVideosList
        notifyDataSetChanged();
    }

    fun setOnWorkoutVideoItemClickedListener (onWorkoutVideoItemClickedListener : OnWorkoutVideoItemClickedListener) {
        this.onWorkoutVideoItemClickedListener = onWorkoutVideoItemClickedListener
    }

    class WorkoutVideoViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val workoutVideoImage = itemView.findViewById<YouTubeThumbnailView>(R.id.workout_video_image)

        fun bind(workoutVideo: WorkoutVideo){

            val onInitializedListener = object : YouTubeThumbnailView.OnInitializedListener{
                override fun onInitializationSuccess(
                    p0: YouTubeThumbnailView?,
                    p1: YouTubeThumbnailLoader?
                ) {
                    p1?.setVideo(workoutVideo.id)
                }

                override fun onInitializationFailure(
                    p0: YouTubeThumbnailView?,
                    p1: YouTubeInitializationResult?
                ) {
                    if (p1.toString().equals("SERVICE_MISSING"))
                        Log.d("YOUTUBE ERROR", "Look here: https://stackoverflow.com/questions/18088082/youtube-api-for-android-exception-service-missing")
                }
            }
            workoutVideoImage.initialize(BuildConfig.YOUTUBE_API_KEY, onInitializedListener)

        }
    }

    interface OnWorkoutVideoItemClickedListener {
        fun onWorkoutVideoItemClicked(workoutVideo : WorkoutVideo) : Unit
    }
}