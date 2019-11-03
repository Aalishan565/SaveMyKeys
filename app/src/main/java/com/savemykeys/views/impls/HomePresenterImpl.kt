package com.savemykeys.views.impls

import android.content.Context
import com.savemykeys.R
import com.savemykeys.db.AppDatabase
import com.savemykeys.db.daos.RecordDao
import com.savemykeys.db.entity.Record
import com.savemykeys.application.SaveMyKeysApplication
import com.savemykeys.views.listeners.HomeViewListener
import com.savemykeys.views.presenters.HomePresenter

class HomePresenterImpl(
    private val context: Context,
    private val homeViewListener: HomeViewListener) : HomePresenter {

    private var databaseInstance: AppDatabase? = null
    private var recordDao: RecordDao? = null

    init {
        databaseInstance =
            (context.applicationContext as SaveMyKeysApplication).getDatabaseInstance();
        recordDao = databaseInstance?.recordDao()
    }

    override fun deleteRecord(record: Record) {
        recordDao!!.deleteRecord(record)
        homeViewListener.successfullyDeleted(R.string.recordDeletedSuccessfully)

    }
}