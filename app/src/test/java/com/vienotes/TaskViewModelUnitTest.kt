package com.vienotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
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
        runBlocking {
            `when`(tasksRepository.listAllTasks()).thenReturn(listOf(Task("", "", "", false)))
        }
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
    fun testGetTaskCall() =
        runBlocking {
//        viewModel.weather.observeForever(weatherObserver) //for live data
            val tasks = viewModel.getTasksList()
//            verify(weatherRepository).getWeather(validLocation)
//            verify(weatherObserver, timeout(50)).onChanged(successResource)
            verify(tasksRepository).listAllTasks()
            assert(tasks.isNotEmpty())
        }

}