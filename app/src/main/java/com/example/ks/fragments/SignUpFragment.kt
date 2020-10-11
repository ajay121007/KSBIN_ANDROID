package com.example.ks.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.dashboard.DashBoardActivity
import com.example.ks.activities.loginsignup.SignUpViewModel
import com.example.ks.common.BaseFragment
import com.example.ks.databinding.FragmentSignUpBinding
import com.example.ks.setVisibility
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class SignUpFragment : BaseFragment() {
    lateinit var   binding:FragmentSignUpBinding
    private val loginViewModel:SignUpViewModel by viewModel{ parametersOf(this)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginViewModel.onNavigate.observe(this, Observer {
          //  Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireContext(),DashBoardActivity::class.java))
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate<FragmentSignUpBinding>(
            layoutInflater,
            R.layout.fragment_sign_up,
            container,
            false
        )
        binding.lifecycleOwner=viewLifecycleOwner
        binding.executePendingBindings()
        binding.viewModel=loginViewModel
//        loginViewModel.emailError.observe(this, Observer {
//            binding.tlName.error=it
//        })
        return binding.root
    }


}