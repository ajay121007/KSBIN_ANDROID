package com.example.ks.activities.profile

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.editprofile.EditProfileActivity
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProfileActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {
   lateinit var profileBinding: ActivityProfileBinding
    private val profileViewModel:ProfileViewModel by viewModel { parametersOf(this)  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        profileBinding.viewModel=profileViewModel
        profileBinding.apply {
            rowName.apply{
                viewModel=profileViewModel
                lifecycleOwner=this@ProfileActivity
                executePendingBindings()

            }
            toolbar.setOnMenuItemClickListener(this@ProfileActivity)
        }
        profileBinding.lifecycleOwner=this
        profileBinding.executePendingBindings()
        bindObeserver()

        }

    private fun bindObeserver() {
        profileViewModel.logout.observe(this, Observer {
            if (it) {
                val i = Intent(this, LoginSignUpActivity::class.java)
// set the new task and clear flags
// set the new task and clear flags
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.getProfileData()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.edit -> {
                startActivity(Intent(this, EditProfileActivity::class.java))
                true
            }
           else -> true
        }
    }
}

