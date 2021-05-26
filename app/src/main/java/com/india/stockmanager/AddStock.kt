package com.india.stockmanager


import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class AddStock : AppCompatActivity() {
    var auth: FirebaseAuth? = FirebaseAuth.getInstance()
   // val mainactivity = MainActivity()
    var ur: Uri?=null
    var database = FirebaseDatabase.getInstance()
    lateinit var file: File
    lateinit var bytedata:ByteArray
    var vk:Int=0
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

            val inflater:View = layoutInflater.inflate(R.layout.custom_layoutdialog, null)
            val imageClick = AlertDialog.Builder(this).setView(inflater)
           val mAlertDialog = imageClick.show()
           val btnCam:Button =  inflater.findViewById(R.id.btnCamera)
           val btnGal:Button = inflater.findViewById(R.id.btnGallery)
            btnCam.setOnClickListener {
                /*var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    file = File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/attachments")!!.path,
                            System.currentTimeMillis().toString() + ".jpg")
//            fileUri = Uri.fromFile(file)
                    val fileUri = FileProvider.getUriForFile(this, this.applicationContext.packageName + ".provider", file!!)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    }
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(takePictureIntent, 123)
                }*/
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
            val fileName: String = SimpleDateFormat("yyyyMMddHHmm").format(Date())
            if(vk==2){
                var fileRef: StorageReference = storageRef.child(tit + fileName + ".jpg")
                fileRef.putBytes(bytedata).addOnSuccessListener {
                    fileRef.downloadUrl.addOnSuccessListener {
                        val temp: String = it.toString()
                        val model = Model(tit, count, temp, UserName)
                        myRef.child(tit).setValue(model).addOnSuccessListener {
                            progress.visibility = View.INVISIBLE
                            Toast.makeText(applicationContext, "Added Successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }.addOnFailureListener {
                            Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }.addOnFailureListener{
                    Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                }

            }
            else {
                var fileRef: StorageReference = storageRef.child(tit + fileName + "." + getFileExtensionv(ur))
                ur?.let { it1 ->
                    fileRef.putFile(it1).addOnSuccessListener {
                        fileRef.downloadUrl.addOnSuccessListener {
                            val temp: String = it.toString()
                            val model = Model(tit, count, temp, UserName)
                            myRef.child(tit).setValue(model).addOnSuccessListener {
                                progress.visibility = View.INVISIBLE
                                Toast.makeText(applicationContext, "Added Successfully", Toast.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_LONG).show()

                    }
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
           // ur = Uri.parse(file.path)
            //Log.d("Vairamuthu", ur.toString())
            var bitmap:Bitmap = data?.extras?.get("data") as Bitmap
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            bytedata = baos.toByteArray()
            vk=2
           /*
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                applicationContext.contentResolver,
                bitmap,
                "Title",
                null
            )
            ur = Uri.parse(path.toString())*/
            uploadImage.setImageBitmap(bitmap)
        } else if(requestCode == 456){

            uploadImage.setImageURI(data?.data)
            ur=data?.data
            Log.d("Vairamuthu", ur.toString())
        }
    }
}