package com.awrcorp.bitmoney_app.ui.main.planning

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
import com.awrcorp.bitmoney_app.ui.main.wallet.WalletViewModel
import com.awrcorp.bitmoney_app.ui.main.wallet.WalletViewModelFactory
import com.awrcorp.bitmoney_app.utils.showMessage

class InputPlanningFragment : Fragment() {

    private lateinit var viewModel : PlanningViewModel
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
        viewModel = ViewModelProvider(this, PlanningViewModelFactory.getInstance(requireContext()))[PlanningViewModel::class.java]
//        binding.etDate.visibility = View.GONE
        binding.btnAdd.setOnClickListener {
            addPlanning()
        }
    }

    private fun addPlanning(){
        val name = binding.etName.text.toString()
        val amount = binding.etNominal.text.toString().toInt()
        val category = binding.etCategory.text.toString()
        val isPlan = true
        val userId = viewModel.userId

        if(isInvalid(name, amount, category)) return

        viewModel.addPlanning(name, amount, category, isPlan, userId).observe(this.viewLifecycleOwner, Observer {
            if(it!=null){
                view?.findNavController()?.navigate(R.id.action_inputPlanningFragment2_to_planningFragment)
            }
        })
    }

    private fun isInvalid(name: String, amount: Int?, category: String) : Boolean{
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
            binding.etCategory.error = "Kategori belum diisi"
            invalid = true
        }
        return invalid
    }
}