package com.exwara.jobflex.ui.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.exwara.jobflex.R

class AccountActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.elevation = 0F
    }
}