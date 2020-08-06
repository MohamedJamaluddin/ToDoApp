package com.jamal.todo.datasource.repository.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jamal.todo.core.AppExecutors
import com.jamal.todo.core.Injection
import com.jamal.todo.datasource.database.LocalMediator
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.datasource.repository.TaskRepository
import com.jamal.todo.utility.Resource

class TaskRepositoryImpl
private constructor(
application: Application,
appExecutors: AppExecutors
)
: TaskRepository {

    private val mApplication: Application = application
    private val mAppExecutors: AppExecutors = appExecutors
    private var mTaskDao = Injection.provideDatabase().getTaskDao()

    companion object {
        private var INSTANCE: TaskRepositoryImpl? = null
        fun getInstance(application: Application, appExecutors: AppExecutors): TaskRepositoryImpl {
            if (null == INSTANCE) {
                INSTANCE = TaskRepositoryImpl(application, appExecutors)
            }
            return INSTANCE as TaskRepositoryImpl
        }

    }

    override fun createTask(obj: Task): LiveData<Resource<Task>> {
        val status = MutableLiveData<Resource<Task>>(Resource.loading())
        mAppExecutors.diskIO().execute {
            try {
                val id = mTaskDao.insert(obj)
                status.postValue(Resource.success(obj))
            } catch (ex: Exception) {
                ex.printStackTrace()
                status.postValue(Resource.error(ex.message + "--"))
            }
        }
        return status
    }

    override fun updateTask(obj: Task): LiveData<Resource<Task>> {
        val status = MutableLiveData<Resource<Task>>(Resource.loading())
        mAppExecutors.diskIO().execute {
            mTaskDao.updateDoneStatus(obj.isDone, obj.aliasId!!)
            status.postValue(Resource.success(obj))
        }
        return status
    }

    override fun deleteTask(obj: Task): LiveData<Resource<Boolean>> {
        val status = MutableLiveData<Resource<Boolean>>(Resource.loading())
        mAppExecutors.diskIO().execute {
            mTaskDao.deleteTask(obj.aliasId!!)
            status.postValue(Resource.success(true))
        }
        return status
    }

    override fun getAllTasks(): LiveData<Resource<List<Task>>> {
        return object : LocalMediator<List<Task>>() {
            override fun loadFromDb(): LiveData<List<Task>> {
                return getListData()
            }
        }.asLiveData()
    }

    private fun getListData(): LiveData<List<Task>> {
        val dataListLiveData = MutableLiveData<List<Task>>()
        Injection.provideAppExecutors().diskIO().execute {
            val dataList = mTaskDao.fetchAll()
            dataListLiveData.postValue(dataList)
        }
        return dataListLiveData
    }
}