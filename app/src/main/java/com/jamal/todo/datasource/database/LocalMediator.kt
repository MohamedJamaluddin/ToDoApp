package com.jamal.todo.datasource.database

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jamal.todo.utility.Resource


@Suppress("UNCHECKED_CAST")
abstract class LocalMediator<ResultType> @MainThread
protected constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading()
        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) {
            result.removeSource(dbSource)
            result.addSource(dbSource) { newData -> result.setValue(Resource.success(newData)) }
        }
    }

    fun asLiveData(): MediatorLiveData<Resource<ResultType>> {
        return result
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

}
