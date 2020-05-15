package com.awrcorp.bitmoney_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), View.OnClickListener{

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
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
        val username = et_username_register.text.toString()
        val email = et_email_register.text.toString()
        val password = et_password_register.text.toString()

        if (username.isEmpty()) {
            et_username_register.error = "Username belum diisi"
            ready = false
        }
        if (email.isEmpty()) {
            et_email_register.error = "Email belum diisi"
            ready = false
        }
        if (password.isEmpty()) {
            et_password_register.error = "Password belum diisi"
            ready = false
        }
        if (ready) {
            this.view?.findNavController()?.navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

}
