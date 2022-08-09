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
import com.example.workoutapp.*
import com.example.workoutapp.BuildConfig.YOUTUBE_API_KEY
import com.example.workoutapp.databinding.FragmentWorkoutCollectionsBinding
import com.example.workoutapp.ui.videos.VideoListActivity
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class WorkoutCollectionsFragment : Fragment(),
    WorkoutCollectionRecyclerViewAdapter.OnWorkoutGroupItemClickedListener {

    private lateinit var workoutCollectionsViewModel: WorkoutCollectionsViewModel
    private var _binding: FragmentWorkoutCollectionsBinding? = null

    lateinit var workoutProgramsRecyclerView : RecyclerView
    lateinit var  linearLayoutManager : LinearLayoutManager
    lateinit var  workoutCollectionRecyclerViewAdapter : WorkoutCollectionRecyclerViewAdapter

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        workoutCollectionsViewModel = ViewModelProvider(this).get(WorkoutCollectionsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWorkoutCollectionsBinding.inflate(inflater, container, false)
        val root = binding.root

        workoutProgramsRecyclerView = binding.workoutCollectionsRecyclerView
        linearLayoutManager = LinearLayoutManager(activity);
        workoutProgramsRecyclerView.setLayoutManager(linearLayoutManager);
        workoutCollectionRecyclerViewAdapter = WorkoutCollectionRecyclerViewAdapter();
        workoutProgramsRecyclerView.setAdapter(workoutCollectionRecyclerViewAdapter)
        workoutCollectionRecyclerViewAdapter.setOnWorkoutGroupItemClickedListener(this)
        workoutCollectionsViewModel.getWorkoutCollections().observe(viewLifecycleOwner, Observer {
            workoutCollectionRecyclerViewAdapter.setWorkoutGroupsList(it)
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