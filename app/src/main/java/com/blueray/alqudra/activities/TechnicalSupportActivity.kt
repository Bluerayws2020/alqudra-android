package com.blueray.alqudra.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
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
    private val CALL_PHONE_REQUEST_CODE = 102
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

        binding.whatsApp.setOnClickListener {
            openWhatsAppChat(this,"+971544503503")
        }
        binding.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:+971544503503"))
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Permission is granted, make the call
                startActivity(intent)
            } else {
                // Permission is not granted, request it
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    CALL_PHONE_REQUEST_CODE
                )
            }
        }
        binding.email.setOnClickListener {
            openEmail(this,"info@alqudrahcars.com")
        }
        binding.instagram.setOnClickListener {
            openInstagramPage(this)
        }


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
            IMAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    image()
                } else {
                    showRotationalDialogForPermission()
                }
            }
            CALL_PHONE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    binding.call.performClick()
                } else {
                    showRotationalDialogForPermission()
                }
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
                    showMessage(this,"تم اختيار صورة")
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

    // whats app
    private fun openWhatsAppChat(context: Context, phoneNumber: String) {
        val packageName = "com.whatsapp" // Package name of the WhatsApp app
        val whatsappIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber"))

        try {
            // Check if the WhatsApp app is installed
            context.packageManager.getPackageInfo(packageName, 0)

            // WhatsApp app is present, open the chat in it
            context.startActivity(whatsappIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            // WhatsApp is not installed, you may want to handle this case accordingly
            // For example, show a toast message indicating that WhatsApp is not installed
            Toast.makeText(context, "WhatsApp is not installed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openEmail(context: Context, emailAddress: String, subject: String = "", body: String = "") {
        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:$emailAddress")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        emailIntent.putExtra(Intent.EXTRA_TEXT, body)

        try {
            context.startActivity(emailIntent)
        } catch (e: ActivityNotFoundException) {
            // Email app is not installed, handle this case accordingly
            // For example, show a toast message indicating that no email app is installed
            Toast.makeText(context, "No email app installed", Toast.LENGTH_SHORT).show()
        }
    }
    private fun openInstagramPage(context: Context) {
        val packageName = "com.instagram.android" // Package name of the Instagram app
        val instagramUrl = "https://www.instagram.com/alqudrahrentals/" // URL of the Instagram profile

        val instagramIntent = Intent(Intent.ACTION_VIEW)
        instagramIntent.data = Uri.parse("https://www.instagram.com/alqudrahrentals/")
        instagramIntent.setPackage(packageName)

        val defaultBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(instagramUrl))

        try {
            // Check if the Instagram app is installed
            if (isPackageInstalled(context, packageName)) {
                // Instagram app is present, open the Instagram profile in it
                context.startActivity(instagramIntent)
            } else {
                // Instagram app is not installed, open the Instagram profile in the default browser
                context.startActivity(defaultBrowserIntent)
            }
        } catch (e: Exception) {
            // Handle exceptions, such as NameNotFoundException or ActivityNotFoundException
            e.printStackTrace()
        }
    }
    private fun openFacebookPage(context: Context) {
        val packageName = "com.facebook.katana" // Package name of the Facebook app
        val facebookPageUrl = "https://www.facebook.com/alqudrahcars" // URL of the Facebook page

        val facebookIntent = Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=$facebookPageUrl"))

        val chromePackageName = "com.android.chrome" // Package name of Chrome browser
        val chromeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl))
        chromeIntent.setPackage(chromePackageName)

        val defaultBrowserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl))

        try {
            // Check if the Facebook app is installed
            context.packageManager.getPackageInfo(packageName, 0)

            // Facebook app is present, open the Facebook page in it
            context.startActivity(facebookIntent)
        } catch (e: PackageManager.NameNotFoundException) {
            try {
                // Check if Chrome browser is installed
                context.packageManager.getPackageInfo(chromePackageName, 0)

                // Chrome is installed, open the Facebook page in Chrome
                context.startActivity(chromeIntent)
            } catch (e: PackageManager.NameNotFoundException) {
                // Chrome is not installed, open the Facebook page in the default browser
                context.startActivity(defaultBrowserIntent)
            }
        }
    }

    private fun isPackageInstalled(context: Context, packageName: String): Boolean {
        return try {
            context.packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}