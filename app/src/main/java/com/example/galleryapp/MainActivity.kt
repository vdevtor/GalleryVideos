package com.example.galleryapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.galleryapp.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var allPictures : ArrayList<Image>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




      //Storage Permissions
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                101
            )

        }

        allPictures = arrayListOf()


        if (allPictures!!.isEmpty()){
            allPictures = getAllImages()
            binding.progressRecycler.visibility = View.VISIBLE
            binding.imageRecycler.apply {
                layoutManager = GridLayoutManager(this.context,3)
                adapter = allPictures?.let { ImageAdapter(this.context, it) }
            }
            binding.progressRecycler.visibility = View.GONE

        }
    }

    private fun getAllImages(): ArrayList<Image>? {
        val images = ArrayList<Image>()
        val allImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA,MediaStore.Images.Media.DISPLAY_NAME)
        val cursor = this.contentResolver.query(allImageUri,projection,null,null,null)


        try {
            cursor?.moveToFirst()
            do {
                val image = Image()
                image.imagePath = cursor?.getString(cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DATA))

                image.imageName= cursor?.getString(cursor.getColumnIndexOrThrow(
                    MediaStore.Images.Media.DISPLAY_NAME))
                images.add(image)
            }while (cursor!!.moveToNext())
                cursor.close()
        }catch (e: Exception){

        }
        return images
    }
}