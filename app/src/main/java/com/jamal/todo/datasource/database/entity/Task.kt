package com.jamal.todo.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    var uniqueIdentityKey: Long? = null,
    @SerializedName("aliasId")
    var aliasId: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("isDone")
    var isDone: Boolean = false
)  : Serializable