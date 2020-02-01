package com.savemykeys.views.listeners

import com.savemykeys.db.entity.Record

interface RecordDeleteListener {

    fun deleteRecord(record: Record)
}