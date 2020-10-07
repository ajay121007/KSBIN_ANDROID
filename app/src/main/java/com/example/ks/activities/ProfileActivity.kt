package com.example.ks.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.adapters.GridAdapter
import com.example.ks.databinding.ActivityProfileBinding
import com.example.ks.model.GridItems

class ProfileActivity : AppCompatActivity() {
    lateinit var activityProfileBinding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityProfileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)



        setAdapter()
    }

    private fun setAdapter() {
        val gridAdapter = GridAdapter(this)
        activityProfileBinding.gridView.adapter = gridAdapter


    }
}
