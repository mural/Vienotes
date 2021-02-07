package com.vienotes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vienotes.domain.Task

class TaskAdapter(private val context: Context, private val list: List<Task>, private val onClickListener: TaskItemClickListener)
    : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(context, inflater, parent, onClickListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = list[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = list.size

    fun getItem(position: Int): Task {
        return list[position]
    }

    interface TaskItemClickListener {
        fun onTaskListItemClick(position: Int)
    }
}