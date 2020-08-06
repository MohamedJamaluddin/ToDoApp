package com.jamal.todo.view.common

import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(value = ["setTextViewDefault", "setTextView"], requireAll = false)
    fun setTextView(view: TextView, defaultText: String?, actualValue: String?) {
        if (TextUtils.isEmpty(actualValue))
            view.text = defaultText
        else
            view.text = actualValue
    }
}