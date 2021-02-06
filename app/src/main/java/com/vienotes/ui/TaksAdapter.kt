package com.vienotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vienotes.domain.Task

class TaskAdapter(private val list: List<Task>)
    : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task: Task = list[position]
        holder.bind(task)
    }

    override fun getItemCount(): Int = list.size
}