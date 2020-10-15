package com.example.ks.activities.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.ks.R
import com.example.ks.activities.editprofile.EditProfileActivity
import com.example.ks.activities.loginsignup.LoginSignUpActivity
import com.example.ks.api.Constants
import com.example.ks.common.BaseActivity
import com.example.ks.databinding.ActivityProfileBinding
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ProfileActivity : BaseActivity(), Toolbar.OnMenuItemClickListener {
   lateinit var profileBinding: ActivityProfileBinding
    private val profileViewModel:ProfileViewModel by viewModel { parametersOf(this)  }
    private var avatarAnimateStartPointY: Float = 0F
    private var avatarCollapseAnimationChangeWeight: Float = 0F
    private var isCalculated = false
    private var verticalToolbarAvatarMargin =0F
    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var horizontalToolbarAvatarMargin: Float = 0F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EXPAND_AVATAR_SIZE = resources.getDimension(R.dimen.default_expanded_image_size)
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)
        horizontalToolbarAvatarMargin = resources.getDimension(R.dimen.activity_margin)
        profileBinding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        (profileBinding.toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
        profileBinding.viewModel=profileViewModel
        profileBinding.apply {
            rowName.apply{
                viewModel=profileViewModel
                lifecycleOwner=this@ProfileActivity
                executePendingBindings()

            }
            profileLayout.cardSupport.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "${Constants.YOUR_CONTACT}"))
                startActivity(intent)
            }
            toolbar.setOnMenuItemClickListener(this@ProfileActivity)
//            setSupportActionBar(toolbar)
        }
//        profileBinding.appBarLayout.addOnOffsetChangedListener(
////            profileBinding.appBarLayout.OnOffsetChangedListener { appBarLayout, i ->
//
////            }
//        )
        profileBinding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

//            if(verticalOffset>-100){
//                profileBinding.profileImageFloating.visibility=View.VISIBLE
//            }
//            else profileBinding.profileImageFloating.visibility=View.GONE
//            if (isCalculated.not()) {
//                avatarAnimateStartPointY =
//                    Math.abs((appBarLayout.height - (EXPAND_AVATAR_SIZE + horizontalToolbarAvatarMargin)) / appBarLayout.totalScrollRange)
//                avatarCollapseAnimationChangeWeight = 1 / (1 - avatarAnimateStartPointY)
//                verticalToolbarAvatarMargin =
//                    (profileBinding.toolbar.height - COLLAPSE_IMAGE_SIZE) * 2
//                isCalculated = true
//            }
//            /**/
//            updateViews(Math.abs(verticalOffset / appBarLayout.totalScrollRange.toFloat()))
            val range = -appBarLayout.totalScrollRange.toFloat()
            val value = 255 * ((1.0f - verticalOffset.toFloat() / range).toInt())
            Log.i(this.javaClass.simpleName, "value $value: ")
            profileBinding.profileImageFloating.setImageAlpha((255 * (1.0f - verticalOffset.toFloat() / range)).toInt())

        })
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
    private fun updateViews(offset: Float) {
        profileBinding.profileImageFloating.apply {
            when {
                offset > avatarAnimateStartPointY -> {
                    val avatarCollapseAnimateOffset = (offset - avatarAnimateStartPointY) * avatarCollapseAnimationChangeWeight
                    val avatarSize = EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * avatarCollapseAnimateOffset
                    this.layoutParams.also {
                        it.height = Math.round(avatarSize).toInt()
                        it.width = Math.round(avatarSize).toInt()
                    }
//                    invisibleTextViewWorkAround.setTextSize(TypedValue.COMPLEX_UNIT_PX, offset)

                    this.translationX = ((profileBinding.appBarLayout.width - horizontalToolbarAvatarMargin - avatarSize) / 2) * avatarCollapseAnimateOffset
                    this.translationY = ((profileBinding.toolbar.height  - verticalToolbarAvatarMargin - avatarSize ) / 2) * avatarCollapseAnimateOffset
                }
                else -> this.layoutParams.also {
                    if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                        it.height = EXPAND_AVATAR_SIZE.toInt()
                        it.width = EXPAND_AVATAR_SIZE.toInt()
                        this.layoutParams = it
                    }
                    translationX = 0f
                }
            }
        }
    }


}

