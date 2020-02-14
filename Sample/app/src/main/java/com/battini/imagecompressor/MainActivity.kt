package com.battini.imagecompressor

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.battini.imagecompressor.utils.FileUtils
import com.battini.imagecompressor.utils.FileUtils.copyFileToDeviceSampleFolder
import com.battini.imagecompressor.utils.ImageCompressor
import com.battini.imagecompressor.utils.getPicturesFile
import com.battini.imagecompressor.utils.getSizeInKB
import com.opensooq.supernova.gligar.GligarPicker
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private val REQUEST_STORAGE = 2
    private val REQUEST_IMAGE_CAPTURE = 1
    private var filePath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun chooseImage(view: View) {
        if (isStoragePermissionGiven()) {
            pickImage()
        } else {
            requestPermission()
        }
    }

    private fun pickImage() {
        GligarPicker().requestCode(REQUEST_IMAGE_CAPTURE).withActivity(this).limit(1).show()
    }

    fun compress(view: View) {
        filePath?.let {
            val file = File(it)
            ImageCompressor.compressBitmap(this, file) {
                after_size_value.text = file.getSizeInKB()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val pathsList = data?.getExtras()?.getStringArray(GligarPicker.IMAGES_RESULT)
            pathsList?.let {
                filePath = it[0]
                filePath?.let { path ->
                    val file = File(path)
                    val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                    before_size_value.text = file.getSizeInKB()
                    after_size_value.text = ""
                    chosen_image.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun isStoragePermissionGiven(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_STORAGE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_STORAGE -> pickImage()
        }
    }

}

