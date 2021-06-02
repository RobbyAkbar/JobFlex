package com.exwara.jobflex.ui.profile

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.ui.ViewModelFactory
import com.exwara.jobflex.core.utils.FileUtils
import com.exwara.jobflex.core.utils.Preferences
import com.exwara.jobflex.databinding.ActivityUploadPdfBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*
import java.nio.file.Files

class UploadPdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadPdfBinding
    private lateinit var uploadPdfViewModel: UploadPdfViewModel

    companion object {
        private const val REQUEST_EXTERNAL_STORAGE = 1
        private val PERMISSIONS_STORAGE = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadPdfBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        uploadPdfViewModel = ViewModelProvider(this, factory)[UploadPdfViewModel::class.java]

        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                this,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }

        binding.btnPdf.setOnClickListener{
            val docIntent = Intent()
            docIntent.action = Intent.ACTION_OPEN_DOCUMENT

            // We will be redirected to choose pdf
            docIntent.type = "application/pdf"
            startActivityForResult(docIntent, 1)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK && data !== null){
            val uri: Uri? = data.data

            val path: String? = uri?.let { FileUtils(this).getPath(it) }

            val cursor: Cursor? =
                uri?.let { contentResolver.query(it, null, null, null, null) }
            cursor?.moveToFirst()

            val columnIndex: Int? = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val pdfName: String? = columnIndex?.let { cursor.getString(it) }
            binding.tvPdfname.text = pdfName

            cursor?.close()

            if (path != null) {
                uploadPdf(path)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.

                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

    private fun uploadPdf(path: String){
        //Create a file object using file path
        val file = File(path)
        // Parsing any Media type file
        var requestBody: RequestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestBody = Files.readAllBytes(file.toPath())
                .toRequestBody("application/octet-stream".toMediaTypeOrNull(), 0,
                    file.length().toInt()
                )
        }

        val fileToUpload: MultipartBody.Part = MultipartBody.Part.createFormData("filename", file.name, requestBody)
        val userId = Preferences().getUserId(this)

        uploadPdfViewModel.uploadPdf(fileToUpload, requestBody, "$userId.pdf").observe(this,
            {res->
                if (res != null) {
                    when (res) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}