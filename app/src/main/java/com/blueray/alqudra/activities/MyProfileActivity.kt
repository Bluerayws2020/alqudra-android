package com.blueray.alqudra.activities

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import  android.content.ContextWrapper.*
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.util.Log.e
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityMyProfileBinding
import com.blueray.alqudra.fragments.BaseFragment
import com.blueray.alqudra.helpers.HelpersUtils.getName
import com.blueray.alqudra.helpers.HelpersUtils.setName
import com.blueray.alqudra.helpers.HelpersUtils.showMessage
import com.blueray.alqudra.helpers.ViewUtils.hide
import com.blueray.alqudra.helpers.ViewUtils.isInputEmpty
import com.blueray.alqudra.model.NetworkResults
import com.blueray.alqudra.viewModels.AppViewModel
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MyProfileActivity : BaseFragment<ActivityMyProfileBinding, AppViewModel>() {

    override val viewModel: AppViewModel by viewModels()
    private val REQUEST_CODE = 100
    private val IMAGE_REQUEST_CODE = 101
    private var imageData: String? = null
    private lateinit var imageFile: File
    private var userPhoto: File? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ActivityMyProfileBinding {
        val binding: ActivityMyProfileBinding = ActivityMyProfileBinding.inflate(layoutInflater)
        return binding
    }

    private var dateOfBirth = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add the name of the user
        getName(requireContext()) {
            binding.includedTap.titleName.text = getString(R.string.hi_zaid_omar, it)
        }

        binding.includedTap.notifications.setOnClickListener {
            findNavController().navigate(R.id.notifications)
        }
        binding.includedTap.menu.setOnClickListener {
            (activity as MainActivity).openDrawer()
        }

        binding.uploadPhotoBtn.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S_V2) {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.READ_MEDIA_IMAGES
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted, request it
                    Log.e("ayham", "permission denied")
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),
                        REQUEST_CODE
                    )
                } else {
                    image()

                }
            } else {
                if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Permission is not granted, request it
                    Log.e("ayham", "permission denied")
                    requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_CODE
                    )
                } else {
                    image()
                }
            }


        }

        // call API
        viewModel.retrieveProfileById()

        // call observers
        getUpdatedData()
        getUserData()


        // on save click
        binding.SaveBtn.setOnClickListener {
            validateData()
        }

        // on datePicker click
        binding.birthDatePicker.setOnClickListener {
//            val calendar = Calendar.getInstance()
//
//            val year = calendar.get(Calendar.YEAR)
//            val month = calendar.get(Calendar.MONTH)
//            val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//            val datePickerDialog = DatePickerDialog(
//                requireContext(),
//                { view, year, monthOfYear, dayOfMonth ->
//                    val formattedDate = String.format(
//                        Locale.ENGLISH,
//                        "%1\$d-%2\$d-%3\$d",
//                        dayOfMonth,
//                        (monthOfYear + 1),
//                        year
//                    )
//                    binding.birthDatePicker.setText(formattedDate)
//                },
//                year,
//                month,
//                day
//            )
//            datePickerDialog.show()
        }


    }

    private fun validateData() {
        if (binding.fullNameEt.isInputEmpty()) {
            showMessage(requireContext(), getString(R.string.fullNameError))
            binding.fullNameEt.requestFocus()
            binding.fullNameEt.error = getString(R.string.fullNameError)
            return
        }

        if (binding.mobileNumberET.isInputEmpty()) {
            showMessage(requireContext(), getString(R.string.mobile_number_error))
            binding.mobileNumberET.requestFocus()
            binding.mobileNumberET.error = getString(R.string.mobile_number_error)
            return
        }

        if (binding.emailEt.isInputEmpty()) {
            showMessage(requireContext(), getString(R.string.email_error))
            binding.emailEt.requestFocus()
            binding.emailEt.error = getString(R.string.email_error)
            return
        }

        // Split the input string by space
        val parts = binding.fullNameEt.text.toString().trim().split(" ")

        // Extract the first name (everything except the last word)
        val firstName = parts.dropLast(1).joinToString(" ")

        // Extract the last name (the last word)
        val lastName = parts.last()
        val date = binding.birthDatePicker.text.toString()
        val phone = binding.mobileNumberET.text.toString()
        val email = binding.emailEt.text.toString()

        // call API
        viewModel.retrieveUpdateProfile(firstName, lastName,date,phone,email, imageFile)


    }

    // observe to live data
    private fun getUserData() {

        viewModel.getProfileById().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.msg.status == 200) {
                        val data = result.data.data
                        Glide.with(requireContext()).load(data.img).placeholder(R.drawable.profile_dummy_img).into(binding.profileImage)
                        binding.userNameTv.text = data.name
                        binding.fullNameEt.setText(data.name)
                        binding.emailEt.setText(data.email)
                        binding.mobileNumberET.setText(data.Phone)
                        binding.birthDatePicker.setText(data.dob)

                        // todo id to be discussed
                        binding.iDTextView.hide()

                    } else {
                        showMessage(
                            requireContext(),
                            result.data.msg.message ?: getString(R.string.error)
                        )
                    }
                }

                is NetworkResults.Error -> {
                    result.exception.printStackTrace()
                    showMessage(requireContext(), getString(R.string.error))
                }
            }
        }
    }

    private fun image() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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


        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "It looks like you have turned off permissions"
                        + "required for this feature. It can be enable under App settings!!!"
            )

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", activity?.packageName, null)
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
        when (requestCode) {
            IMAGE_REQUEST_CODE -> {
                if (data != null) {
                    val uri = data.data
                    imageData = getFilePathFromUri(uri)
                    imageFile = File(imageData)
                    showMessage(requireContext(), "تم اختيار صورة")
                    binding.profileImage.setImageURI(uri)
                    userPhoto = saveImageToFile(binding.profileImage)
                    Log.w("ASDFFDSEEEE", userPhoto.toString())
                } else {
                    showMessage(requireContext(), "لم يتم اختيار اي صورة")
                }
            }
        }
    }

    private fun saveImageToFile(imageView: ImageView): File? {
        val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap

        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return try {
            // Create a file to save the image
            val imageFile = File.createTempFile(
                "JPEG_${timeStamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )

            // Write the bitmap to the file using a FileOutputStream
            FileOutputStream(imageFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }

            // Return the created image file
            imageFile
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun getFilePathFromUri(uri: Uri?): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = activity?.contentResolver?.query(uri!!, projection, null, null, null)

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

    // observe to live data
    private fun getUpdatedData() {

        viewModel.getUpdateProfile().observe(this) { result ->
            when (result) {
                is NetworkResults.Success -> {
                    if (result.data.msg.status == 200) {
                        showMessage(requireContext(), result.data.msg.message)
                        val data = result.data.data[0]
                        Glide.with(requireContext()).load(data.img).placeholder(R.drawable.ic_launcher_foreground).into(binding.profileImage)
                        binding.userNameTv.text = data.name
                        binding.fullNameEt.setText(data.name)
                        binding.emailEt.setText(data.email)
                        binding.mobileNumberET.setText(data.Phone)
                        binding.birthDatePicker.setText(data.dob)
                        binding.includedTap.titleName.text =
                            getString(R.string.hi_zaid_omar, data.name)
                        setName(requireContext(), data.name)
                        e("SFDHDFDF", userPhoto.toString())
                        // todo id to be discussed
                        binding.iDTextView.hide()


                    } else {
                        showMessage(
                            requireContext(),
                            result.data.msg.message ?: getString(R.string.error)
                        )
                    }
                }

                is NetworkResults.Error -> {
                    result.exception.printStackTrace()
                    showMessage(requireContext(), getString(R.string.error))
                }
            }
        }
    }
}