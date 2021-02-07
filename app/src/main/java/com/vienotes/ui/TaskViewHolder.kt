package com.vienotes.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartledge.vienotes.R
import com.vienotes.domain.Task

class TaskViewHolder(val context: Context, inflater: LayoutInflater, parent: ViewGroup, private val onClickListener: TaskAdapter.TaskItemClickListener) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.task_item, parent, false)),
    View.OnClickListener {
    private var id: TextView? = null
    private var name: TextView? = null
    private var note: TextView? = null
    private var done: TextView? = null

    init {
        name = itemView.findViewById(R.id.name)
        note = itemView.findViewById(R.id.note)
        done = itemView.findViewById(R.id.doneStatus)
        itemView.setOnClickListener(this)
    }

    fun bind(task: Task) {
        id?.text = task.id
        name?.text = task.name
        note?.text = task.detail
        done?.text = context.getText(if (task.done) R.string.status_done else R.string.status_pending)
    }

    override fun onClick(v: View?) {
        val position = adapterPosition
        onClickListener.onTaskListItemClick(position)
    }

}