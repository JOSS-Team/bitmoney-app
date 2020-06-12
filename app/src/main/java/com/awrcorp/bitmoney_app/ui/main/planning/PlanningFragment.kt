package com.awrcorp.bitmoney_app.ui.main.planning

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentPlanningBinding
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.ui.main.home.HomeAdapter
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Outcome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.fabTambah.setOnClickListener {
            view.findNavController().navigate(R.id.action_planningFragment_to_inputPlanningFragment2)
        }

        viewModel = ViewModelProvider(this, PlanningViewModelFactory.getInstance(requireContext()))[PlanningViewModel::class.java]

        val id = Anicantik.getInstance(requireContext()).getId()
        updateList(id)

        planningAdapter.setOnClickListener(object : PlanningAdapter.OnClickListener {
            override fun onCLick(outcome: Outcome) {
                ApiClient.instance.deleteOutcome(outcome.outcomeId).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        updateList(id)
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
//                updateList(id)
            }
        })
    }

    private fun updateList(id : Int){
        var planList: MutableList<Outcome> = mutableListOf()
        ApiClient.instance.getOutcomes(id).enqueue(object : Callback<List<Outcome>> {
            override fun onResponse(call: Call<List<Outcome>>, response: Response<List<Outcome>>) {
                val planResponse = response.body()
                if (planResponse != null){
                    planResponse.forEach {
                        if (it.isPlan && it.user == id){
                            planList.add(it)
                        }
                    }
                    countAmount(planList)
                    planningAdapter.setPlanList(planList)
                }
            }

            override fun onFailure(call: Call<List<Outcome>>, t: Throwable) {
                Log.e(ContentValues.TAG, t.toString())
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
