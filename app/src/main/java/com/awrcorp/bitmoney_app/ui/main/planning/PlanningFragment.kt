package com.awrcorp.bitmoney_app.ui.main.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentPlanningBinding

/**
 * A simple [Fragment] subclass.
 */
class PlanningFragment : Fragment() {

    private lateinit var viewModel : PlanningViewModel
    private lateinit var binding : FragmentPlanningBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_planning,
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
