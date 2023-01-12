package com.jef4tech.interviewnotifier.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.jef4tech.interviewnotifier.databinding.InterviewAdapterBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.utils.Extension

/**
 * @author jeffin
 * @date 02/01/23
 */
class InterviewAdapter(private val listener: (position: InterviewList,type:Int) -> Unit):RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder>() {

inner class InterviewViewHolder(val custombind:InterviewAdapterBinding):RecyclerView.ViewHolder(custombind.root)
    private var jobList:List<InterviewList> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterviewViewHolder {
        val itemBinding = InterviewAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InterviewViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return jobList.size
    }

    override fun onBindViewHolder(holder: InterviewViewHolder, position: Int) {
        val job = jobList[position]
        holder.custombind.companyName.text=job.companyName
        holder.custombind.placeName.text=job.location
        holder.custombind.jobType.text=job.jobType
        holder.custombind.dateScheduled.text=job.Date
        holder.custombind.delete.setOnClickListener{
            listener.invoke(job,0)
//            Extension.showToast("delete",holder.custombind.company.context)
        }
        holder.custombind.edit.setOnClickListener{
            listener.invoke(job,1)
        }
        holder.custombind.alarm.setOnClickListener {
            listener.invoke(job,2)
        }
    }

    fun setjobList(interviewLists: List<InterviewList>) {
        this.jobList = interviewLists
        notifyDataSetChanged()
    }
}

