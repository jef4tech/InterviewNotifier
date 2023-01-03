package com.jef4tech.interviewnotifier.ui.gallery

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jef4tech.interviewnotifier.InterviewApplication
import com.jef4tech.interviewnotifier.databinding.FragmentGalleryBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.utils.Extension.isValidInput
import com.jef4tech.interviewnotifier.utils.Extension.showToast

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
//    private lateinit var galleryViewModel:GalleryViewModel
    private var location_spinner = arrayOf("THRISSUR", "KOCHI", "BANGLORE")
    private var jobType_spinner = arrayOf("HYBRID","WORK FROM OFFICE","WORK FROM HOME")
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


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val location_adapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, location_spinner) }
        binding.spinnerLocation.adapter=location_adapter
        location_adapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

        val type_adapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, jobType_spinner) }
        binding.spinnerType.adapter=type_adapter
        type_adapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.buttonAdd.setOnClickListener{
            submitJob()
        }
        return root

//        submitJob()
    }

    private fun submitJob() {
        val company = binding.edCompanyName.editText?.text.toString().trim()
        val location = binding.spinnerLocation.selectedItem.toString()
        val jobType = binding.spinnerType.selectedItem.toString()
        val salaryRange = binding.edSalaryRange.editText?.text.toString().trim()
        val validation_result = validation(location,jobType,salaryRange,company)
        if (validation_result=="good"){
            context?.let { showToast("submit job", it) }
        val interviewData = InterviewList(company,location,jobType,salaryRange,null)
        galleryViewModel.insert(interviewData)
        } else{
            context?.let { showToast(validation_result, it) }
        }

    }

    private fun validation(location: String, jobType: String, salaryRange: String, company: String):String {
        if (!isValidInput(location)){
            return "location is empty"
        }
        if (!isValidInput(company)){
            return "company is empty"
        }
        return "good"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}