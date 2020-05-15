package com.awrcorp.bitmoney_app.ui.auth.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {

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
        binding.btnLogin.setOnClickListener(this)
        binding.tvCreateAccount.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_login -> {
                checkInput()
//                this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
            }
            R.id.tv_create_account -> {
                this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun checkInput() {
        var ready = true
        val email = binding.etEmailLogin.text.toString()
        val password = binding.etPasswordLogin.text.toString()

        if (email.isEmpty()) {
            binding.etEmailLogin.error = "Email belum diisi"
            ready = false
        }
        if (password.isEmpty()) {
            binding.etEmailLogin.error = "Password belum diisi"
            ready = false
        }
        if (ready) {
//            login()
            this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
            this.activity?.finish()
        }
    }
}
