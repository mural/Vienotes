package com.vienotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.smartledge.vienotes.R
import com.vienotes.base.BaseFragment
import com.vienotes.base.Resource
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

    private var tasksResponse: Resource<List<Task>> = Resource.default()
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

        swipeRefreshLayout.setOnRefreshListener {
            retrieveList()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivityActions.showFAB(true)
    }

    private fun retrieveList() {
        coroutinesManager.uiScope.launch {
            info_text.text = context?.getString(R.string.loading)
            info_text.visibility = View.VISIBLE
            tasklistRecycleview.visibility = View.GONE
        }
        coroutinesManager.ioScope.launch {
            userSession.saveUserToken(tokenViewModel.getAccessToken())

            tasksResponse = tasksViewModel.getTasksList(pendingOnly = false)
            if (Resource.Status.SUCCESS == tasksResponse.status) {
                tasksResponse.data?.let {
                    tasksList = it
                }
                updateList()
            } else {
                coroutinesManager.uiScope.launch {
                    tasklistRecycleview?.apply {
                        visibility = View.GONE
                        info_text.visibility = View.VISIBLE
                        info_text.text = context?.getString(R.string.error_try_again)
                    }
                }
            }
        }
    }

    private fun updateList() {
        coroutinesManager.uiScope.launch {
            tasklistRecycleview?.apply {
                taskAdapter = TaskAdapter(requireContext(), tasksList, this@TaskListFragment)
                adapter = taskAdapter
            }
            tasklistRecycleview?.apply {
                visibility = if (tasksList.isEmpty()) View.GONE else View.VISIBLE
                info_text.visibility = if (tasksList.isEmpty()) View.VISIBLE else View.GONE
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