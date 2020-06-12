package com.awrcorp.bitmoney_app.ui.main.wallet

import android.content.ContentValues.TAG
import android.net.DnsResolver
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
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentInputBinding
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.repository.AppRepository
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.utils.showMessage
import com.awrcorp.bitmoney_app.vo.Income
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class InputWalletFragment : Fragment() {

    private lateinit var viewModel : WalletViewModel
    private lateinit var binding : FragmentInputBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_input,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, WalletViewModelFactory.getInstance(requireContext()))[WalletViewModel::class.java]

        binding.etCategory.visibility = View.GONE

        binding.btnAdd.setOnClickListener {
            addIncome()
        }
    }

    private fun addIncome(){
        val name = binding.etName.text.toString()
        val amount = binding.etNominal.text.toString().toInt()

        val pattern = "EEEE, dd MMMM yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        val date: String = simpleDateFormat.format(Date())

//        val date = binding.etDate.text.toString()

        val userId = viewModel.userId

        if(isInvalid(name, amount, date)) return

        viewModel.addIncome(name, amount, date, userId).observe(this.viewLifecycleOwner, Observer {

        })

        viewModel.user.observe(this.viewLifecycleOwner, Observer {user ->
            if(user!=null){
                viewModel.updateUser(userId, user.name!!, user.email!!, user.password!!, user.balance + amount).observe(this.viewLifecycleOwner, Observer {
                    view?.findNavController()?.navigate(R.id.action_inputWalletFragment2_to_walletFragment)
                })
            }
        })
    }

    private fun isInvalid(name: String, amount: Int?, date: String) : Boolean{
        var invalid = false

        if (name.isEmpty()) {
            binding.etName.error = "Nama belum diisi"
            invalid = true
        }
        if (amount == null) {
            binding.etNominal.error = "Nominal belum diisi"
            invalid = true
        }
//        if (date.isEmpty()) {
//            binding.etDate.error = "Tanggal belum diisi"
//            invalid = true
//        }
        return invalid
    }
}