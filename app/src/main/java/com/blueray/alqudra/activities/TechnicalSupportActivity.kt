package com.blueray.alqudra.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.util.Log.e
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityTechnicalSupportBinding
import com.blueray.alqudra.fragments.CustomDrawerLayout
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.setUpActivity
import com.blueray.alqudra.helpers.HelpersUtils.showMessage
import com.blueray.alqudra.helpers.ViewUtils.hide
import java.io.File

class TechnicalSupportActivity : BaseActivity() {

    private lateinit var binding : ActivityTechnicalSupportBinding
    private val REQUEST_CODE = 100
    private val IMAGE_REQUEST_CODE = 101
    private var  imageData : String? =null
    private var  imageFile : File? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTechnicalSupportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set Up Activity
        setUpActivity(this)
        HelpersUtils.getName(this) {
            binding.includedTap.name.text = getString(R.string.hi_zaid_omar, it)
        }
        binding.includedTap.title.hide()

        binding.includedTap.back.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()

        binding.attachImageBtn.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2){
                if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_IMAGES ) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    e("ayham","permission denied")
                    requestPermissions(arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),REQUEST_CODE)
                } else {
                    image()
                }
            }else{
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted, request it
                    e("ayham","permission denied")
                    requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),REQUEST_CODE)
                } else {
                    image()
                }}



    }
    }
    private fun image() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    image()
                } else {
                    showRotationalDialogForPermission()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions"
                    + "required for this feature. It can be enable under App settings!!!")

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            IMAGE_REQUEST_CODE->{
                if(data != null){
                    val uri=data.data
                    imageData=getFilePathFromUri(uri)
                    imageFile= File(imageData)
                }else{
                    showMessage(this,"لم يتم اختيار اي صورة")
                }
            }
        }
    }
    private fun getFilePathFromUri(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri!!, projection, null, null, null)

        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            filePath
        } else {
            uri.path ?: ""
        }
    }
}