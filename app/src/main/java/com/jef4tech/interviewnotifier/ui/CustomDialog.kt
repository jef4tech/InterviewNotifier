package com.jef4tech.interviewnotifier. ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.jef4tech.interviewnotifier.InterviewApplication
import com.jef4tech.interviewnotifier.databinding.DialogBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModel
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModelFactory
import com.jef4tech.interviewnotifier.utils.Extension
import com.jef4tech.interviewnotifier.utils.Extension.isValidInput
import com.jef4tech.interviewnotifier.utils.Extension.showToast
import java.text.SimpleDateFormat
import java.util.Locale

class CustomDialog(Id: InterviewList) : DialogFragment() {

    private var _binding: DialogBinding? = null
    private var interviewList:InterviewList=Id
//    private lateinit var galleryViewModel:GalleryViewModel
    private var location_spinner = arrayOf("THRISSUR", "KOCHI", "BANGLORE")
    private var jobType_spinner = arrayOf("HYBRID","WORK FROM OFFICE","WORK FROM HOME")
    private val galleryViewModel: GalleryViewModel by viewModels {
        GalleryViewModelFactory((activity?.application as InterviewApplication).repository)
    }
    val datePicker: MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker()
        .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR).setTitleText("SELECT DATE OF INTERVIEW")
        .build()
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = DialogBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.edCompanyName.editText?.setText(interviewList.companyName)
        binding.edSalaryRange.editText?.setText(interviewList.salaryRange)
        binding.edDate.setText(interviewList.Date)
        val location = interviewList.location
        val type = interviewList.jobType
        val location_adapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, location_spinner) }
        binding.spinnerLocation.adapter=location_adapter
        location_adapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        val position_location=location_adapter?.getPosition(location)
        binding.spinnerLocation.setSelection(position_location!!)

        val type_adapter = context?.let { ArrayAdapter(it, R.layout.simple_spinner_item, jobType_spinner) }
        binding.spinnerType.adapter=type_adapter
        type_adapter?.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        val position_type=type_adapter?.getPosition(type)
        binding.spinnerType.setSelection(position_type!!)

        binding.buttonAdd.setOnClickListener{
            submitJob()
        }
        binding.buttonDismiss.setOnClickListener{
            dialog?.dismiss()
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

    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    private fun submitJob() {
        val company = binding.edCompanyName.editText?.text.toString().trim()
        val location = binding.spinnerLocation.selectedItem.toString()
        val jobType = binding.spinnerType.selectedItem.toString()
        val salaryRange = binding.edSalaryRange.editText?.text.toString().trim()
        val date = binding.edDate.text.toString().trim()
        val validation_result = validation(location,jobType,salaryRange,company)
        if (validation_result=="good"){
            context?.let { showToast("submit job", it) }
        val interviewData = InterviewList(company,location,jobType,salaryRange,date,interviewList
            .id)
        galleryViewModel.updateJobs(interviewData)
            dialog?.dismiss()
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