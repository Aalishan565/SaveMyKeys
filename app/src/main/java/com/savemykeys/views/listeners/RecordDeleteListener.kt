package com.savemykeys.views.listeners

import com.savemykeys.db.entity.Record
import java.text.FieldPosition

interface RecordDeleteListener {
    fun deleteRecord(record: Record)
}