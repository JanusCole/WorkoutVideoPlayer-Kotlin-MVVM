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
import com.example.workoutapp.databinding.FragmentWorkoutGroupsBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class WorkoutGroupsFragment : Fragment(),
    WorkoutGroupRecyclerViewAdapter.OnWorkoutGroupItemClickedListener {

    private lateinit var workoutGroupsViewModel: WorkoutGroupsViewModel
    private var _binding: FragmentWorkoutGroupsBinding? = null

    lateinit var workoutGroupsRecyclerView : RecyclerView
    lateinit var  linearLayoutManager : LinearLayoutManager
    lateinit var  workoutGroupRecyclerViewAdapter : WorkoutGroupRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutGroupsViewModel = ViewModelProvider(this).get(WorkoutGroupsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkoutGroupsBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModelSetup()

        return root
    }

    private fun viewModelSetup() {
        workoutGroupsRecyclerView = binding.workoutGroupsRecyclerView
        linearLayoutManager = LinearLayoutManager(activity);
        workoutGroupsRecyclerView.setLayoutManager(linearLayoutManager);
        workoutGroupRecyclerViewAdapter = WorkoutGroupRecyclerViewAdapter();
        workoutGroupsRecyclerView.setAdapter(workoutGroupRecyclerViewAdapter)
        workoutGroupRecyclerViewAdapter.setOnWorkoutGroupItemClickedListener(this)
        workoutGroupsViewModel.getWorkoutGroups().observe(viewLifecycleOwner, Observer {
            if (it.size > 0) {
                workoutGroupRecyclerViewAdapter.setWorkoutGroupsList(it)
            }
        })
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
            Snackbar.make(workoutGroupsRecyclerView,
                R.string.youtube_api_key_warning,
                BaseTransientBottomBar.LENGTH_LONG).show()
        }
    }
}