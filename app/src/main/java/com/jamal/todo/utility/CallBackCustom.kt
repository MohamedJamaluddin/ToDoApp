package com.jamal.todo.utility

interface CallBackCustom<T, S> {

    fun handleCallback(t: T, s: S)

    fun handleCallbackWithType(t: T, s: S, type: String)
}