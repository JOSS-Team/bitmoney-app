package com.awrcorp.bitmoney_app.ui.main.home

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
import com.awrcorp.bitmoney_app.databinding.FragmentHomeBinding
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.utils.showMessage
import com.awrcorp.bitmoney_app.vo.Outcome
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel
    private lateinit var binding : FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter

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

        homeAdapter = HomeAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.adapter = homeAdapter
        binding.rvHistory.setHasFixedSize(true)

        binding.fabTambah.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_inputOutcomeFragment)
        }

        viewModel = ViewModelProvider(this, HomeViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]

        val id = Anicantik.getInstance(requireContext()).getId()
        updateList(id)

        homeAdapter.setOnClickListener(object : HomeAdapter.OnClickListener {
            override fun onCLick(outcome: Outcome) {
                ApiClient.instance.deleteOutcome(outcome.outcomeId).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
//                        updateList(id)
                        updateIncome(outcome.amount)
                        updateList(id)
                    }
                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
//                updateList(id)
            }
        })

        viewModel.user.observe(this.viewLifecycleOwner, Observer { user ->
            binding.tvHi.text = "Hi " + user.name
            binding.tvAmountWallet.text = "Rp " + user.balance.toString()
            Glide.with(this@HomeFragment)
                    .load(user.photo)
                    .into(binding.iconKategori)
        })
    }

    private fun updateIncome(amount : Int){
        viewModel.user.observe(this.viewLifecycleOwner, Observer {user ->
            val userId = viewModel.userId
            if(user!=null){
                viewModel.updateUser(userId, user.name!!, user.email!!, user.password!!, user.balance + amount).observe(this.viewLifecycleOwner, Observer {
                    binding.tvAmountWallet.text = "Rp " + (user.balance+amount).toString()
                })
            }
        })
    }

    private fun updateList(id : Int){
//        viewModel.histories.observe(this.viewLifecycleOwner, Observer { listHistory ->
//            if(listHistory!=null){
//                countSpentAmount(listHistory)
//                homeAdapter.setHistoryList(listHistory)
//            }
//        })
        var historyList: MutableList<Outcome> = mutableListOf()
        ApiClient.instance.getOutcomes(id).enqueue(object : Callback<List<Outcome>> {
            override fun onResponse(call: Call<List<Outcome>>, response: Response<List<Outcome>>) {
                val historyResponse = response.body()
                if (historyResponse != null){
                    historyResponse.forEach {
                        if (!it.isPlan && it.user == id){
                            historyList.add(it)
                        }
                    }
                    countSpentAmount(historyList)
                homeAdapter.setHistoryList(historyList)
                }
            }

            override fun onFailure(call: Call<List<Outcome>>, t: Throwable) {
                Log.e(ContentValues.TAG, t.toString())
            }
        })
    }

    private fun countSpentAmount(listHistory: List<Outcome>) {
        var amount = 0
        listHistory.forEach {
            amount+=it.amount
        }
        binding.tvAmountSpent.text = "Rp " + amount.toString()
    }
}
