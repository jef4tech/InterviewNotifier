package com.jef4tech.interviewnotifier.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jef4tech.interviewnotifier.InterviewApplication
import com.jef4tech.interviewnotifier.R
import com.jef4tech.interviewnotifier.adapters.InterviewAdapter
import com.jef4tech.interviewnotifier.databinding.FragmentHomeBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.ui.CustomDialog
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModel
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModelFactory
import com.jef4tech.interviewnotifier.utils.AndroidAlarmScheduler
import com.jef4tech.interviewnotifier.utils.Extension

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var interviewAdapter:InterviewAdapter
    private lateinit var scheduler:AndroidAlarmScheduler
    private val galleryViewModel:GalleryViewModel by viewModels {
        GalleryViewModelFactory((activity?.application as InterviewApplication).repository)
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
         scheduler = AndroidAlarmScheduler(requireActivity().applicationContext)
        val root: View = binding.root
        setupUI()
        setupRecyclerView()
        return root
    }

    private fun setupUI() {
        galleryViewModel.allitems.observe(viewLifecycleOwner){
            interviewAdapter.setjobList(it)
        }
    }

    private fun setupRecyclerView()=binding.jobRecyclerView.apply {
        interviewAdapter= InterviewAdapter{position,type -> onClick(position,type)}
        adapter=interviewAdapter
        layoutManager= LinearLayoutManager(context)
        setHasFixedSize(true)
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }
    private fun onClick(Id: InterviewList, type:Int) {
        //o for delete 1 for edit
        if (type==0){
            galleryViewModel.deleteJob(Id)

        }else if(type == 1){
//            galleryViewModel.updateJobs(Id)
            showDialog(Id)
        }else{
            scheduler.schedule()
        }
 }

    private fun showDialog(Id: InterviewList) {
        val dialog = CustomDialog(Id)
        fragmentManager?.let { dialog.show(it, "my_custom_dialog") }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}