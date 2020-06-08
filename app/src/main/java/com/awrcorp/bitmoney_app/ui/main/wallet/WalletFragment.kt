package com.awrcorp.bitmoney_app.ui.main.wallet

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
import com.awrcorp.bitmoney_app.databinding.FragmentWalletBinding

/**
 * A simple [Fragment] subclass.
 */
class WalletFragment : Fragment() {

    private lateinit var viewModel : WalletViewModel
    private lateinit var binding : FragmentWalletBinding
    private lateinit var walletAdapter: WalletAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_wallet,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        walletAdapter = WalletAdapter()
        binding.rvIncome.layoutManager = LinearLayoutManager(context)
        binding.rvIncome.adapter = walletAdapter
        binding.rvIncome.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, WalletViewModelFactory.getInstance(requireContext()))[WalletViewModel::class.java]
        viewModel.incomes.observe(this.viewLifecycleOwner, Observer { incomeList ->
            walletAdapter.setIncomeList(incomeList)
        })
    }
}
