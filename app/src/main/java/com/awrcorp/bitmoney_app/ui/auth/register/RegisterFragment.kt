package com.awrcorp.bitmoney_app.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentRegisterBinding
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener{

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
        btn_register.setOnClickListener(this)
        tv_have_account.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_register -> {
                checkInput()
//                this.view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
            }
            R.id.tv_have_account -> {
                this.view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun checkInput() {
        var ready = true
        val username = binding.etUsernameRegister.text.toString()
        val email = binding.etEmailRegister.text.toString()
        val password = binding.etPasswordRegister.text.toString()

        if (username.isEmpty()) {
            binding.etUsernameRegister.error = "Username belum diisi"
            ready = false
        }
        if (email.isEmpty()) {
            binding.etEmailRegister.error = "Email belum diisi"
            ready = false
        }
        if (password.isEmpty()) {
            binding.etPasswordRegister.error = "Password belum diisi"
            ready = false
        }
        if (ready) {
            this.view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}
