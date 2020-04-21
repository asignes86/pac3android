package edu.uoc.android

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.settings_activity.*
import java.io.File
import java.io.IOException


class SettingsActivity : AppCompatActivity() {
    private val TAG = SettingsActivity::class.simpleName
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_TAKE_PHOTO = 2

    lateinit var photoURI: Uri

    private lateinit var imgFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        createImageFile()
        showImage()

        bt_camera.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                iv_camera.setImageBitmap(imageBitmap)
            }
        }
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show()
                showImage()
            } else {
                Toast.makeText(this, getString(R.string.error_saving_img), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun showImage() {
        if (imgFile.exists()) {
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            iv_camera.setImageBitmap(myBitmap)
            tv_camera.visibility = View.INVISIBLE
            iv_camera.visibility = View.VISIBLE
        } else {
            tv_camera.text = getString(R.string.no_exist_img)
            tv_camera.visibility = View.VISIBLE
            iv_camera.visibility = View.INVISIBLE
        }
    }

    private fun openCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Continue only if the File was successfully created
                imgFile.also {
                    photoURI = FileProvider.getUriForFile(
                        this,
                        "edu.uoc.android.fileprovider",
                        it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile() {
        val IMG_NAME = "pac3android_camera"
        val IMG_EXT = "jpg"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imgFile = File(storageDir, "$IMG_NAME.$IMG_EXT")
    }
}