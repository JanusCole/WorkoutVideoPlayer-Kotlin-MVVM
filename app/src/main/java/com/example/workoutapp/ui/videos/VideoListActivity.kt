package com.example.workoutapp.ui.videos

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.databinding.ActivityVideoListBinding
import com.example.workoutapp.models.WorkoutVideo

class VideoListActivity : AppCompatActivity(),
    WorkoutVideoRecyclerViewAdapter.OnWorkoutVideoItemClickedListener {

    companion object{
        const val WORKOUT_GROUP_NAME = "Workout Group Name"
    }

    private lateinit var binding: ActivityVideoListBinding
    private lateinit var workoutVideosViewModel: WorkoutVideosViewModel

    lateinit var workoutGroupsRecyclerView : RecyclerView
    lateinit var  linearLayoutManager : LinearLayoutManager
    lateinit var  workoutGroupRecyclerViewAdapter : WorkoutVideoRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutVideosViewModel = ViewModelProvider(this).get(WorkoutVideosViewModel::class.java)

        binding = ActivityVideoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val workoutGroupName : String = getIntent().getStringExtra(WORKOUT_GROUP_NAME)!!;

        workoutGroupsRecyclerView = binding.videoListRecyclerView
        linearLayoutManager = LinearLayoutManager(this)
        workoutGroupsRecyclerView.setLayoutManager(linearLayoutManager)
        workoutGroupRecyclerViewAdapter = WorkoutVideoRecyclerViewAdapter()
        workoutGroupsRecyclerView.setAdapter(workoutGroupRecyclerViewAdapter)
        workoutGroupRecyclerViewAdapter.setOnWorkoutVideoItemClickedListener(this)
        val heading = binding.videoListHeading
        heading.text = workoutGroupName
        workoutVideosViewModel.getWorkoutVideos().observe(this, Observer {
            workoutGroupRecyclerViewAdapter.setWorkoutVideosList(it.filter {
                it.workoutGroup.equals(workoutGroupName) ||
                it.workoutCollection.equals(workoutGroupName) ||
                it.workoutProgram.equals(workoutGroupName)
            })
        })

        binding.videoListBackButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onWorkoutVideoItemClicked(workoutVideo: WorkoutVideo) {
        val workoutVideoDetailsIntent : Intent = Intent(this, VideoDetailsActivity::class.java)
        workoutVideoDetailsIntent.putExtra(VideoPlayerActivity.WORKOUT_VIDEO_ID, workoutVideo.id)
        startActivity(workoutVideoDetailsIntent)
    }
}