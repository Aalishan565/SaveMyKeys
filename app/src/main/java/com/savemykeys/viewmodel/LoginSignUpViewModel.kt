package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.savemykeys.R
import com.savemykeys.repositories.UserRepository
import com.savemykeys.views.listeners.LoginViewListener
import com.savemykeys.views.listeners.SignUpViewListener

class LoginSignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LoginSignUpViewModel"
    private lateinit var loginViewListener: LoginViewListener
    private lateinit var signUpViewListener: SignUpViewListener
    private var userRepository: UserRepository = UserRepository(application)

    fun setLoginViewListener(loginViewListener: LoginViewListener) {
        Log.d(TAG, "setLoginViewListener()")
        this.loginViewListener = loginViewListener

    }

    fun setSignUpViewListener(signUpViewListener: SignUpViewListener) {
        Log.d(TAG, "setSignUpViewListener()")
        this.signUpViewListener = signUpViewListener

    }

    fun doLogin(pin: String) {
        Log.d(TAG, "doLogin() pin: $pin")
        if (pin.isBlank()) {
            Log.d(TAG, "pin is blank")
            loginViewListener.showEmptyPinError(R.string.emptyPinMessage)
            return
        }
        if (userRepository.doLogin(pin)) {
            Log.d(TAG, "doLogin() success pin: $pin")
            loginViewListener.loginSuccess(R.string.loginSuccess)
        } else {
            Log.d(TAG, "doLogin() fail")
            loginViewListener.loginFail(R.string.userNotFound)
        }
    }

    fun doSignUp(pin: String, confirmPin: String) {
        Log.d(TAG, "doSignUp() pin: $pin confirmPin: $confirmPin")
        if (pin.isBlank()) {
            Log.d(TAG, "doSignUp() pin is blank")
            signUpViewListener.showEmptyPinError(R.string.emptyPinMessage)
            return
        } else if (confirmPin.isBlank()) {
            Log.d(TAG, "doSignUp() confirmPin is Blank")
            signUpViewListener.showEmptyPinError(R.string.emptyPinMessage)
        } else if (!pin.equals(confirmPin)) {
            Log.d(TAG, "confirm pin $confirmPin and pin $pin does not match")
            signUpViewListener.showPinDoesNotMatchError(R.string.pinDoesNotMatch)
        } else {
            userRepository.doSignUp(pin)
            signUpViewListener.signUpSuccess(R.string.signUpSuccess)
        }
    }
}