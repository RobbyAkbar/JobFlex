package com.exwara.jobflex.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.exwara.jobflex.R
import com.exwara.jobflex.core.ui.ViewModelFactory
import com.exwara.jobflex.core.utils.Preferences
import com.exwara.jobflex.databinding.ActivityProfileBinding
import com.exwara.jobflex.ui.account.AccountActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        binding.tvUploadcv.setOnClickListener {
            val intent = Intent(this, UploadPdfActivity::class.java)
            startActivity(intent)
        }
        binding.tvName.text = Preferences().getUserFullName(this)
        binding.btnLogout.setOnClickListener { logout() }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.txt_profile)
    }

    private fun logout() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.message_logout))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                Preferences().deletePref(this)
                startActivity(Intent(this, AccountActivity::class.java))
                finish()
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialogInterface, _ -> dialogInterface.cancel() }
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}