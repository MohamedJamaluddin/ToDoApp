package com.jamal.todo.core

import android.content.Context
import com.jamal.todo.datasource.database.AppDatabase
import java.util.concurrent.TimeUnit


object Injection {

    fun provideAppExecutors(): AppExecutors {
        return AppExecutors.instance
    }

    fun provideDatabase(): AppDatabase {
        return AppDatabase.getInstance()
    }

    fun provideContext(): Context? {
        return AppHolder.context
    }
}
