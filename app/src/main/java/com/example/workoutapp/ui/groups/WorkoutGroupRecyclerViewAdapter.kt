package com.example.workoutapp.ui.groups

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.workoutapp.R
import com.example.workoutapp.models.WorkoutGroup

class WorkoutGroupRecyclerViewAdapter : RecyclerView.Adapter<WorkoutGroupRecyclerViewAdapter.WorkoutGroupViewHolder> () {

    private var items : MutableList<WorkoutGroup> = ArrayList()
    private var onWorkoutGroupItemClickedListener: OnWorkoutGroupItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutGroupViewHolder {
        return WorkoutGroupViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.workout_group_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: WorkoutGroupViewHolder, position: Int) {
        holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onWorkoutGroupItemClickedListener!!.onWorkoutGroupItemClicked(v!!, items.get(holder.layoutPosition).name)
                }

            })
        holder.bind(items.get(position))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setWorkoutGroupsList (workoutGroupsListItem : List<WorkoutGroup>) {
        items = workoutGroupsListItem as MutableList<WorkoutGroup>
        notifyDataSetChanged();
    }

    fun setOnWorkoutGroupItemClickedListener (onWorkoutGroupItemClickedListener : OnWorkoutGroupItemClickedListener) {
        this.onWorkoutGroupItemClickedListener = onWorkoutGroupItemClickedListener
    }

    class WorkoutGroupViewHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {
        val workoutGroupImage = itemView.findViewById<ImageView>(R.id.workout_group_image)
        val workoutGroupName = itemView.findViewById<TextView>(R.id.workout_group_name)

        fun bind(workoutGroupItem: WorkoutGroup){

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.fitness_class_example)
                .error(R.drawable.fitness_class_example)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(workoutGroupItem.imageUrl)
                .into(workoutGroupImage)
            workoutGroupName.setText(workoutGroupItem.name)

        }
    }

    interface OnWorkoutGroupItemClickedListener {
        fun onWorkoutGroupItemClicked(view : View, groupName : String) : Unit
    }
}