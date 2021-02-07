package com.vienotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smartledge.vienotes.R
import com.vienotes.base.BaseFragment
import com.vienotes.domain.Task
import com.vienotes.domain.UserSession
import com.vienotes.manager.CoroutinesManager
import com.vienotes.viewmodel.TasksViewModel
import com.vienotes.viewmodel.TokenViewModel
import kotlinx.android.synthetic.main.taskslist_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskListFragment : BaseFragment(), TaskAdapter.TaskItemClickListener {

    private val tasksViewModel by viewModel<TasksViewModel>()
    private val tokenViewModel by viewModel<TokenViewModel>()

    private val userSession by inject<UserSession>()
    private val coroutinesManager by inject<CoroutinesManager>()

    private var tasksList: List<Task> = listOf()
    private var taskAdapter: TaskAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.taskslist_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        retrieveList()
    }

    override fun onResume() {
        super.onResume()
        fabBehavior.showFAB(true)
    }

    private fun retrieveList() {
        coroutinesManager.ioScope.launch {
            userSession.saveUserToken(tokenViewModel.getAccessToken())

            tasksList = tasksViewModel.getTasksList()
            updateList()
        }
    }

    private fun updateList() {
        coroutinesManager.uiScope.launch {
            tasklistRecycleview?.apply {
                taskAdapter = TaskAdapter(requireContext(), tasksList, this@TaskListFragment)
                adapter = taskAdapter
            }
            loading_text?.apply {
                visibility = if (tasksList.isEmpty()) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onTaskListItemClick(position: Int) {
        taskAdapter?.let {
            findNavController().navigate(
                TaskListFragmentDirections.actionListFragmentToDetailFragment(
                    it.getItem(position)
                )
            )
        }
    }

}