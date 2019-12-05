package com.savemykeys.views.listeners

interface SignUpViewListener {

    fun signUpSuccess(signUpSuccessMessage: Int)
    fun showEmptyPinError(emptyPinMessage: Int)
    fun showPinDoesNotMatchError(pinDoesNotMatch: Int)

}