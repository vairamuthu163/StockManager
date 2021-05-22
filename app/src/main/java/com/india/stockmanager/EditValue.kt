package com.india.stockmanager

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.FirebaseDatabase

class EditValue : AppCompatActivity() {
    var database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_value)
        var title:String = "null"
        lateinit var imageURI: Uri
        var Count:String = "null"
        title = intent.getStringExtra("Title")!!
        imageURI = Uri.parse(intent.getStringExtra("ImageURI")!!)
        Count = intent.getStringExtra("Count")!!
        val userName:String = intent.getStringExtra("UName")!!
        var myRef = database.getReference().child("data").child(userName).child(title)
        val titleTextView:TextView = findViewById(R.id.titleTextView)
        val imageView:ImageView = findViewById(R.id.imageView2)
        val count:TextView = findViewById(R.id.textView2)

        val Numberl:TextInputLayout = findViewById(R.id.Numberl)
        val Number:TextInputEditText = findViewById(R.id.Number)

        val increase:Button = findViewById(R.id.btnIcrease)
        val decrease:Button = findViewById(R.id.btnDecrease)
        val CancelBtn:Button = findViewById(R.id.CancelBtn)

        imageView.setImageURI(imageURI)
        titleTextView.text = title
        count.text = Count
        increase.setOnClickListener {
            if(TextUtils.isEmpty(Number.text.toString().trim()))
            {
                Numberl.error = "Enter some value"
                return@setOnClickListener
            }
            val integ:Int = Count.toInt()+Number.text.toString().trim().toInt()
            Count= integ.toString()
            val model = Model(title, Count, intent.getStringExtra("ImageURI")!!,userName)
            myRef.setValue(model).addOnSuccessListener {
                finish()
            }.addOnFailureListener {
                Toast.makeText(applicationContext,it.toString(), Toast.LENGTH_SHORT).show()
            }


        }
        decrease.setOnClickListener {
            if(TextUtils.isEmpty(Number.text.toString().trim()))
            {
                Numberl.error = "Enter some value"
                return@setOnClickListener
            }
            val integ:Int = Count.toInt()-Number.text.toString().trim().toInt()
            Count= integ.toString()
            val model = Model(title, Count, intent.getStringExtra("ImageURI")!!,userName)
            myRef.setValue(model).addOnSuccessListener {
                finish()
            }.addOnFailureListener {
                Toast.makeText(applicationContext,it.toString(), Toast.LENGTH_SHORT).show()
            }
        }
        CancelBtn.setOnClickListener {
            finish()
        }

    }
}

