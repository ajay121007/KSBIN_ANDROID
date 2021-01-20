package com.example.ks.activities

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.renewal.RenewalViewModel
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityRenewalBinding
import com.example.ks.utils.PathUtils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

import gun0912.tedimagepicker.builder.TedImagePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.lang.Throwable
import kotlin.Int
import kotlin.String
import kotlin.apply
import kotlin.arrayOf
import kotlin.getValue
import kotlin.let

class RenewalActivity : BaseActivity() {
    private val DMV_LIC: Int=100
    private val TCL_LIC: Int=101
    private val DDC_LIC: Int=102

    private lateinit var activityRenewalBinding: ActivityRenewalBinding
    private val renewalViewModel:RenewalViewModel by viewModel { parametersOf(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_renewal)
        activityRenewalBinding=DataBindingUtil.setContentView(this, R.layout.activity_renewal)
        activityRenewalBinding.apply {
            setSupportActionBar(toolbar)
            lifecycleOwner=this@RenewalActivity
            executePendingBindings()
            llDdc.setOnClickListener {
                selectDocs(DDC_LIC)
            }
            llDmv.setOnClickListener {
                selectDocs(DMV_LIC)
            }
            llTcl.setOnClickListener {
                selectDocs(TCL_LIC)
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DDC_LIC -> {
                val path = getpath(data)
                if (path != null) {
                    activityRenewalBinding.tvDdc.text = path
                    renewalViewModel.ddc.postValue(path)
                }
            }
            TCL_LIC -> {
                val path = getpath(data)
                if (path != null) {
                    activityRenewalBinding.tvTcl.text = getpath(data)
                    renewalViewModel.tcl.postValue(path)
                }
            }
            DMV_LIC -> {
                val path = getpath(data)
                if (path != null) {
                    activityRenewalBinding.tvDmv.text = getpath(data)
                    renewalViewModel.dmv.postValue(path)
                }
            }
        }



    }

    private fun getpath(data: Intent?): String? {
        return data?.data?.let {
            val uriString: String = it.toString()
            val pathUtils = PathUtils.getPath(this, it)
            val myFile = File(pathUtils)
            val path: String = myFile.getAbsolutePath()
            path
        }
    }


    private fun selectDocs(requestCode: Int){
        val permissions =
            arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
            )
        Permissions.check(
            this /*context*/,
            permissions,
            null /*rationale*/,
            null /*options*/,
            object : PermissionHandler() {
                override fun onGranted() {
                    TedImagePicker.with(this@RenewalActivity)
                        .showCameraTile(true)
                        .start {
                            when (requestCode) {
                                DDC_LIC -> {
                                    val path =it.path
                                    if (path != null) {
                                        activityRenewalBinding.tvDdc.text = path
                                        renewalViewModel.ddc.postValue(path)
                                    }
                                }
                                TCL_LIC -> {
                                    val path =it.path
                                    if (path != null) {
                                        activityRenewalBinding.tvTcl.text = path
                                        renewalViewModel.tcl.postValue(path)
                                    }
                                }
                                DMV_LIC -> {
                                    val path =it.path
                                    if (path != null) {
                                        activityRenewalBinding.tvDmv.text = path
                                        renewalViewModel.dmv.postValue(path)
                                    }
                                }
                            }
                        }



//                    val intent = Intent()
////                    intent.type = "/*/"
//                    intent.type = "*/*"
//                    intent.action = Intent.ACTION_GET_CONTENT
//
//                    startActivityForResult(Intent.createChooser(intent, "Select PDF"), requestCode)
                }
            })

    }

    fun uploadDocs(view: View) {
        renewalViewModel.uploadRenewals()
    }
}