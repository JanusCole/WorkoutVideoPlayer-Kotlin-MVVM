package com.example.workoutapp.ui.videos

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.workoutapp.BuildConfig
import com.example.workoutapp.databinding.ActivityVideoDetailsBinding
import com.google.android.youtube.player.*

class VideoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailsBinding
    private lateinit var workoutVideoViewModel: WorkoutVideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        workoutVideoViewModel = ViewModelProvider(this).get(WorkoutVideoViewModel::class.java)

        binding.workoutVideoViewModel = workoutVideoViewModel
        binding.lifecycleOwner = this

        val workoutVideoId : String = getIntent().getStringExtra(VideoPlayerActivity.WORKOUT_VIDEO_ID)!!

        binding.videoDetailsBackButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

        binding.videoDetailsPlayButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                startVideoPlayerActivity(workoutVideoId)
            }
        })
        workoutVideoViewModel.getVideo(workoutVideoId)

        val workoutVideoImage = binding.workoutVideoDetailImage

        val onInitializedListener = object : YouTubeThumbnailView.OnInitializedListener{
            override fun onInitializationSuccess(
                youTubeThumbnailView: YouTubeThumbnailView?,
                youTubeThumbnailLoader: YouTubeThumbnailLoader?
            ) {
                youTubeThumbnailLoader?.setVideo(workoutVideoId)
            }

            override fun onInitializationFailure(
                youTubeThumbnailView: YouTubeThumbnailView?,
                youTubeInitializationResult: YouTubeInitializationResult?
            ) {
                if (youTubeInitializationResult.toString().equals("SERVICE_MISSING"))
                    Log.d("YOUTUBE ERROR", "Look here: https://stackoverflow.com/questions/18088082/youtube-api-for-android-exception-service-missing")
            }
        }
        workoutVideoImage.initialize(BuildConfig.YOUTUBE_API_KEY, onInitializedListener)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun startVideoPlayerActivity(workoutVideoId : String) {
        val workoutVideoPlayerIntent : Intent = Intent(this, VideoPlayerActivity::class.java)
        workoutVideoPlayerIntent.putExtra(VideoPlayerActivity.WORKOUT_VIDEO_ID, workoutVideoId)
        startActivity(workoutVideoPlayerIntent)
    }
}