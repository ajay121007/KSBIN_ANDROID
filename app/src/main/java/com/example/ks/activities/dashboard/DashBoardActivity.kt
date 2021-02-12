package com.example.ks.activities.dashboard

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.*
import com.example.ks.activities.profile.ProfileActivity
import com.example.ks.R
import com.example.ks.activities.claim.FileClaimActivity
import com.example.ks.activities.document.IdCardDocumentActivity
import com.example.ks.activities.editprofile.EditProfileViewModel
import com.example.ks.activities.payment.PaymentActivity
import com.example.ks.activities.policydetail.ChangePolicyDetials1Activity
import com.example.ks.activities.profile.ProfileViewModel
import com.example.ks.activities.renewal.PaymentPlanActivity
import com.example.ks.activities.signabledocument.SignableDocumentActivity
import com.example.ks.activities.upload.UploadDocumentActivity
import com.example.ks.adapters.Documents
import com.example.ks.adapters.GridAdapter
import com.example.ks.adapters.OnItemPositionClick
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivityDashboardBinding
import com.example.ks.databinding.PolicyCardLayoutBinding
import com.example.ks.models.DashBoardResponse
import com.example.ks.repo.AuthRepo
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import ir.siaray.downloadmanagerplus.classes.Downloader
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import kotlin.random.Random


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
//        showDialogDownload("Success","http://epay.ksbin.com/invoice.php?id=0&nm=trs&em=4mit.inc@gmail.com&mt=card&fee=3.50&sub=100&ttl=103.50")
        bindObserver()
//        showDialogDownload("download","")
        val permissions =
            arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
        Permissions.check(
            this /*context*/,
            permissions,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {

                }
            })

    }

    private fun bindObserver() {
        dashBoardViewModel.data.observe(this, Observer {
            setAdapter(it)
            activityDashboardBinding.profileName.visibility= View.VISIBLE
            activityDashboardBinding.rvPolicy.apply {

              setViewListener {position->
                    val binding=DataBindingUtil.inflate<PolicyCardLayoutBinding>(layoutInflater,R.layout.policy_card_layout, null, false)
                    binding.model=it?.data?.policies?.get(position)
                    binding.executePendingBindings()
                  binding.root
              }
                pageCount = it.data?.policies?.size?:0
                UserConstants.policyArrayList= it.data?.policies as ArrayList<DashBoardResponse.Data.Policy>
                }


        })
    }

    private fun setAdapter(model: DashBoardResponse?) {
        
      
        val gridAdapter = GridAdapter(this,this,model)
        activityDashboardBinding.gridView.adapter = gridAdapter
    }

    override fun onTapProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    override fun onItem(documents: Documents) {

        when(documents){
            Documents.SIGNABLE_DOCS -> startActivityForResult(Intent(this,SignableDocumentActivity::class.java),100)
            Documents.PAYMENT -> startActivityForResult(Intent(this,PaymentActivity::class.java),10)
            Documents.ID_CARDS -> startActivityForResult(Intent(this,IdCardDocumentActivity::class.java),100)
            Documents.CHANGE_MY_DOCS -> {
                val list = dashBoardViewModel.data.value?.data?.policies?.map { it?.policyNumber.toString() }?.toList()?:return
                val arrayList=ArrayList<String>()
                arrayList.addAll(list)
                startActivityForResult(Intent(this,ChangePolicyDetials1Activity::class.java).apply {
                    putStringArrayListExtra("policy",arrayList)
                },100)
            }
            Documents.UPLOAD_DOCS -> startActivityForResult(Intent(this, UploadDocumentActivity::class.java),100)
            Documents.CLAIM_FILE -> startActivityForResult(Intent(this, FileClaimActivity::class.java),100)
            Documents.RENEWALS -> startActivityForResult(Intent(this,PaymentPlanActivity::class.java),100)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        dashBoardViewModel.getDashBoardData()
    }


}
interface OnDashBoardActions{
    fun onTapProfile()
}


