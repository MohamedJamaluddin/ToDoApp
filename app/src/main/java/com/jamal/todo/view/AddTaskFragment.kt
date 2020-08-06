package com.jamal.todo.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jamal.todo.R
import com.jamal.todo.databinding.FragmentAddTaskBinding
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.utility.Status
import java.util.*

class AddTaskFragment : Fragment() {
    companion object {

        fun newInstance(obj: Task?): AddTaskFragment {
            val fragment = AddTaskFragment()
            val bundle = Bundle()
            if (null != obj)
                bundle.putSerializable("key_Task", obj)
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance() = AddTaskFragment()

    }

    private lateinit var viewModel: TaskViewModel
    private lateinit var mBinding: FragmentAddTaskBinding
    private lateinit var mContext: Context
    private var mtask: Task = Task()
    private var isNew = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_task,
            container,
            false
        )
        if (arguments != null && arguments!!.containsKey("key_Task")) {
            mtask = arguments!!.get("key_Task") as Task
            mBinding.imageLayout.visibility = View.VISIBLE
            isNew = false
            initCheckBoxes()
        }

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        initVariables()
        observeViewEvents()
    }

    private fun initVariables() {
        mBinding.obj = mtask
        if (null != activity) {
            (activity as MainActivity).doneFab()?.setOnClickListener {
                validateAndSaveData()
            }
        }
    }

    private fun validateAndSaveData() {
        mtask = mBinding.obj!!
        observeCheckEvents()
        if (TextUtils.isEmpty(mtask.aliasId))
            mtask.aliasId = UUID.randomUUID().toString()
        if (isValidated()) {
            if (isNew) {
                viewModel.createTask(mtask).observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.LOADING) {
                        mBinding.llProgressBar.visibility = View.VISIBLE
                    } else if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                        mBinding.llProgressBar.visibility = View.GONE
                        if (it.status == Status.SUCCESS) {
                            fragmentManager?.popBackStackImmediate()
                            Toast.makeText(
                                context!!,
                                getString(R.string.msg_data_saved_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context!!,
                                getString(R.string.err_msg_data_saved_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            } else {

                viewModel.updateTask(mtask).observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.LOADING) {
                        mBinding.llProgressBar.visibility = View.VISIBLE
                    } else if (it.status == Status.SUCCESS || it.status == Status.ERROR) {
                        mBinding.llProgressBar.visibility = View.GONE
                        if (it.status == Status.SUCCESS) {
                            fragmentManager?.popBackStackImmediate()
                            Toast.makeText(
                                context!!,
                                getString(R.string.msg_data_saved_successfully),
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context!!,
                                getString(R.string.err_msg_data_saved_failed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }

    }

    private fun isValidated(): Boolean {
        if (mBinding.taskTitle.text!!.isEmpty() || mBinding.taskTitle.text == null) {
            Toast.makeText(context!!, getString(R.string.enterTitle), Toast.LENGTH_SHORT).show()
            return false
        }
        if (mBinding.taskDesc.text!!.isEmpty() || mBinding.taskDesc.text == null) {
            Toast.makeText(context!!, getString(R.string.enterDesc), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun initCheckBoxes() {
        if (mtask.isDone) {
            mBinding.done.isChecked = true
            mBinding.done.isEnabled = false
        }

    }

    private fun observeViewEvents() {
        mBinding.btnDelete.setOnClickListener{
            viewModel.deleteTask(mtask)
            fragmentManager?.popBackStackImmediate()
            Toast.makeText(
                context!!,
                getString(R.string.msg_task_deleted_successfully),
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun observeCheckEvents() {
        mtask.isDone = mBinding.done.isChecked
    }
}