package com.vienotes.repository

import CreateTaskMutation
import DeleteTaskMutation
import TasksQuery
import android.content.Context
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.vienotes.domain.Task
import com.vienotes.manager.CoroutinesManager

open class TasksRepository(
    val context: Context,
    private val coroutinesManager: CoroutinesManager,
    private val apolloClient: ApolloClient
) {

    open suspend fun listAllTasks(): List<Task> {
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
            // handle protocol errors
            e.printStackTrace()
        }
        return tasks
    }

    open suspend fun createTask(task: Task): Boolean {
        try {
            apolloClient.mutate(CreateTaskMutation(task.name, Input.fromNullable(task.detail)))
                .await()

        } catch (e: ApolloException) {
            // handle protocol errors
            e.printStackTrace()
            return false
        }
        return true
    }

    open suspend fun deleteTask(taskId: String): Boolean {
        try {
            apolloClient.mutate(DeleteTaskMutation(taskId)).await()

        } catch (e: ApolloException) {
            // handle protocol errors
            e.printStackTrace()
            return false
        }
        return true
    }

}

////            val launch = response.data?.allTasks
////            if (launch == null || response.hasErrors()) {
////                // handle application errors
////                Log.d(this.toString(), "response error")
////                return@launch
////            }
////
////            // launch now contains a typesafe model of your data
////            Log.d(this.toString(), "Tasks qty: ${launch.size}")