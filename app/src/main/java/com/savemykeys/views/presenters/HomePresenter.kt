package com.savemykeys.views.presenters

import com.savemykeys.db.entity.Record

interface HomePresenter {
    fun deleteRecord(record: Record)
}