package com.savemykeys.views.listeners

interface LoginViewListener {
    fun loginSuccess(loginSuccessMessage: Int)
    fun loginFail(loginFailMessage: Int)
    fun showEmptyPinError(emptyPinMessage: Int)
}