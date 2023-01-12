package com.jef4tech.interviewnotifier.ui.gallery

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.jef4tech.interviewnotifier.InterviewApplication
import com.jef4tech.interviewnotifier.databinding.FragmentGalleryBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.utils.Extension.isValidInput
import com.jef4tech.interviewnotifier.utils.Extension.showToast
import java.text.SimpleDateFormat
import java.util.Locale

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
//    private lateinit var galleryViewModel:GalleryViewModel
    private var location_spinner = arrayOf("THRISSUR", "KOCHI", "BANGLORE")
    private var jobType_spinner = arrayOf("HYBRID","WORK FROM OFFICE","WORK FROM HOME")
//    val builder = MaterialDatePicker.Builder.datePicker()
    val datePicker:MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker()
    .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).setTitleText("SELECT DATE OF INTERVIEW")
    .build()
//    val picker = builder.build()
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

        binding.edAdd.setOnClickListener{
            datePicker.show(requireFragmentManager(),datePicker.toString())
        }
        datePicker.addOnPositiveButtonClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val date = sdf.format(it)
            binding.edDate.setText(date)
        }
        return root

//        submitJob()
    }

    private fun submitJob() {
        val company = binding.edCompanyName.editText?.text.toString().trim()
        val location = binding.spinnerLocation.selectedItem.toString()
        val jobType = binding.spinnerType.selectedItem.toString()
        val salaryRange = binding.edSalaryRange.editText?.text.toString().trim()
        val date = binding.edDate.text.toString().trim()
        val validation_result = validation(location,jobType,salaryRange,company,date)
        if (validation_result=="good"){
            context?.let { showToast("submit job", it) }
        val interviewData = InterviewList(company,location,jobType,salaryRange,date,null)
        galleryViewModel.insert(interviewData)
            view?.let { Navigation.findNavController(it).navigate(com.jef4tech.interviewnotifier.R.id.action_nav_gallery_to_nav_home) }

        } else{
            context?.let { showToast(validation_result, it) }
        }

    }

    private fun validation(
        location: String,
        jobType: String,
        salaryRange: String,
        company: String,
        date: String
    ):String {
        if (!isValidInput(location)){
            return "Location is Empty"
        }
        if (!isValidInput(company)){
            return "Enter a Company Name"
        }
        if(date=="00/00/00"){
            return "Enter a Valid Date"
        }
        return "good"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}