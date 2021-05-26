package com.exwara.jobflex.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exwara.jobflex.databinding.ActivityProfileBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPdf.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT

            // We will be redirected to choose pdf
            galleryIntent.type = "application/pdf"
            // resultLauncher.launch(intent)
            startActivityForResult(galleryIntent, 1)
        }
    }

//    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            // There are no request codes
//            val data: Intent? = result.data
//            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK && data !== null){
            binding.textView2.text = data.data.toString().substring(data.data.toString().lastIndexOf("/")+1)
            Toast.makeText(this, data.data.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun uploadFile(fileUri: Uri) {
        // create upload service client
        val service: FileUploadService =
            ServiceGenerator.createService(FileUploadService::class.java)

        // https://github.com/iPaulPro/aFileChooser/blob/master/aFileChooser/src/com/ipaulpro/afilechooser/utils/FileUtils.java
        // use the FileUtils to get the actual file by uri
        val file: File = FileUtils.getFile(this, fileUri)

        // create RequestBody instance from file
        val requestFile: RequestBody = create(
            MediaType.parse(contentResolver.getType(fileUri)),
            file
        )

        // MultipartBody.Part is used to send also the actual file name
        val body: Part = createFormData.createFormData("picture", file.name, requestFile)

        // add another part within the multipart request
        val descriptionString = "hello, this is description speaking"
        val description = RequestBody.create(
            MultipartBody.FORM, descriptionString
        )

        // finally, execute the request
    }*/
}