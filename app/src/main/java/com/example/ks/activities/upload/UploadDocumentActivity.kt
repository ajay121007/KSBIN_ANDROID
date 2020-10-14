package com.example.ks.activities.upload
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.activities.signabledocument.SignableDocumentModel
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityUploadDocumentBinding
import com.example.ks.utils.PathUtils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gun0912.tedbottompicker.TedRxBottomPicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File



class UploadDocumentActivity : BaseActivity() {
    val viewModel: UploadViewModel by viewModel { parametersOf(this) }
    val PDF_PICKER_RESULTS=104
    lateinit var binding: ActivityUploadDocumentBinding
    var filePath:String=""
    var filename:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upload_document)
        initView()


    }

    private fun initView() {
       // viewModel.getContractList()
        clickEvent()
    }

    private fun clickEvent() {

        binding.uploadDocumentBtn.setOnClickListener {
            if (filePath.isNotEmpty()){
                viewModel.uploadDocs(filePath,filename)
            }
        }

        binding.layoutUpload.setOnClickListener {

            val permissions =
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            Permissions.check(
                this /*context*/,
                permissions,
                null /*rationale*/,
                null /*options*/,
                object : PermissionHandler() {
                    override fun onGranted() {

                        selectDocs()
                    }
                })
        }
    }

    fun selectDocs(){
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF_PICKER_RESULTS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PDF_PICKER_RESULTS) {
           data?.data?.let {
               val uriString: String = it.toString()
              val pathUtils= PathUtils.getPath(this,it)
               val myFile = File(pathUtils)
               val path: String = myFile.getAbsolutePath()

               binding.txtFileName.text =myFile.path
               filePath = myFile.absolutePath
               filename= myFile.name




               Log.e("path ", path+"=>"+myFile.name)
           }

        }
    }
}