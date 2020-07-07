package com.example.demo.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

/**

@author dengfeijie
@description:   JetPack-WorkManager
@date : 2020/7/7 19:06
 */
class SimpleWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        Log.e("WorkManager-TAG", "do work in simpleWorker")

        return Result.success()
    }
}