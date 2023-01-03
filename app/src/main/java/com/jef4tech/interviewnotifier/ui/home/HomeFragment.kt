package com.jef4tech.interviewnotifier.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jef4tech.interviewnotifier.InterviewApplication
import com.jef4tech.interviewnotifier.adapters.InterviewAdapter
import com.jef4tech.interviewnotifier.databinding.FragmentHomeBinding
import com.jef4tech.interviewnotifier.models.InterviewList
import com.jef4tech.interviewnotifier.ui.CustomDialog
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModel
import com.jef4tech.interviewnotifier.ui.gallery.GalleryViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var interviewAdapter:InterviewAdapter
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

        }else{
//            galleryViewModel.updateJobs(Id)
            showDialog(Id)
        }
//        val bundle= Bundle()
//        bundle.putInt("fixtureid",Id.fixture.id)
//        bundle.putString("homeTeam", Id.teams.home.name)
//        bundle.putString("awayTeam",Id.teams.away.name)
//        view?.let { Navigation.findNavController(it).navigate(R.id.action_navigation_home_to_navigation_statistic,bundle) }
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