package com.savemykeys.views.impls

import android.content.Context
import com.savemykeys.R
import com.savemykeys.views.presenters.SignUpPresenter
import com.savemykeys.views.listeners.SignUpViewListener
import com.savemykeys.utils.AppSharedPreference

class SignUpPresenterImpl(private val context:Context, private val signUpViewListener: SignUpViewListener) : SignUpPresenter {

    override fun doSignUp(pin: String, confirmPin: String) {
        if (pin.isBlank()) {
            signUpViewListener.showEmptyPinError(R.string.emptyPinMessage)
            return
        }else if (confirmPin.isBlank()){
            signUpViewListener.showEmptyPinError(R.string.emptyPinMessage)
        }
        else if (!pin.equals(confirmPin)){
            signUpViewListener.showPinDoesNotMatchError(R.string.pinDoesNotMatch)
        }else{
         var appPref: AppSharedPreference = AppSharedPreference.instance
            appPref.saveBooleanPref(context,pin,true)
            signUpViewListener.signUpSuccess(R.string.signUpSuccess)
        }

    }
}