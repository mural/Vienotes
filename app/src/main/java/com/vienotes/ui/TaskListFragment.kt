package com.vienotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.smartledge.vienotes.R
import com.vienotes.domain.Task
import com.vienotes.domain.UserSession
import com.vienotes.manager.CoroutinesManager
import com.vienotes.viewmodel.TasksViewModel
import com.vienotes.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TaskListFragment : Fragment() {

    private val tasksViewModel by viewModel<TasksViewModel>()
    private val tokenViewModel by viewModel<TokenViewModel>()

    private val userSession by inject<UserSession>()
    private val coroutinesManager by inject<CoroutinesManager>()

    private var tasksList: List<Task> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.taskslist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }

        coroutinesManager.ioScope.launch {
            userSession.saveUserToken(tokenViewModel.getAccessToken())

            tasksList = tasksViewModel.getTasksList()
            updateList()
        }

    }

    private fun updateList() {
        coroutinesManager.uiScope.launch {
            view?.findViewById<RecyclerView>(R.id.tasklistRecycleview)?.apply {
                adapter = TaskAdapter(tasksList)
            }
            view?.findViewById<TextView>(R.id.loading_text)?.apply {
                visibility = if (tasksList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

}