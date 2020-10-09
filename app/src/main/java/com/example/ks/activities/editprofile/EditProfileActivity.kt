package com.example.ks.activities.editprofile

import android.Manifest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ks.R
import com.example.ks.activities.claim.FileClaimViewModel
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivityEditProfileBinding
import com.example.ks.databinding.ActivityFileClaimBinding
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import gun0912.tedbottompicker.TedBottomPicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File

class EditProfileActivity : BaseActivity() {
    lateinit var binding: ActivityEditProfileBinding
    val viewModel: EditProfileViewModel by viewModel { parametersOf(this) }
    var filePath=""
    var fileName=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        initView()

    }
    private fun initView() {
        updateProfileView()
        clickEvent()
    }

    private fun updateProfileView() {
        UserConstants.userProfile?.let {
            it.data?.user?.let {
               binding.editUserName.setText(it.name)
               binding.editProfileEmailEditText.setText(it.email)
               binding.editPhone.setText(it.mobileNumber?:"")

                var imageUrl = ""
                if (!it.userImageUrl.isNullOrEmpty()){
                    imageUrl = it.userImageUrl
                    filePath= imageUrl
                    fileName= it.userImage
                }
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.user_defult)
                requestOptions.error(R.drawable.user_defult)
                Glide.with(binding.userProfile.context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(imageUrl).into(binding.userProfile)
           }
        }
    }

    private fun clickEvent() {
        binding.userProfile.setOnClickListener {

            val permissions =
                arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
            Permissions.check(
                this /*context*/,
                permissions,
                null /*rationale*/,
                null /*options*/,
                object : PermissionHandler() {
                    override fun onGranted() {
                        TedBottomPicker.with(this@EditProfileActivity)
                            .show {
                                val uriString: String = it.toString()
                                val myFile = File(uriString)
                                val path: String = myFile.getAbsolutePath()
                                val bitmap =
                                    MediaStore.Images.Media.getBitmap(getContentResolver(), it)
                                binding.userProfile.setImageBitmap(bitmap)
                                filePath= it.toString()
                                fileName= myFile.name

                            }
                    }
                })

        }

        binding.updateButton.setOnClickListener {

            if (binding.editUserName.text.toString().isNotEmpty() &&binding.editProfileEmailEditText.text.toString().isNotEmpty()
                && binding.editPhone.text.toString().isNotEmpty() && binding.editProfileEmailEditText.text.toString().isNotEmpty()){

                viewModel.updateProfile(filePath,fileName,binding.editUserName.text.toString(), binding.editPhone.text.toString(),
                binding.editProfileEmailEditText.text.toString())


            }
            else{
                Toast.makeText(this, "Please fill all the field", Toast.LENGTH_LONG).show()
            }

        }

        binding.backImage.setOnClickListener {
            finish()
        }

    }

}