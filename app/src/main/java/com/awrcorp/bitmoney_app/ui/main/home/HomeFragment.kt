package com.awrcorp.bitmoney_app.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentHomeBinding
import com.awrcorp.bitmoney_app.vo.Outcome

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
        viewModel.histories.observe(this.viewLifecycleOwner, Observer { listHistory ->
            if(listHistory!=null){
                countSpentAmount(listHistory)
                homeAdapter.setHistoryList(listHistory)
            }
        })

        viewModel.user.observe(this.viewLifecycleOwner, Observer { user ->
            binding.tvHi.text = "Hi " + user.name
            binding.tvAmountWallet.text = "Rp " + user.balance.toString()
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
