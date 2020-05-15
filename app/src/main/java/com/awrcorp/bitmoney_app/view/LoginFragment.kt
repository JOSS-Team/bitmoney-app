package com.awrcorp.bitmoney_app.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.awrcorp.bitmoney_app.R
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_login.setOnClickListener(this)
        tv_create_account.setOnClickListener(this)
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
        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()

        if (email.isEmpty()) {
            et_email_login.error = "Email belum diisi"
            ready = false
        }
        if (password.isEmpty()) {
            et_password_login.error = "Password belum diisi"
            ready = false
        }
        if (ready) {
//            login()
            this.view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeActivity)
            this.activity?.finish()
        }
    }
}
