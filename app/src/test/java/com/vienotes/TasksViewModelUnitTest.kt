package com.vienotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vienotes.di.ManagedCoroutineScope
import com.vienotes.manager.CoroutinesManager
import com.vienotes.viewmodel.TasksViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

@RunWith(JUnit4::class)
class TasksViewModelUnitTest : KoinTest {

    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)

    val tasksViewModel by inject<TasksViewModel>()
    val coroutinesManager by inject<CoroutinesManager>()

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin {
            modules(module {
                single { TasksViewModel(get(), get()) }

                single { CoroutinesManager() }
            })
        }

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        stopKoin()

        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun testToggleTodo() = runBlockingTest {
//        coroutinesManager.ioScope.launch {
        val taks = tasksViewModel.getTasksList()

        assert(taks.isNotEmpty())
//        }

    }

}