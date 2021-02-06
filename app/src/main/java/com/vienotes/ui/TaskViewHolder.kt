package com.vienotes.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartledge.vienotes.R
import com.vienotes.domain.Task

class TaskViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.task_item, parent, false)) {
    private var id: TextView? = null
    private var name: TextView? = null
    private var note: TextView? = null
    private var done: TextView? = null

    init {
        id = itemView.findViewById(R.id.id)
        name = itemView.findViewById(R.id.name)
        note = itemView.findViewById(R.id.note)
        done = itemView.findViewById(R.id.done)
    }

    fun bind(task: Task) {
        id?.text = task.id
        name?.text = task.name
        note?.text = task.detail
        done?.text = task.done.toString()
    }

}