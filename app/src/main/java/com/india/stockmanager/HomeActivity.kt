package com.india.stockmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val ownerBtn:Button = findViewById(R.id.ownerBtn)
        val collaboratorBtn:Button =findViewById(R.id.collaboratorBtn)
        if(auth!!.currentUser!=null)
        {
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        ownerBtn.setOnClickListener {
            val intent = Intent(applicationContext,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        collaboratorBtn.setOnClickListener {
            val intent = Intent(applicationContext,LoginActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}