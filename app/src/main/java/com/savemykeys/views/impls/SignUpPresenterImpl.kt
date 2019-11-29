package com.savemykeys.views.impls

import android.content.Context
import com.savemykeys.R
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.views.presenters.SignUpPresenter
import com.savemykeys.views.listeners.SignUpViewListener
import com.savemykeys.utils.AppSharedPreference

class SignUpPresenterImpl(private val context:Context, private val signUpViewListener: SignUpViewListener) : SignUpPresenter {
    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null
    init {
        databaseInstance =
            (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance();
        recordDao = databaseInstance?.recordDao()
    }
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
            appPref.clearAllPrefs(context)
            signUpViewListener.signUpSuccess(R.string.signUpSuccess)
            appPref.saveBooleanPref(context,pin,true)
        }

    }

    override fun deleteAllPreviousRecords() {
        recordDao!!.deleteAllRecord()
    }
}