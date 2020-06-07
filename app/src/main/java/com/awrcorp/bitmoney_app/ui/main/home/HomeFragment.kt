package com.awrcorp.bitmoney_app.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentHomeBinding
import com.awrcorp.bitmoney_app.ui.main.planning.PlanningViewModel
import com.awrcorp.bitmoney_app.ui.main.planning.PlanningViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel : PlanningViewModel
    private lateinit var binding : FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, PlanningViewModelFactory.getInstance(requireContext()))[PlanningViewModel::class.java]

    }
}
