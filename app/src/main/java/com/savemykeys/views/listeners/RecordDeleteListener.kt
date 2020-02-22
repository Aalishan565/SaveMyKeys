package com.savemykeys.views.listeners

import com.savemykeys.db.entity.Key
import com.savemykeys.db.entity.Memory
import com.savemykeys.db.entity.Reminder

interface RecordDeleteListener {

    fun deleteKey(key: Key) {

    }

    fun deleteKey(key: Memory) {

    }

    fun deleteKey(key: Reminder) {

    }
}