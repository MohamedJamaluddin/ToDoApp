package com.jamal.todo.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jamal.todo.datasource.database.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    fun fetchAll(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: Task):Long

    @Query("UPDATE task SET isDone=:isDone WHERE aliasId=:aliasId")
    fun updateDoneStatus(isDone: Boolean,aliasId: String)

    @Query("DELETE FROM task WHERE aliasId=:aliasId")
    fun deleteTask(aliasId: String)
}