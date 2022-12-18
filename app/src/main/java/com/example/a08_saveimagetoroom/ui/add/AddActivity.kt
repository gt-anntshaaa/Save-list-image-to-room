package com.example.a08_saveimagetoroom.ui.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.a08_saveimagetoroom.R
import com.example.a08_saveimagetoroom.data.local.entity.User
import com.example.a08_saveimagetoroom.databinding.ActivityAddBinding
import com.example.a08_saveimagetoroom.ui.main.UserApplication
import com.example.a08_saveimagetoroom.ui.main.UserViewModel
import com.example.a08_saveimagetoroom.ui.main.UserViewModelFactory

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var path: String? = null

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).userRepo)
    }

    private val requestPermissionHandler = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
        if (isGranted){
            pickImage.launch("image/*")
        }else{
            Log.e("AddActivity::class.java", "requestPermission: izin ditolak", )
        }
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        // Gambar berhasil dipilih, kemudian dapatkan file path dan tampilkan gambar ke image preview
        it.let {
            path = getRealPathFromURI(it)
            binding.inputImage.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupImageClick()
        setupSubmitBtn()
    }

    private fun setupImageClick(){
        binding.inputImage.setOnClickListener {
            requestPermissionHandler.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun setupSubmitBtn(){
        binding.submitBtn.setOnClickListener {
            val name: String = binding.inputName.text.toString()

            try{
                val pathFile: String = path!!

                // save data to db room
                val user: User = User(name, pathFile)
                userViewModel.insert(user)

                val intent = Intent().putExtra("data_user", user)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }catch (e: Exception){
                e.stackTrace
            }

        }
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        if (uri == null) return null

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            return filePath
        }

        return uri.path
    }
}