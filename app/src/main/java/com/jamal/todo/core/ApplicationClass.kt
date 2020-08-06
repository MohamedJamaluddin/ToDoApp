package com.jamal.todo.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import java.io.PrintWriter
import java.io.StringWriter


class ApplicationClass : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext

        Stetho.initializeWithDefaults(this)

        try {
            @SuppressLint("PrivateApi")
            val sqliteDebugClass = Class.forName("android.database.sqlite.SQLiteDebug")
            val field = sqliteDebugClass.getDeclaredField("DEBUG_SQL_STATEMENTS")
            field.isAccessible = true
            field.set(null, true)

        } catch (t: Throwable) {
            Log.e(TAG, "Could not enable SQLiteDebug logging")
        }

        Thread.setDefaultUncaughtExceptionHandler { thread, exception ->

            val sw = StringWriter()
            exception.printStackTrace(PrintWriter(sw))
            val exceptionAsString = sw.toString()
            Log.e(TAG, "======== Exception Start ===========\n\n")
            Log.e("  ---->  %s", exceptionAsString)
            Log.e(TAG, "\n\n ======= Exception ENDS ========\n\n")

        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    companion object {
        private const val TAG = "ApplicationClass"
        @SuppressLint("StaticFieldLeak")
        lateinit var appContext: Context
    }

}
