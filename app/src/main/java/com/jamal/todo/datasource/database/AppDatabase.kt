package com.jamal.todo.datasource.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jamal.todo.core.ApplicationClass
import com.jamal.todo.datasource.database.dao.TaskDao
import com.jamal.todo.datasource.database.entity.Task

@Database(
    entities = [
        Task::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "todo.db"
        private var mAppDatabase: AppDatabase? = null

        private fun provideDatabase(): AppDatabase {
            if (mAppDatabase == null) {
                mAppDatabase = Room.databaseBuilder(
                    ApplicationClass.appContext,
                    AppDatabase::class.java, DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return mAppDatabase as AppDatabase
        }

        fun getInstance(): AppDatabase {
            return mAppDatabase ?: synchronized(this) {
                mAppDatabase ?: provideDatabase().also { mAppDatabase = it }
            }
        }

    }

    abstract fun getTaskDao(): TaskDao
}