package com.awrcorp.bitmoney_app.ui.main.wallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.awrcorp.bitmoney_app.R
import com.awrcorp.bitmoney_app.databinding.FragmentWalletBinding

/**
 * A simple [Fragment] subclass.
 */
class WalletFragment : Fragment() {

    private lateinit var binding : FragmentWalletBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_wallet,
                container,
                false
        )
        return binding.root
    }

}
