package com.savemykeys.views.listeners

import com.savemykeys.db.entity.Key

interface KeyDeleteListener {

    fun deleteKey(key: Key)
}