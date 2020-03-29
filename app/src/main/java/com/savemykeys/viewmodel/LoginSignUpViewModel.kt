package com.savemykeys.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.savemykeys.R
import com.savemykeys.repositories.UserRepository

class LoginSignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "LoginSignUpViewModel"
    private var userRepository: UserRepository = UserRepository(application)
    private var loginStatus: MutableLiveData<Int> = MutableLiveData()
    private var signUpStatus: MutableLiveData<Int> = MutableLiveData()

    fun getLoginStatus(): LiveData<Int> {
        return loginStatus
    }

    fun getSignUpStatus(): LiveData<Int> {
        return signUpStatus
    }

    fun doLogin(pin: String) {
        Log.d(TAG, "doLogin() pin: $pin")
        if (pin.isBlank()) {
            Log.d(TAG, "pin is blank")
            loginStatus.value = R.string.emptyPinMessage
            return
        } else if (userRepository.doLogin(pin)) {
            Log.d(TAG, "doLogin() success pin: $pin")
            loginStatus.value = R.string.loginSuccess

        } else {
            Log.d(TAG, "doLogin() fail")
            loginStatus.value = R.string.userNotFound
        }
    }

    fun doSignUp(pin: String, confirmPin: String) {
        Log.d(TAG, "doSignUp() pin: $pin confirmPin: $confirmPin")
        when {
            pin.isBlank() -> {
                Log.d(TAG, "doSignUp() pin is blank")
                signUpStatus.value = R.string.emptyPinMessage
                return
            }
            confirmPin.isBlank() -> {
                Log.d(TAG, "doSignUp() confirmPin is Blank")
                signUpStatus.value = R.string.emptyPinMessage
                return
            }
            pin != confirmPin -> {
                Log.d(TAG, "confirm pin $confirmPin and pin $pin does not match")
                signUpStatus.value = R.string.pinDoesNotMatch
                return
            }
            else -> {
                userRepository.doSignUp(pin)
                signUpStatus.value = R.string.signUpSuccess
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("ViewMode", "LoginSignUpViewModel onCleared()")

    }
}