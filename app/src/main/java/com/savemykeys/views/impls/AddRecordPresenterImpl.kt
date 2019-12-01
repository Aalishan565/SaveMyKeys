package com.savemykeys.views.impls

import android.content.Context
import com.savemykeys.R
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record
import com.savemykeys.views.listeners.AddRecordViewListener
import com.savemykeys.views.presenters.AddRecordPresenter

class AddRecordPresenterImpl(
    private val context: Context,
    private val addRecordViewListener: AddRecordViewListener
) : AddRecordPresenter {

    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null
    private var record: Record? = null

    override fun addRecord(
        url: String,
        userName: String,
        password: String,
        note: String,
        insert: Boolean
    ) {
        if (url.isBlank()) {
            addRecordViewListener.showEmptyFieldMessage(R.string.urlMustNotBeEmpty)
            return
        } else if (userName.isBlank()) {
            addRecordViewListener.showEmptyFieldMessage(R.string.userNameMustNotBeEmpty)
            return
        } else if (password.isBlank()) {
            addRecordViewListener.showEmptyFieldMessage(R.string.passwordMustNotBeEmpty)
            return
        } else {
            record = Record(null,url, userName, password, note)
            databaseInstance =
                (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance();
            recordDao = databaseInstance?.recordDao()
            if (insert)recordDao?.insertRecord(record!!) else recordDao?.updateRecord(record!!)
            addRecordViewListener.addRecordSuccess(R.string.recordAddedSucessfully)
        }

    }
}