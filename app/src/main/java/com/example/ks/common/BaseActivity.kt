package com.example.ks.common

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Environment
import android.os.PersistableBundle
import android.view.MenuItem
import android.view.View
import android.webkit.DownloadListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.example.ks.activities.profile.ProfileViewModel
import com.example.ks.sharedpref.SharedPreferenceHelper
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import ir.siaray.downloadmanagerplus.classes.Downloader
import ir.siaray.downloadmanagerplus.enums.DownloadReason
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import kotlin.random.Random


/**
 * Created by skycap.
 */
open class BaseActivity :AppCompatActivity(),UICallBacks {
    private var timer: CountDownTimer?=null
    var  dialogView: View?=null
     var   dialog:BottomSheetDialog?=null
     val baseViewModel: ProfileViewModel by viewModel { parametersOf(this) }
    val pref:SharedPreferenceHelper by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseViewModel.logout.observe(this, Observer {
            if (it) {
                val i = Intent(this, LoginSignUpActivity::class.java)
// set the new task and clear flags
// set the new task and clear flags
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            }
        })
    }
    override fun onToast(message: String?) {
        runOnUiThread { Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
        }
    }

    override fun onLoading(loading: Boolean) {
        showLoading(loading)
    }

    override fun showDialog(message: String?) {
        showAlertDialog(message)
    }

    override fun showDialogDownload(message: String?, url: String?) {
        runOnUiThread {

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Confirmation")
            builder.setMessage(message)

            // add a button
            builder.setNegativeButton(
                "OK"
            ) { p0, p1 -> p0.dismiss()
//                finish()
            }
            builder.setPositiveButton(
                "Download Invoice"
            ) { p0, p1 -> p0.dismiss()

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

                            val downloader = Downloader.getInstance(this@BaseActivity)
                                .setUrl(url?.replace("dev.",""))
//                                .setUrl("https://en.unesco.org/inclusivepolicylab/sites/default/files/dummy-pdf_2.pdf")
                                .setAllowedOverRoaming(true)
                                .setAllowedOverMetered(true) //Api 16 and higher
                                .setVisibleInDownloadsUi(true)
                                .setDestinationDir(
                                    Environment.getDataDirectory().absolutePath,
                                    "invoice.pdf"
                                )
                                .setNotificationTitle("Downloading File")
                                .setDescription("DownLoading file")
                                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            downloader.start()
                            downloader.showProgress()
                        }
                    })



//                finish()
//                var updatedUrl="https://docs.google.com/viewerng/viewer?url=$url";
//                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(updatedUrl.replace("dev.","")))
//                startActivity(browserIntent)
            }

            // create and show the alert dialog
            runOnUiThread {  val dialog: AlertDialog = builder.create()
                dialog.show() }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else ->false
        }
    }

    private  fun showLoading(loading: Boolean){
        if(loading){
            runOnUiThread {
                dialogView=layoutInflater.inflate(R.layout.progress_layout, null)
                dialog= BottomSheetDialog(this)
                dialog?.setContentView(dialogView ?: return@runOnUiThread)
//                dialog.setCancelable(false)
                dialogView?.minimumHeight=600
                dialog?.show()
            }
        }
        else dialog?.dismiss()
    }
    open fun showAlertDialog(message: String?) {

        // setup the alert builder
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")
        builder.setMessage(message)

        // add a button9
        builder.setPositiveButton(
            "OK"
        ) { p0, p1 -> p0.dismiss()
        finish()
        }

        // create and show the alert dialog
       runOnUiThread {  val dialog: AlertDialog = builder.create()
           dialog.show() }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {

        timer?.cancel()
        super.onDestroy()
    }

}


