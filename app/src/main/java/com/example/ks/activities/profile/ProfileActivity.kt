package com.example.ks.activities.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProfileActivity : BaseActivity() {
   lateinit var profileBinding: ActivityProfileBinding
//    private val profileViewModel:ProfileViewModel by viewModel { parametersOf(this)  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
//        profileViewModel.getProfileData()

        }
    }

