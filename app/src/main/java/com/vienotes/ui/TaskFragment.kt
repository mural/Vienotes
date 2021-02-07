package com.vienotes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.smartledge.vienotes.R
import com.vienotes.base.BaseFragment
import com.vienotes.domain.Task
import com.vienotes.manager.CoroutinesManager
import com.vienotes.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.task_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TaskFragment : BaseFragment() {

    private val tasksViewModel by viewModel<TasksViewModel>()

    private val coroutinesManager by inject<CoroutinesManager>()
    val args: TaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.task?.let { task ->
            titleText.text = context?.getText(R.string.title_task_detail)
            nameText.text = task.name
            noteText.text = task.detail
            nameText.visibility = View.VISIBLE
            noteText.visibility = View.VISIBLE
            nameInput.visibility = View.GONE
            noteInput.visibility = View.GONE
            actionButton.text = context?.getText(R.string.delete)
            actionButton.setOnClickListener {
                coroutinesManager.ioScope.launch {
                    val deleted = tasksViewModel.deleteTask(task.id)

                    coroutinesManager.uiScope.launch {
                        if (deleted) {
                            Snackbar.make(view, R.string.task_deleted_ok, Snackbar.LENGTH_SHORT)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Snackbar.make(view, R.string.error_try_again, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }

        } ?: run {
            // create
            titleText.text = context?.getText(R.string.title_create_note)
            actionButton.text = context?.getText(R.string.create)

            actionButton.setOnClickListener {
                coroutinesManager.ioScope.launch {
                    val created = tasksViewModel.createTask(
                        Task(
                            "",
                            nameInput.text.toString(),
                            noteInput.text.toString(),
                            done = false
                        )
                    )
                    coroutinesManager.uiScope.launch {
                        if (created) {
                            Snackbar.make(view, R.string.task_created_ok, Snackbar.LENGTH_SHORT)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Snackbar.make(view, R.string.fill_all_fields, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}