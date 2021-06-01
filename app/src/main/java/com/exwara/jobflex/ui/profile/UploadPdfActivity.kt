package com.exwara.jobflex.ui.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.exwara.jobflex.core.data.Resource
import com.exwara.jobflex.core.ui.ViewModelFactory
import com.exwara.jobflex.core.utils.Preferences
import com.exwara.jobflex.databinding.ActivityUploadPdfBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.nio.file.Files

class UploadPdfActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadPdfBinding
    private lateinit var uploadPdfViewModel: UploadPdfViewModel

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private val BUFFER_SIZE = 1024 * 2

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

            var path: String?
            path = getFilePathFromURI(applicationContext, uri)
            if (path != null) {
                if (path.contains("msf:")) path = FilePath.getPath(applicationContext, uri)
            }

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
        val requestBody: RequestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.readAllBytes(file.toPath())
        }*/

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

    fun getFilePathFromURI(context: Context, contentUri: Uri?): String? {
        //copy file and send new file path
        val fileName: String? = getFileName(contentUri)
        val wallpaperDirectory = File(
            Environment.getExternalStorageState(), "file"
        )
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(wallpaperDirectory.toString() + File.separator + fileName)
            // create folder if not exists
            copy(context, contentUri, copyFile)
            return copyFile.absolutePath
        }
        return null
    }

    private fun getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path = uri.path
        val cut = path!!.lastIndexOf('/')
        if (cut != -1) {
            fileName = path.substring(cut + 1)
        }
        return fileName
    }

    fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream = context.contentResolver.openInputStream(srcUri!!)
                ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copystream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class, IOException::class)
    fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.message, java.lang.String.valueOf(e))
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Log.e(e.message, java.lang.String.valueOf(e))
            }
        }
        return count
    }
}