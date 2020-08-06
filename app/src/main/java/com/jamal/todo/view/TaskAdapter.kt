package com.jamal.todo.view

import android.graphics.Bitmap
import android.opengl.Visibility
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.jamal.todo.R
import com.jamal.todo.core.AppExecutors
import com.jamal.todo.databinding.ItemTaskBinding
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.utility.CallBackCustom
import com.jamal.todo.view.common.DataBoundListAdapter
import kotlinx.android.synthetic.main.item_task.view.*
import java.util.*
import kotlin.collections.ArrayList

@Suppress("UNCHECKED_CAST")
class TaskAdapter(
    mAppExecutors: AppExecutors,
    callback: CallBackCustom<Task, ItemTaskBinding>
) : DataBoundListAdapter<Task, ItemTaskBinding>(
        mAppExecutors = mAppExecutors,
        mDiffCallback = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return false
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return false
            }
        }
) {
    private val mCallback: CallBackCustom<Task, ItemTaskBinding> = callback
    private var mTaskList: List<Task> = mutableListOf()
    override fun createBinding(parent: ViewGroup, viewType: Int): ItemTaskBinding {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_task,
                parent,
                false)
    }

    override fun bind(binding: ItemTaskBinding, item: Task) {
        binding.item = item
        binding.root.setOnClickListener {
            mCallback.handleCallback(item, binding)
        }
        if(item.isDone){
            binding.root.doneStatus.visibility = View.VISIBLE
        }
    }

    fun setData(data: List<Task>) {
        mTaskList = data
    }
}