package com.jef4tech.interviewnotifier.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.jef4tech.interviewnotifier.repository.JobRepository

/**
 * @author jeffin
 * @date 30/12/22
 */
 class GalleryViewModelFactory(private val repository: JobRepository):ViewModelProvider.Factory {
 override fun <T : ViewModel> create(modelClass: Class<T>): T {
  if (modelClass.isAssignableFrom(GalleryViewModel::class.java)) {
   @Suppress("UNCHECKED_CAST")
   return GalleryViewModel(repository) as T
  }
  throw IllegalArgumentException("Unknown ViewModel class")
 }


}