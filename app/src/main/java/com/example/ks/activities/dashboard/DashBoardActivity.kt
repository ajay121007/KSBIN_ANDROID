package com.example.ks.activities.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.activities.profile.ProfileActivity
import com.example.ks.R
import com.example.ks.activities.ChangeMyPolicyDetialsActivity
import com.example.ks.activities.FileClaimActivity
import com.example.ks.activities.UploadDocumentActivity
import com.example.ks.activities.document.IdCardDocumentActivity
import com.example.ks.activities.payment.PaymentActivity
import com.example.ks.activities.signabledocument.SignableDocumentActivity
import com.example.ks.adapters.Documents
import com.example.ks.adapters.GridAdapter
import com.example.ks.adapters.OnItemPositionClick
import com.example.ks.adapters.PolicyAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityDashboardBinding
import com.example.ks.databinding.PolicyCardLayoutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DashBoardActivity : BaseActivity(),OnDashBoardActions,OnItemPositionClick {
    lateinit var activityDashboardBinding: ActivityDashboardBinding
    private val dashBoardViewModel:DashBoardViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        activityDashboardBinding.viewModel=dashBoardViewModel
        activityDashboardBinding.handler=this
        activityDashboardBinding.lifecycleOwner=this
        activityDashboardBinding.executePendingBindings()
        dashBoardViewModel.getDashBoardData()
        setAdapter()
        bindObserver()
    }

    private fun bindObserver() {
        dashBoardViewModel.data.observe(this, Observer {
            activityDashboardBinding.rvPolicy.apply {
              setViewListener {position->
                    val binding=DataBindingUtil.inflate<PolicyCardLayoutBinding>(layoutInflater,R.layout.policy_card_layout, null, false)
                    binding.model=it?.data?.policies?.get(position)
                    binding.executePendingBindings()
                  binding.root
              }
                pageCount = it.data?.policies?.size?:0
                }


        })
    }

    private fun setAdapter() {
        
      
        val gridAdapter = GridAdapter(this,this)
        activityDashboardBinding.gridView.adapter = gridAdapter
    }

    override fun onTapProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    override fun onItem(documents: Documents) {
        when(documents){
            Documents.SIGNABLE_DOCS -> startActivity(Intent(this,SignableDocumentActivity::class.java))
            Documents.PAYMENT -> startActivity(Intent(this,PaymentActivity::class.java))
            Documents.ID_CARDS -> startActivity(Intent(this,IdCardDocumentActivity::class.java))
            Documents.CHANGE_MY_DOCS -> startActivity(Intent(this,ChangeMyPolicyDetialsActivity::class.java))
            Documents.UPLOAD_DOCS -> startActivity(Intent(this,UploadDocumentActivity::class.java))
            Documents.CLAIM_FILE -> startActivity(Intent(this,FileClaimActivity::class.java))
        }
    }
}
interface OnDashBoardActions{
    fun onTapProfile()
}