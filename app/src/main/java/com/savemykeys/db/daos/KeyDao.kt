package com.savemykeys.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.savemykeys.db.entity.Key


@Dao
interface KeyDao {

    @Insert
    fun insertKey(key: Key)

    @Update
    fun updateKey(key: Key)

    @Query("SELECT * from record_table")
    fun getKeys(): LiveData<List<Key>>

    @Delete
    fun deleteKey(key: Key)

    @Query("DELETE FROM record_table")
    fun deleteAllKeys()

}