package com.vienotes.viewmodel

import androidx.lifecycle.ViewModel
import com.vienotes.base.Resource
import com.vienotes.domain.Task
import com.vienotes.manager.CoroutinesManager
import com.vienotes.repository.TasksRepository

class TasksViewModel(
    private val coroutinesManager: CoroutinesManager,
    private val tasksRepository: TasksRepository
) : ViewModel() {

    suspend fun getTasksList(pendingOnly: Boolean): Resource<List<Task>> {

        val resourceResponse = tasksRepository.listAllTasks()
        if (Resource.Status.SUCCESS == resourceResponse.status && pendingOnly) {
            return Resource.success(resourceResponse.data?.filter {
                !it.done
            })
        }
        return resourceResponse
    }

    suspend fun createTask(name: String, note: String): Resource<Boolean> {
        if (name.isBlank() || note.isBlank()) throw FillAllFieldsException()

        return tasksRepository.createTask(Task(id = "", name = name, detail = note, done = false))
    }

    suspend fun deleteTask(taskId: String): Resource<Boolean> {
        return tasksRepository.deleteTask(taskId)
    }

    open class FillAllFieldsException : Exception()
}