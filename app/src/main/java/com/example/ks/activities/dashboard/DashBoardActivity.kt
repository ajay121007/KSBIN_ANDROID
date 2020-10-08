package com.example.ks.activities.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.ks.activities.profile.ProfileActivity
import com.example.ks.R
import com.example.ks.adapters.GridAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DashBoardActivity : BaseActivity(),OnDashBoardActions {
    lateinit var activityDashboardBinding: ActivityDashboardBinding
    private val dashBoardViewModel:DashBoardViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        activityDashboardBinding.handler=this
        activityDashboardBinding.lifecycleOwner=this
        activityDashboardBinding.executePendingBindings()
        dashBoardViewModel.getDashBoardData()
        setAdapter()
    }

    private fun setAdapter() {
        val gridAdapter = GridAdapter(this)
        activityDashboardBinding.gridView.adapter = gridAdapter
    }

    override fun onTapProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
}
interface OnDashBoardActions{
    fun onTapProfile()
}