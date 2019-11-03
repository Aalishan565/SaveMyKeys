package com.savemykeys.views.presenters

interface AddRecordPresenter {
    fun addRecord(url: String, userName: String, password: String, note: String)
}