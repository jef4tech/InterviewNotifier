package com.jef4tech.interviewnotifier.db

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jef4tech.interviewnotifier.models.InterviewList
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(interviewList: InterviewList)

    @Query("SELECT * FROM interviewList")
    fun getAll(): Flow<List<InterviewList>>

    @Delete
    suspend fun delete(interviewList: InterviewList)
    @Update
    suspend fun updateJob(interviewList: InterviewList)
}