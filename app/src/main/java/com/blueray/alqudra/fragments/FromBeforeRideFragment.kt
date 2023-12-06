package com.blueray.alqudra.fragments

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.activities.MainActivity
import com.blueray.alqudra.api.inProgressRides.Data
import com.blueray.alqudra.databinding.ActivityMyProfileBinding
import com.blueray.alqudra.databinding.FragmentFromBeforeRideBinding
import com.blueray.alqudra.helpers.HelpersUtils
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_STATUS
import com.blueray.alqudra.helpers.HelpersUtils.SELECTED_TRIP_TYPE
import com.blueray.alqudra.helpers.HelpersUtils.showMessage
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel
import com.blueray.alqudra.viewModels.FromBeforeRideViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import java.io.File







class FromBeforeRideActivity : AppCompatActivity() {
    private var fullType = "Full" // Default value
    private val defaultButtonColor = Color.GRAY
    private val selectedButtonColor = Color.RED
    private val defaultTextColor = Color.BLACK
    private val selectedTextColor = Color.WHITE
    val viewModel by viewModels<AppViewModel>()
    private val REQUEST_CODE = 100
    private lateinit var imageData: String
    private var imageFile: File? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 2
    private var latLocation: String? = ""
    private var longLocation: String? = ""
    private val IMAGE_REQUEST_CODE = 4
    private lateinit var binding: FragmentFromBeforeRideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentFromBeforeRideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tripData = intent?.getStringExtra("orderId") as? String

        binding.includedTap.back.setOnClickListener {
            onBackPressed()
        }
        HelpersUtils.getName(this) {
            binding.includedTap.name.text = getString(R.string.hi_zaid_omar, it)
        }
        binding.includedTap.notifications.hide()
        binding.includedTap.menu.hide()
        binding.includedTap.title.text = SELECTED_TRIP_STATUS

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext!!)

        binding.fullBtn.setOnClickListener {
            selectButton(binding.fullBtn, "Full")
        }

        binding.mediumBtn.setOnClickListener {
            selectButton(binding.mediumBtn, "Medium")
        }

        binding.lowBtn.setOnClickListener {
            selectButton(binding.lowBtn, "Low")
        }


        listOf(binding.fullBtn, binding.mediumBtn, binding.lowBtn).forEach { button ->
            button.setBackgroundColor(defaultButtonColor)
            button.setTextColor(defaultTextColor)
        }


        selectButton(binding.fullBtn, fullType)


        val groubTpe = intent?.getStringExtra("")




        binding.uploadPhotoBtn.setOnClickListener {
//            image_Video=true
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted, request it
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                        REQUEST_CODE
                    )
                } else {
                    image()
                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        this,
                        READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted, request it
                    requestPermissions(arrayOf(READ_EXTERNAL_STORAGE), REQUEST_CODE)
                } else {
                    image()
                }
            }
            isLocationPermissionGranted()

        }

        getData()
        if (imageFile == null && binding.KmEditText.editText?.text?.isEmpty() == true) {
            binding.sendBtn.setBackgroundColor(Color.GRAY)
        } else {
            binding.sendBtn.setBackgroundColor(Color.RED)
            binding.sendBtn.setTextColor(Color.WHITE)

        }




        binding.sendBtn.setOnClickListener {
            imageFile?.let { it1 ->

                if (imageFile == null && binding.KmEditText.editText?.text?.isEmpty() == true) {

                    showMessage(this, "please Add ")

                } else {
                    if (latLocation.isNullOrEmpty()){
                        showMessage(this,"Please Turn on Location Services")
                    }else{
                        viewModel.updateTrip(
                            tripData.toString(),
                            HelpersUtils.SELECTED_TRIP_TYPE_ID.toString(),
                            "1",
                            binding.KmEditText.editText?.text.toString(),
                            fullType,
                            it1, latLocation.toString(), longLocation.toString()
                        )
                    }


                }


            }
        }

    }


    private fun image() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    // Select fullBtn by default


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST_CODE -> {
                val uri = data?.data
                imageData = getFilePathFromUri(uri)
                imageFile = File(imageData)
                binding.uploadPhotoBtn.text = "تم اختيار صورة"


                binding.selectedImage.setImageURI(uri)
            }

        }
    }

    private fun getData() {
        viewModel.getUpdateTrip().observe(this) {

            when (it) {
                is NetworkResults.Success -> {

                    if (it.data.msg.status == 200) {
                        showMessage(this, it.data.msg.message.toString())
//                        navController.popBackStack()
                        onBackPressed()

                    } else {
                        showMessage(this, it.data.msg.message.toString())

                    }

                }

                is NetworkResults.Error -> {
                    Log.e("ayham", it.exception.toString())
                }

                else -> {}
            }
        }
    }


    private fun getFilePathFromUri(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver?.query(uri!!, projection, null, null, null)

        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            filePath
        } else {
            uri?.path ?: ""
        }
    }

    private fun selectButton(selectedButton: MaterialButton, type: String) {
        listOf(binding.fullBtn, binding.mediumBtn, binding.lowBtn).forEach { button ->
            button.run {
                setBackgroundColor(defaultButtonColor)
                setTextColor(defaultTextColor)
            }
        }

        // Set selected button style
        selectedButton.run {
            setBackgroundColor(selectedButtonColor)
            setTextColor(selectedTextColor)
        }

        // Set selected type
        fullType = type
    }


    //    permtion location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    permetion of Location

    private fun isLocationPermissionGranted(): Boolean {
        return if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            false
        } else {


            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    Log.d("Locatiooon!", location.toString())
                    Log.d("Locatiooon!!", location?.latitude.toString())
                    Log.d("Locatiooon!!!", location?.longitude.toString())

                    if (location?.longitude.toString() != "" && location?.latitude.toString() != "") {

                        Log.d("Locatiooon!", location.toString())
                        Log.d("Locatiooon!!", location?.latitude.toString())
                        Log.d("Locatiooon!!!", location?.longitude.toString())
                        latLocation = location?.latitude.toString()
                        longLocation = location?.longitude.toString()

                    }


                }


            true
        }
    }
}






