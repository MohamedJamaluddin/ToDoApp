package com.jamal.todo.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


@SuppressLint("StaticFieldLeak")
object AppHolder {

    private val APP: Application

    var context: Context? = null
        get() = if (null != field) field else APP.applicationContext

    init {
        try {
            @SuppressLint("PrivateApi") val c = Class.forName("android.app.ActivityThread")
            APP = c.getDeclaredMethod("currentApplication").invoke(null) as Application
        } catch (e: Throwable) {
            throw AssertionError(e)
        }

    }
}
