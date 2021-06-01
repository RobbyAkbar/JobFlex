package com.exwara.jobflex.core.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AlertDialog
import com.exwara.jobflex.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by robby on 16/05/21.
 */
class AppUtils {

    fun hideSystemUIAndNavigation(activity: Activity) {
        val decorView: View = activity.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.setDecorFitsSystemWindows(false)
            activity.window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }

    fun formatDate(dateString: String): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("en", "US"))
        val newFmt = SimpleDateFormat("MMM dd, yyyy", Locale("en", "US"))
        val date: Date = fmt.parse(dateString) ?: Date()
        return newFmt.format(date)
    }

    companion object {
        private lateinit var builder: AlertDialog.Builder
        private lateinit var dialog: AlertDialog

        fun sendSampleDialog(context: Context, message: String) {
            builder = AlertDialog.Builder(context)
            builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok) { _, _ ->
                    dialog.dismiss()
                }
            dialog = builder.create()
            dialog.show()
        }
    }

}