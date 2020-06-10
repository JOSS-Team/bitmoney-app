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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentInputBinding
import com.awrcorp.bitmoney_app.network.ApiClient
import com.awrcorp.bitmoney_app.repository.AppRepository
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.awrcorp.bitmoney_app.vo.Income
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
//            PostWallet()
        }
    }

    private fun isInvalid(nominal : Int?, name : String, date: String) : Boolean{
        var invalid = false
        if (nominal == null) {
            binding.etNominal.error = "Nominal belum diisi"
            invalid = true
        }
        if (name.isEmpty()) {
            binding.etName.error = "Nama belum diisi"
            invalid = true
        }
        if (date.isEmpty()) {
            binding.etDate.error = "Tanggal belum diisi"
            invalid = true
        }
        return invalid
    }
}