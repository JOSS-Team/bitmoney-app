package com.awrcorp.bitmoney_app.ui.main.home

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.awrcorp.bitmoney_app.utils.showMessage
import java.text.SimpleDateFormat
import java.util.*

class InputOutcomeFragment : Fragment() {

    private lateinit var viewModel : HomeViewModel
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

        viewModel = ViewModelProvider(this, HomeViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            addOutcome()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addOutcome(){
        val name = binding.etName.text.toString()
        val amount = binding.etNominal.text.toString().toInt()
        val category = binding.etCategory.text.toString()

        val pattern = "EEEE, dd MMMM yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.ENGLISH)
        val date: String = simpleDateFormat.format(Date())

//        val date = binding.etDate.text.toString()
        val isPlan = false
        val userId = viewModel.userId

        if(isInvalid(name, amount,category, date)) return

        viewModel.addOutcome(name, amount, category, date, isPlan, userId).observe(this.viewLifecycleOwner, Observer {

        })

        viewModel.user.observe(this.viewLifecycleOwner, Observer {user ->
            if(user!=null){
                viewModel.updateUser(userId, user.name!!, user.email!!, user.password!!, user.balance - amount).observe(this.viewLifecycleOwner, Observer {
                    view?.findNavController()?.navigate(R.id.action_inputOutcomeFragment_to_homeFragment)
                })
            }
        })
    }

    private fun isInvalid(name: String, amount: Int?, category: String, date: String) : Boolean{
        var invalid = false

        if (name.isEmpty()) {
            binding.etName.error = "Nama belum diisi"
            invalid = true
        }
        if (amount == null) {
            binding.etNominal.error = "Nominal belum diisi"
            invalid = true
        }
        if (category.isEmpty()) {
            binding.etNominal.error = "Kategori belum diisi"
            invalid = true
        }
//        if (date.isEmpty()) {
//            binding.etDate.error = "Tanggal belum diisi"
//            invalid = true
//        }
        return invalid
    }
}