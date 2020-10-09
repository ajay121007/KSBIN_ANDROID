package com.example.ks.constants

import com.example.ks.models.DashBoardResponse
import com.example.ks.models.ProfileResponse

/**
 * Created by skycap.
 */
object UserConstants {
    var signWebUrl: String=""
    var signToken: String=""
    lateinit var policyArrayList: ArrayList<DashBoardResponse.Data.Policy>
    var userProfile:ProfileResponse?=null
}