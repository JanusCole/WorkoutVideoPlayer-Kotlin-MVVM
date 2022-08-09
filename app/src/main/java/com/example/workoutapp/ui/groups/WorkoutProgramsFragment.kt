package com.example.workoutapp.ui.groups

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.BuildConfig.YOUTUBE_API_KEY
import com.example.workoutapp.R
import com.example.workoutapp.ui.videos.VideoListActivity
import com.example.workoutapp.databinding.FragmentWorkoutProgramsBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class WorkoutProgramsFragment : Fragment(),
    WorkoutProgramRecyclerViewAdapter.OnWorkoutGroupItemClickedListener {

    private lateinit var workoutProgramsViewModel: WorkoutProgramsViewModel
    private var _binding: FragmentWorkoutProgramsBinding? = null

    lateinit var workoutProgramsRecyclerView : RecyclerView
    lateinit var  linearLayoutManager : LinearLayoutManager
    lateinit var  workoutProgramRecyclerViewAdapter : WorkoutProgramRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutProgramsViewModel = ViewModelProvider(this).get(WorkoutProgramsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkoutProgramsBinding.inflate(inflater, container, false)
        val root = binding.root

        workoutProgramsRecyclerView = binding.workoutProgramsRecyclerView
        linearLayoutManager = LinearLayoutManager(activity);
        workoutProgramsRecyclerView.setLayoutManager(linearLayoutManager);
        workoutProgramRecyclerViewAdapter = WorkoutProgramRecyclerViewAdapter();
        workoutProgramsRecyclerView.setAdapter(workoutProgramRecyclerViewAdapter)
        workoutProgramRecyclerViewAdapter.setOnWorkoutGroupItemClickedListener(this)
        workoutProgramsViewModel.getWorkoutPrograms().observe(viewLifecycleOwner, Observer {
            workoutProgramRecyclerViewAdapter.setWorkoutGroupsList(it)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onWorkoutGroupItemClicked(view: View, groupName: String) {
        if (YOUTUBE_API_KEY != "") {
            val workoutGroupVideosIntent: Intent = Intent(activity, VideoListActivity::class.java)
            workoutGroupVideosIntent.putExtra(VideoListActivity.WORKOUT_GROUP_NAME, groupName)
            startActivity(workoutGroupVideosIntent)
        } else {
            Snackbar.make(workoutProgramsRecyclerView,
                R.string.youtube_api_key_warning,
                BaseTransientBottomBar.LENGTH_LONG).show()
        }
    }
}