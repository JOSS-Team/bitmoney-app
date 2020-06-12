package com.awrcorp.bitmoney_app.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentLoginBinding
import com.awrcorp.bitmoney_app.utils.showMessage
import com.awrcorp.bitmoney_app.utils.Anicantik

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_login,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, LoginViewModelFactory.getInstance(requireContext()))[LoginViewModel::class.java]

        binding.btnLogin.setOnClickListener{
            login()
        }

        binding.tvCreateAccount.setOnClickListener{
            view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun login() {
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        if(isInvalid(email, password)) return

        viewModel.login(email, password).observe(this.viewLifecycleOwner, Observer { responseCode ->
            if (responseCode != null) {
                when (responseCode) {
                    404 -> context?.showMessage("account not found")
                    408 -> context?.showMessage("check your connection and try again")
                    else -> {
                        Anicantik.getInstance(requireContext()).saveId(responseCode)
                        view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
                        this.activity?.finish()
                    }
                }
            }
        })
    }

    private fun isInvalid(email : String, password : String) : Boolean{
        var invalid = false
        if (email.isEmpty()) {
            binding.etEmailLogin.error = "Email belum diisi"
            invalid = true
        }
        if (password.isEmpty()) {
            binding.etPasswordLogin.error = "Password belum diisi"
            invalid = true
        }
        return invalid
    }
}
