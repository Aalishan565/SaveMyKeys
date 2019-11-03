package com.savemykeys.views.presenters

interface SignUpPresenter {
    fun doSignUp(pin: String, confirmPin: String)
}