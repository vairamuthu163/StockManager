package com.india.stockmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val ownerBtn:Button = findViewById(R.id.ownerBtn)
        val collaboratorBtn:Button =findViewById(R.id.collaboratorBtn)

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