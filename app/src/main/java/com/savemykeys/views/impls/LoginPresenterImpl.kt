package com.savemykeys.views.impls

import android.content.Context
import com.savemykeys.R
import com.savemykeys.views.presenters.LoginPresenter
import com.savemykeys.views.listeners.LoginViewListener
import com.savemykeys.utils.AppSharedPreference

class LoginPresenterImpl(
    private val context: Context,
    private val loginViewListener: LoginViewListener
) : LoginPresenter {

    override fun doLogin(pin: String) {
        if (pin.isBlank()) {
            loginViewListener.showEmptyPinError(R.string.emptyPinMessage)
            return
        }
        var appSharedPreference: AppSharedPreference = AppSharedPreference.instance
        if (appSharedPreference.getBooleanPref(context, pin)) {
            loginViewListener.loginSuccess(R.string.loginSuccess)
        } else {
            loginViewListener.loginFail(R.string.userNotFound)
        }

    }
}