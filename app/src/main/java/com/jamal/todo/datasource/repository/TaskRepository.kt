package com.jamal.todo.datasource.repository

import androidx.lifecycle.LiveData
import com.jamal.todo.datasource.database.entity.Task
import com.jamal.todo.utility.Resource

interface TaskRepository {

    fun createTask(obj: Task): LiveData<Resource<Task>>
    fun updateTask(obj: Task): LiveData<Resource<Task>>
    fun deleteTask(obj: Task): LiveData<Resource<Boolean>>
    fun getAllTasks(): LiveData<Resource<List<Task>>>
}