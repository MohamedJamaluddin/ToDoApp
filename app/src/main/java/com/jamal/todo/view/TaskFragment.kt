package com.jamal.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jamal.todo.R
import com.jamal.todo.core.Injection
import com.jamal.todo.databinding.FragmentTaskBinding
import com.jamal.todo.databinding.ItemTaskBinding
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.utility.ActivityUtils
import com.jamal.todo.utility.CallBackCustom
import com.jamal.todo.utility.Status

class TaskFragment : Fragment(), CallBackCustom<Task, ItemTaskBinding> {

    companion object {
        fun newInstance() = TaskFragment()
    }

    private lateinit var viewModel: TaskViewModel
    private lateinit var mBinding: FragmentTaskBinding
    private var mAdapter = TaskAdapter(Injection.provideAppExecutors(), this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_task,
            container,
            false
        )

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!).get(TaskViewModel::class.java)
        initRecyclerView()
        getData()
        if (null != activity) {
            (activity as MainActivity).addFab()?.setOnClickListener {
                ActivityUtils.addFragmentToActivity(activity?.supportFragmentManager!!,
                    AddTaskFragment.newInstance(null),
                    R.id.fragment_container, true
                    , AddTaskFragment::class.java.name)
            }
            //initSearchMenu()
        }
    }

    private fun initRecyclerView() {
        mBinding.rv.addItemDecoration(
            DividerItemDecoration(context,
            DividerItemDecoration.VERTICAL)
        )
        mBinding.rv.adapter = mAdapter
        val layoutManager = LinearLayoutManager(context)
        mBinding.rv.layoutManager = layoutManager
    }

    private fun getData() {
        viewModel.getAllTasks().observe(viewLifecycleOwner, Observer { value ->
            if (value.status == Status.LOADING) {
                showLoader()
            } else if (value.status == Status.SUCCESS || value.status == Status.ERROR) {
                hide()
                if (value.status == Status.SUCCESS) {
                    showData(value.data)
                } else if (value.status == Status.ERROR) {
                    showErrorDialog()
                }
            }
        })
    }


    private fun showData(data: List<Task>?) {
        if (data != null && data.isNotEmpty()) {
            mBinding.includeEmpty.root.visibility = View.GONE
            mAdapter.submitList(data)
            mAdapter.setData(data)
        } else {
            showNoData()
            mAdapter.submitList(null)
        }
    }

    private fun showNoData() {
        mBinding.includeEmpty.root.visibility = View.VISIBLE
        hide()
    }

    private fun showLoader() {
        mBinding.progress.visibility = View.VISIBLE
        mBinding.error.visibility = View.GONE
    }

    private fun showErrorDialog() {
        mBinding.error.visibility = View.VISIBLE
        mBinding.progress.visibility = View.GONE
    }

    private fun hide() {
        mBinding.progress.visibility = View.GONE
        mBinding.error.visibility = View.GONE
    }

    override fun handleCallback(t: Task, s: ItemTaskBinding) {
        ActivityUtils.addFragmentToActivity(activity?.supportFragmentManager!!,
            AddTaskFragment.newInstance(t),
            R.id.fragment_container, true
            , AddTaskFragment::class.java.name)
    }

    override fun handleCallbackWithType(t: Task, s: ItemTaskBinding, type: String) {
    }
}