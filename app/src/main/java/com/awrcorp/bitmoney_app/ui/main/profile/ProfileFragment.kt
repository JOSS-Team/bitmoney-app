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
import com.awrcorp.bitmoney_app.databinding.FragmentProfileBinding
import com.awrcorp.bitmoney_app.utils.Anicantik
import com.bumptech.glide.Glide

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ProfileViewModelFactory.getInstance(requireContext()))[ProfileViewModel::class.java]

        binding.btnLogout.setOnClickListener {
            Anicantik.getInstance(requireContext()).removeId()
            view.findNavController().navigate(R.id.action_profileFragment_to_authActivity)
            this.activity?.finish()
        }

        binding.editProfile.setOnClickListener {
            view.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        viewModel.user.observe(this.viewLifecycleOwner, Observer {user ->
            if(user!=null){
                binding.tvNameProfile.text = user.name
                binding.tvEmailProfile.text = user.email
                Glide.with(this@ProfileFragment)
                        .load(user.photo)
                        .into(binding.iconKategori)
            }
        })
    }

}
