package com.exwara.jobflex.core.utils

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.exwara.jobflex.core.data.source.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by robby on 31/05/21.
 */
class MyApplication : MultiDexApplication(), LifeCycleDelegate {

    companion object {
        var currentUser: UserEntity? = null
    }

    override fun onCreate() {
        super.onCreate()
        val lifeCycleHandler = AppLifecycleHandler(this)
        registerLifecycleHandler(lifeCycleHandler)
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }

    override fun onAppBackgrounded() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null && currentUser != null) {
            currentUser!!.active = false
            FirestoreUtil.updateUser(currentUser!!) {

            }
        }
        Log.d("Awww", "App in background")
    }

    override fun onAppForegrounded() {
        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null && currentUser != null) {
            currentUser!!.active = true
            FirestoreUtil.updateUser(currentUser!!) {
            }
        }

        Log.d("Yeeey", "App in foreground")
    }

    private fun registerLifecycleHandler(lifeCycleHandler: AppLifecycleHandler) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }
}