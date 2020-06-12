package com.awrcorp.bitmoney_app.ui.main.profile

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
import com.awrcorp.bitmoney_app.databinding.FragmentEditProfileBinding
import com.awrcorp.bitmoney_app.utils.showMessage

class EditProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_edit_profile,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ProfileViewModelFactory.getInstance(requireContext()))[ProfileViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            updateUser()
        }

    }

    private fun updateUser(){
        val userId = viewModel.userId
        val name = binding.editNama.text.toString()
        val email = binding.editAlamat.text.toString()
        val password = binding.editpass.text.toString()
        val balance = viewModel.user.value?.balance

        if (balance != null) {
            viewModel.updateUser(userId, name, email, password, balance).observe(this.viewLifecycleOwner, Observer {
                if(it!=null){
                    context?.showMessage(it)
                    view?.findNavController()?.navigate(R.id.action_editProfileFragment_to_profileFragment)
                }
            })
        }
    }
}