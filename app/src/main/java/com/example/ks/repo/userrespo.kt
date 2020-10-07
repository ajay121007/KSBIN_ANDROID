package com.example.ks.repo

import com.example.ks.api.ApiService
import com.example.ks.models.DashBoardResponse
import com.example.ks.utils.ResultWrapper
import com.example.ks.utils.safeApiCall

/**
 * Created by skycap.
 */
class UserRepo(private val apiService: ApiService){
    suspend fun getDashBoardData():ResultWrapper<DashBoardResponse?>{
        return safeApiCall { apiService.getDashboardData() }
    }
}