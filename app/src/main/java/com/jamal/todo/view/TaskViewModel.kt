package com.jamal.todo.view

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jamal.todo.R
import com.jamal.todo.core.AppExecutors
import com.jamal.todo.core.Injection
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.datasource.repository.TaskRepository
import com.jamal.todo.datasource.repository.impl.TaskRepositoryImpl
import com.jamal.todo.utility.ActivityUtils
import com.jamal.todo.utility.Resource

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private var mFragment: String? = null
    private var mFragmentManager: FragmentManager? = null
    private val mAppExecutors: AppExecutors = Injection.provideAppExecutors()
    private val mRepository: TaskRepository

    init {
        mRepository = TaskRepositoryImpl.getInstance(application, mAppExecutors)
    }

    fun initFragmentManager(fragmentManager: FragmentManager) {
        mFragmentManager = fragmentManager
    }

    fun navigateToChildFragment(fragment: Fragment) {
        if (mFragment != null &&
            mFragment.equals(fragment::class.simpleName)) {
            return
        }
        ActivityUtils.addFragmentToContentContainer(mFragmentManager!!, fragment
            , R.id.fragment_container, fragment::class.simpleName!!)
        mFragment = fragment::class.simpleName
    }

    fun getAllTasks(): LiveData<Resource<List<Task>>> {
        return mRepository.getAllTasks()
    }

    fun createTask(obj: Task): LiveData<Resource<Task>> {
        return mRepository.createTask(obj)
    }

    fun updateTask(obj: Task): LiveData<Resource<Task>> {
        return mRepository.updateTask(obj)
    }

    fun deleteTask(obj: Task): LiveData<Resource<Boolean>> {
        return mRepository.deleteTask(obj)
    }

}