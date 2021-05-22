package com.india.stockmanager


import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream


class AddStock : AppCompatActivity() {
    var auth: FirebaseAuth? = FirebaseAuth.getInstance()
   // val mainactivity = MainActivity()
    var ur: Uri?=null
    var database = FirebaseDatabase.getInstance()


    var storageRef: StorageReference = FirebaseStorage.getInstance().getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stock)
        val UserName = intent.getStringExtra("UserName")!!
        var myRef = database.getReference().child("data").child(UserName!!)


        val add: Button = findViewById(R.id.add)
        val title: TextInputEditText = findViewById(R.id.stockTitle)
        val txtCount: TextInputEditText = findViewById(R.id.stockCount)
        val uploadImage: ImageView = findViewById(R.id.uploadImageView)

        var progress:ProgressBar = findViewById(R.id.addStockProgress)
        progress.visibility = View.INVISIBLE
        uploadImage.setOnClickListener {

            val inflater:View = layoutInflater.inflate(R.layout.custom_layoutdialog,null)
            val imageClick = AlertDialog.Builder(this).setView(inflater)
           val mAlertDialog = imageClick.show()
           val btnCam:Button =  inflater.findViewById(R.id.btnCamera)
           val btnGal:Button = inflater.findViewById(R.id.btnGallery)
            btnCam.setOnClickListener {
                var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, 123)
                mAlertDialog.dismiss()
            }
           btnGal.setOnClickListener {
               val intent = Intent(Intent.ACTION_PICK)
               intent.type = "image/*"
               startActivityForResult(intent, 456)
               mAlertDialog.dismiss()

           }
        }
        add.setOnClickListener {
            progress.visibility = View.VISIBLE
            val tit:String = title.text.toString().trim()
            val count:String = txtCount.text.toString().trim()
            if(ur==null){
                val resourceURI =
                    Uri.parse("android.resource://" + this.packageName + "/" + R.drawable.letter_a)
                ur=resourceURI
            }

           // Toast.makeText(applicationContext,"Add Sucess", Toast.LENGTH_SHORT).show()
            var fileRef: StorageReference = storageRef.child(tit + "." + getFileExtensionv(ur))
            ur?.let { it1 ->
                fileRef.putFile(it1).addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener {
                        val temp:String = it.toString()
                        val model = Model(tit, count, temp,UserName)
                        myRef.child(tit).setValue(model).addOnSuccessListener {
                            progress.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "Add Sucess", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                        }

                    }

                }.addOnFailureListener {
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()

                }.addOnProgressListener {
                    progress.visibility = View.VISIBLE
                   // Toast.makeText(applicationContext, "onGoing", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getFileExtensionv(ur: Uri?):String? {
        // ContentResolver cr = getContentResolver()
        val cr: ContentResolver = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(ur?.let { cr.getType(it) })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uploadImage: ImageView = findViewById(R.id.uploadImageView)
        if(requestCode == 123)
        {
            var bitmap:Bitmap = data?.extras?.get("data") as Bitmap
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                applicationContext.contentResolver,
                bitmap,
                "Title",
                null
            )
            ur = Uri.parse(path.toString())
            uploadImage.setImageBitmap(bitmap)
        } else if(requestCode == 456){
            uploadImage.setImageURI(data?.data)
            ur=data?.data
        }
    }
}