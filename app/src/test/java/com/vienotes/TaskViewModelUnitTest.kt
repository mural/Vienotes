package com.vienotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.vienotes.base.Resource
import com.vienotes.domain.Task
import com.vienotes.manager.CoroutinesManager
import com.vienotes.repository.TasksRepository
import com.vienotes.viewmodel.TasksViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class TaskViewModelUnitTest {
    private lateinit var viewModel: TasksViewModel
    private lateinit var tasksRepository: TasksRepository

    private val tasksList = listOf(
        Task("1", "Task 1", "pending task", false),
        Task("2", "Task 2", "done task", true)
    )

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        tasksRepository = mock()
        viewModel = TasksViewModel(CoroutinesManager(), tasksRepository)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testGetAllStatusTaskCallOK() =
        runBlocking {
            `when`(tasksRepository.listAllTasks()).thenReturn(Resource.success(tasksList))

            val response = viewModel.getTasksList(pendingOnly = false)

            verify(tasksRepository).listAllTasks()
            assert(Resource.Status.SUCCESS == response.status)
            assert(response.data?.size == 2)
        }

    @Test
    fun testGetPendingStatusTaskCallOK() =
        runBlocking {
            `when`(tasksRepository.listAllTasks()).thenReturn(Resource.success(tasksList))

            val response = viewModel.getTasksList(pendingOnly = true)

            verify(tasksRepository).listAllTasks()
            assert(Resource.Status.SUCCESS == response.status)
            assert(response.data?.size == 1)
        }

    @Test
    fun testGetTaskCallError() =
        runBlocking {
            `when`(tasksRepository.listAllTasks()).thenReturn(Resource.error())

            val response = viewModel.getTasksList(pendingOnly = false)

            verify(tasksRepository).listAllTasks()
            assert(Resource.Status.ERROR == response.status)
            assert(response.data == null)
        }

    @Test
    fun testCreateTaskCallOK() =
        runBlocking {
            val taskName = "Task 1000"
            val taskNote = "pending task"
            val task = Task("", taskName, taskNote, false)
            `when`(tasksRepository.createTask(task)).thenReturn(Resource.success(data = true))

            val response = viewModel.createTask(name = taskName, note = taskNote)

            verify(tasksRepository).createTask(task)
            assert(Resource.Status.SUCCESS == response.status)
            assert(response.data == true)
        }

    @Test(expected = TasksViewModel.FillAllFieldsException::class)
    fun testCreateTaskCallException() {
        runBlocking {
            viewModel.createTask(name = "", note = "")
        }
    }

    @Test
    fun testDeleteTaskOK() =
        runBlocking {
            val taskId = "1001"
            `when`(tasksRepository.deleteTask(taskId)).thenReturn(Resource.success(data = true))

            val response = viewModel.deleteTask(taskId)

            verify(tasksRepository).deleteTask(taskId)
            assert(Resource.Status.SUCCESS == response.status)
            assert(response.data == true)
        }

    @Test
    fun testDeleteTaskError() =
        runBlocking {
            val taskId = ""
            `when`(tasksRepository.deleteTask(taskId)).thenReturn(Resource.error())

            val response = viewModel.deleteTask(taskId)

            verify(tasksRepository).deleteTask(taskId)
            assert(Resource.Status.ERROR == response.status)
            assert(response.data == null)
        }

}