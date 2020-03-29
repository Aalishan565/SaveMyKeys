package com.savemykeys.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.savemykeys.db.entity.Memory

@Dao
interface MemoryDao {

    @Insert
    fun insertMemory(memory: Memory)

    @Update
    fun updateMemory(memory: Memory)

    @Query("SELECT * from memory_table")
    fun getMemory(): LiveData<List<Memory>>

    @Delete
    fun deleteMemory(memory: Memory)

    @Query("DELETE FROM memory_table")
    fun deleteAllMemory()

}