package com.awrcorp.bitmoney_app.ui.auth.register

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
import com.awrcorp.bitmoney_app.databinding.FragmentRegisterBinding
import com.awrcorp.bitmoney_app.utils.showMessage
import com.awrcorp.bitmoney_app.utils.Anicantik

class RegisterFragment : Fragment() {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_register,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, RegisterViewModelFactory.getInstance(requireContext()))[RegisterViewModel::class.java]

        binding.btnRegister.setOnClickListener{
            register()
        }

        binding.tvHaveAccount.setOnClickListener{
            view.findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    private fun register() {
        val name = binding.etUsernameRegister.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        if(isInvalid(name, email, password)) return

        viewModel.register(name, email, password).observe(this.viewLifecycleOwner, Observer { responseCode ->
            if (responseCode != null) {
                when (responseCode) {
                    404 -> context?.showMessage("try another email")
                    408 -> context?.showMessage("check your connection and try again")
                    else -> {
                        Anicantik.getInstance(requireContext()).saveId(responseCode)
                        view?.findNavController()?.navigate(R.id.action_registerFragment_to_homeActivity)
                        this.activity?.finish()
                    }
                }
            }
        })
    }

    private fun isInvalid(name : String, email : String, password : String) : Boolean{
        var invalid = false
        if (name.isEmpty()) {
            binding.etUsernameRegister.error = "Nama belum diisi"
            invalid = true
        }
        if (email.isEmpty()) {
            binding.etEmailRegister.error = "Email belum diisi"
            invalid = true
        }
        if (password.isEmpty()) {
            binding.etPasswordRegister.error = "Password belum diisi"
            invalid = true
        }
        return invalid
    }
}
