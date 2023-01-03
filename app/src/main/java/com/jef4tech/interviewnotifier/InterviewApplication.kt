package com.jef4tech.interviewnotifier

import android.app.Application
import com.jef4tech.interviewnotifier.db.InterViewDatabase
import com.jef4tech.interviewnotifier.repository.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * @author jeffin
 * @date 31/12/22
 */
class InterviewApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { InterViewDatabase.invoke(this) }
    val repository by lazy { JobRepository(database.interviewDao()) }
}