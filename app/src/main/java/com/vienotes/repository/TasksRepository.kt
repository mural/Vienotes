package com.vienotes.repository

import CreateTaskMutation
import DeleteTaskMutation
import TasksQuery
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.vienotes.base.Resource
import com.vienotes.domain.Task

open class TasksRepository(
    private val apolloClient: ApolloClient
) {

    open suspend fun listAllTasks(): Resource<List<Task>> {
        val tasks: MutableList<Task> = mutableListOf()
        try {
            val response = apolloClient.query(TasksQuery()).await()
            response.data?.allTasks?.let {
                for (taskData in it) {
                    if (taskData != null) {
                        tasks.add(
                            Task(
                                id = taskData.id,
                                name = taskData.name,
                                detail = taskData.note ?: "",
                                done = taskData.isDone
                            )
                        )
                    }
                }
            }


        } catch (e: ApolloException) {
            return Resource.error(e)
        }
        return Resource.success(tasks)
    }

    open suspend fun createTask(task: Task): Resource<Boolean> {
        try {
            val response =
                apolloClient.mutate(CreateTaskMutation(task.name, Input.fromNullable(task.detail)))
                    .await()
            response.data?.let { data ->
                if (data.createTask == null) return Resource.error()
            }

        } catch (e: ApolloException) {
            return Resource.error(e)
        }
        return Resource.success(true)
    }

    open suspend fun deleteTask(taskId: String): Resource<Boolean> {
        try {
            val response = apolloClient.mutate(DeleteTaskMutation(taskId)).await()
            response.data?.let { data ->
                data.deleteTask?.let {
                    if (!it) return Resource.error()
                }
            }
        } catch (e: ApolloException) {
            return Resource.error(e)
        }
        return Resource.success(true)
    }

}