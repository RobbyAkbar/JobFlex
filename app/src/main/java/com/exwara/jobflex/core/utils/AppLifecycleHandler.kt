package com.exwara.jobflex.core.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.os.Bundle

/**
 * Created by robby on 31/05/21.
 */
class AppLifecycleHandler(private val lifeCycleDelegate: LifeCycleDelegate) :
    Application.ActivityLifecycleCallbacks,
    ComponentCallbacks2 {

    private var appInForeground = false

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {
        if (!appInForeground) {
            appInForeground = true
            lifeCycleDelegate.onAppForegrounded()
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onConfigurationChanged(newConfig: Configuration) {}

    override fun onLowMemory() {}

    override fun onTrimMemory(level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            appInForeground = false
            lifeCycleDelegate.onAppBackgrounded()
        }
    }

}

interface LifeCycleDelegate {
    fun onAppBackgrounded()
    fun onAppForegrounded()
}
