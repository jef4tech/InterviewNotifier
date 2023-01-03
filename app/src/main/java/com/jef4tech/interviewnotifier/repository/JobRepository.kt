package com.jef4tech.interviewnotifier.repository

import com.jef4tech.interviewnotifier.db.InterviewDao
import com.jef4tech.interviewnotifier.models.InterviewList
import kotlinx.coroutines.flow.Flow

/**
 * @author jeffin
 * @date 30/12/22
 */
class JobRepository(private val interviewDao: InterviewDao) {
    val allJobs: Flow<List<InterviewList>> = interviewDao.getAll()

    suspend fun insertJobs(interviewList: InterviewList){
        interviewDao.insert(interviewList)
    }

    suspend fun deleteJob(id: InterviewList) {
        interviewDao.delete(id)
    }

    suspend fun updateJob(interviewList: InterviewList){
        interviewDao.updateJob(interviewList)
    }

}