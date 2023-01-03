package com.jef4tech.interviewnotifier.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.repository.JobRepository
import kotlinx.coroutines.launch

class GalleryViewModel(private val repository: JobRepository) : ViewModel() {
    val allitems:LiveData<List<InterviewList>> = repository.allJobs.asLiveData()
    fun insert(interviewList: InterviewList) = viewModelScope.launch {
        // Call the repository function and pass the details.
        repository.insertJobs(interviewList)
    }

    fun deleteJob(id: InterviewList)=viewModelScope.launch {
        repository.deleteJob(id)
    }
    fun updateJobs(interviewList: InterviewList)=viewModelScope.launch {
        repository.updateJob(interviewList)
    }
}