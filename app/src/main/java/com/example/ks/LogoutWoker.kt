package com.example.ks

import android.content.Context
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.ks.repo.AuthRepo
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * Created by skycap.
 */
class LogoutWorker (val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params),KoinComponent{
    private val authRepo:AuthRepo by inject()
    override suspend fun doWork(): Result {
//       sendNotification("stated","started",context)
        authRepo.logOut()
        return Result.success()
    }

}