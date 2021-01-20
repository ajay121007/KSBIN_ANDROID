package com.example.ks.activities.upload

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.ks.R
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityUploadDocumentBinding
import com.example.ks.utils.PathUtils
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
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
        binding.apply {
            setSupportActionBar(toolbar)
        }
        binding.executePendingBindings()
        initView()


    }

    private fun initView() {
       // viewModel.getContractList()
        clickEvent()
    }

    private fun clickEvent() {

        binding.uploadDocumentBtn.setOnClickListener {
            if (filePath.isNotEmpty()){
                viewModel.uploadDocs(filePath, filename)
            }
        }

        binding.layoutUpload.setOnClickListener {

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

                        selectDocs()
                    }
                })
        }
    }

    fun selectDocs(){
        val intent = Intent(this, FilePickerActivity::class.java)
        intent.putExtra(
            FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .enableImageCapture(true)
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .build()
        )
        startActivityForResult(intent, PDF_PICKER_RESULTS)

//        val intent = Intent()
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//
//        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PDF_PICKER_RESULTS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==PDF_PICKER_RESULTS) {

               val files=data?.getParcelableArrayExtra(FilePickerActivity.MEDIA_FILES)
//              val pathUtils= PathUtils.getPath(this, it)
//               val myFile = File(pathUtils)
            when (files?.size) {
                0 -> {

                }
                else -> {
                    val mediaFile: MediaFile? = data?.getParcelableArrayListExtra<MediaFile>(FilePickerActivity.MEDIA_FILES)
                        ?.get(0)

                    binding.txtFileName.text =mediaFile?.name
                    filePath = mediaFile?.path.toString()
                    filename= mediaFile?.name.toString()
                }
            }




//               Log.e("path ", path + "=>" + myFile.name)


        }
    }
}