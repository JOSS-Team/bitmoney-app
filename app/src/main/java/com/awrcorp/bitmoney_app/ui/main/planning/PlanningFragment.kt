package com.awrcorp.bitmoney_app.ui.main.planning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentPlanningBinding
import com.awrcorp.bitmoney_app.vo.Outcome

/**
 * A simple [Fragment] subclass.
 */
class PlanningFragment : Fragment() {

    private lateinit var viewModel : PlanningViewModel
    private lateinit var binding : FragmentPlanningBinding
    private lateinit var planningAdapter: PlanningAdapter

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

        planningAdapter = PlanningAdapter()
        binding.rvPlan.layoutManager = LinearLayoutManager(context)
        binding.rvPlan.adapter = planningAdapter
        binding.rvPlan.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, PlanningViewModelFactory.getInstance(requireContext()))[PlanningViewModel::class.java]
        viewModel.plans.observe(this.viewLifecycleOwner, Observer { listPlan ->
            if(listPlan!=null){
                countAmount(listPlan)
                planningAdapter.setPlanList(listPlan)
            }
        })
    }

    private fun countAmount(listPlan: List<Outcome>) {
        var amount = 0
        listPlan.forEach {
            amount+=it.amount
        }
        binding.tvTotalPlanning.text = "Rp " + amount.toString()
    }

}
