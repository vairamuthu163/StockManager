package com.india.stockmanager

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class AddStock : AppCompatActivity() {
    val mainactivity = MainActivity()
    var ur: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)
        val capture: Button = findViewById(R.id.captureBtn)
        val gallery: Button = findViewById(R.id.galleryBtn)
        val add: Button = findViewById(R.id.add)
        val title: TextInputEditText = findViewById(R.id.stockTitle)
        val txtCount: TextInputEditText = findViewById(R.id.stockCount)

        val uploadImage: ImageView = findViewById(R.id.uploadImageView)
        uploadImage.setOnClickListener {
            Toast.makeText(applicationContext,"Clicked on image", Toast.LENGTH_SHORT).show()
        }
        add.setOnClickListener {
            val tit:String = title.text.toString()
            val count:String = txtCount.text.toString()
            mainactivity.addDatainList(ur,tit,count);
            Toast.makeText(applicationContext,"Add Sucess", Toast.LENGTH_SHORT).show()
            finish()
        }
        capture.setOnClickListener {
            var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent,123)
        }
        gallery.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,456)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uploadImage: ImageView = findViewById(R.id.uploadImageView)
        if(requestCode == 123)
        {
            var bmp: Bitmap = data?.extras?.get("data") as Bitmap
            uploadImage.setImageBitmap(bmp)

        } else if(requestCode == 456){
            uploadImage.setImageURI(data?.data)
            ur=data?.data
        }
    }
}