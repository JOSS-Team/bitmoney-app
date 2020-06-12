package com.awrcorp.bitmoney_app.ui.main.wallet

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
import com.awrcorp.bitmoney_app.databinding.FragmentWalletBinding
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Income
import com.awrcorp.bitmoney_app.vo.Outcome
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        viewModel = ViewModelProvider(this, WalletViewModelFactory.getInstance(requireContext()))[WalletViewModel::class.java]

        walletAdapter = WalletAdapter()
        binding.rvIncome.layoutManager = LinearLayoutManager(context)
        binding.rvIncome.adapter = walletAdapter
        binding.rvIncome.setHasFixedSize(true)

        viewModel.user.observe(this.viewLifecycleOwner, Observer { user ->
            binding.tvBalanceWallet.text = "Rp " + user.balance.toString()
        })

        binding.fabTambah.setOnClickListener {
            view.findNavController().navigate(R.id.action_walletFragment_to_inputWalletFragment2)
        }

        val id = Anicantik.getInstance(requireContext()).getId()
        updateList(id)

        walletAdapter.setOnClickListener(object : WalletAdapter.OnClickListener {
            override fun onCLick(income: Income) {
                ApiClient.instance.deleteIncome(income.incomeId).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        updateWallet(income.amount)
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

    private fun updateWallet(amount : Int){
        viewModel.user.observe(this.viewLifecycleOwner, Observer {user ->
            val userId = viewModel.userId
            if(user!=null){
                viewModel.updateUser(userId, user.name!!, user.email!!, user.password!!, user.balance - amount).observe(this.viewLifecycleOwner, Observer {
                    binding.tvBalanceWallet.text = "Rp " + (user.balance+amount).toString()
                })
            }
        })
    }

    private fun updateList(id : Int){
        var incomeList: MutableList<Income> = mutableListOf()
        ApiClient.instance.getIncomes(id).enqueue(object : Callback<List<Income>> {
            override fun onResponse(call: Call<List<Income>>, response: Response<List<Income>>) {
                val incomeResponse = response.body()
                if (incomeResponse != null){
                    incomeResponse.forEach {
                        if (it.user == id){
                            incomeList.add(it)
                        }
                    }
                    walletAdapter.setIncomeList(incomeList)
                }
            }

            override fun onFailure(call: Call<List<Income>>, t: Throwable) {
                Log.e(ContentValues.TAG, t.toString())
            }
        })
    }
}
