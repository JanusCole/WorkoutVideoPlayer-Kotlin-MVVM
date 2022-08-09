package com.example.workoutapp.ui.videos

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.workoutapp.BuildConfig
import com.example.workoutapp.databinding.ActivityVideoPlayerBinding
import com.google.android.youtube.player.*

class VideoPlayerActivity : YouTubeBaseActivity() {

    companion object{
        const val WORKOUT_VIDEO_ID = "Workout Video Id"
    }

    private lateinit var binding: ActivityVideoPlayerBinding
    private var youTubePlayer: YouTubePlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutVideoId : String = getIntent().getStringExtra(WORKOUT_VIDEO_ID)!!

        val youTubePlayerView = binding.workoutVideoPlayer
        binding.videoPlayerConstraintLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        val onTouchListener = object : YouTubePlayer.PlaybackEventListener{
            override fun onPlaying() {
            }

            override fun onPaused() {
                finish()
            }

            override fun onStopped() {
                finish()
            }

            override fun onBuffering(p0: Boolean) {
            }

            override fun onSeekTo(p0: Int) {
            }

        }
        val onInitializedListener = object : YouTubePlayer.OnInitializedListener{

            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                youTubePlayer = p1
                p1?.loadVideo(workoutVideoId)
                p1?.setPlaybackEventListener(onTouchListener)
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Log.d("ERROR", p1.toString())
                if (p1.toString().equals("SERVICE_MISSING"))
                    Log.d("YOUTUBE ERROR", "Look here: https://stackoverflow.com/questions/18088082/youtube-api-for-android-exception-service-missing")
            }
        }

        youTubePlayerView.initialize(BuildConfig.YOUTUBE_API_KEY, onInitializedListener)
        youTubePlayerView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}