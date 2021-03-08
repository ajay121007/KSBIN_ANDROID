package com.example.ks.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.forgot.ForgotPasswordActivity
import com.example.ks.activities.dashboard.DashBoardActivity
import com.example.ks.activities.loginsignup.LoginViewModel
import com.example.ks.common.BaseFragment
import com.example.ks.databinding.FragmentSignInBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class LoginFragment : BaseFragment() {

    lateinit var fragmentSignInBinding: FragmentSignInBinding
    val loginViewModel:LoginViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentSignInBinding=DataBindingUtil.inflate(layoutInflater,R.layout.fragment_sign_in, container, false)
        fragmentSignInBinding.apply {
            viewModel=loginViewModel
            lifecycleOwner=viewLifecycleOwner
            executePendingBindings()
            forgetText.setOnClickListener {
                startActivity(Intent(requireContext(), ForgotPasswordActivity::class.java))
            }
        }
        bindObserver()
        return fragmentSignInBinding.root
    }

    override fun showPicker( requestCode: Int) {
    }


    private fun bindObserver() {
        loginViewModel.onNavigate.observe(viewLifecycleOwner, Observer {
            if(it)
            startActivity(Intent(requireContext(), DashBoardActivity::class.java))
        })
    }


}