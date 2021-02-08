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
import com.vienotes.base.Resource
import com.vienotes.manager.CoroutinesManager
import com.vienotes.viewmodel.TasksViewModel
import kotlinx.android.synthetic.main.task_fragment.*
import kotlinx.coroutines.CoroutineExceptionHandler
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
                coroutinesManager.uiScope.launch {
                    loading_layout.visibility = View.VISIBLE
                }
                coroutinesManager.ioScope.launch {
                    val deletedResponse = tasksViewModel.deleteTask(task.id)

                    coroutinesManager.uiScope.launch {
                        loading_layout.visibility = View.GONE
                        if (Resource.Status.SUCCESS == deletedResponse.status) {
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
                coroutinesManager.uiScope.launch {
                    loading_layout.visibility = View.VISIBLE
                }
                val exceptionHandler = CoroutineExceptionHandler { _, error ->
                    coroutinesManager.uiScope.launch {
                        if (error is TasksViewModel.FillAllFieldsException)
                            Snackbar.make(view, R.string.fill_all_fields, Snackbar.LENGTH_SHORT)
                                .show()
                        else {
                            Snackbar.make(view, R.string.error_try_again, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                coroutinesManager.ioScope.launch(exceptionHandler) {
                    val createdResponse = tasksViewModel.createTask(
                        nameInput.text.toString(),
                        noteInput.text.toString()
                    )
                    coroutinesManager.uiScope.launch {
                        loading_layout.visibility = View.GONE
                        if (Resource.Status.SUCCESS == createdResponse.status) {
                            Snackbar.make(view, R.string.task_created_ok, Snackbar.LENGTH_SHORT)
                                .show()
                            findNavController().popBackStack()
                        } else {
                            Snackbar.make(view, R.string.error_try_again, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }
}