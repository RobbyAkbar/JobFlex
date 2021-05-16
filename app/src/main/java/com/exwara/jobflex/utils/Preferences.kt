package com.exwara.jobflex.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by robby on 16/05/21.
 */
class Preferences {

    private lateinit var sharedPreferences: SharedPreferences

    fun hasUserLoggedIn(context: Context): Boolean {
        sharedPreferences = context.getSharedPreferences(
            Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(Config.LOGGED_IN_SHARED_PREF, false)
    }

    fun deletePref(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(Config.USER_SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        editor.commit()
    }

}