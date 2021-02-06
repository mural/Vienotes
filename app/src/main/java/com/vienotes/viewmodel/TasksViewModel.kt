package com.vienotes.viewmodel

import androidx.lifecycle.ViewModel
import com.vienotes.domain.Task
import com.vienotes.manager.CoroutinesManager
import com.vienotes.repository.TasksRepository

class TasksViewModel(private val coroutinesManager: CoroutinesManager, private val tasksRepository: TasksRepository) : ViewModel() {

    suspend fun getTasksList(): List<Task> {

        return tasksRepository.listAllTasks()
    }
}