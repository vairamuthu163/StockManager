package com.india.stockmanager

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(findViewById(R.id.toolBar))
        supportActionBar!!.title = "Settings"

        val collaboratorSettingTxt:TextView = findViewById(R.id.collaboratorSettingTxt)
        val changePassTxt:TextView = findViewById(R.id.changePassTxt)
        val deleteAccTxt:TextView = findViewById(R.id.deleteAccTxt)

        val userName:String = intent.getStringExtra("userName")!!
        val password:String = intent.getStringExtra("password")!!
        collaboratorSettingTxt.setOnClickListener {
            val intent = Intent(applicationContext,ChangeUserName::class.java).apply {
                putExtra("userName",userName)
                putExtra("password",password)
            }
            startActivity(intent)
        }
        changePassTxt.setOnClickListener {
            startActivity(Intent(applicationContext,ChangeOwnerPassword::class.java))
        }
        deleteAccTxt.setOnClickListener {
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Are you sure!")
            builder.setMessage("Do you want to Delete your account? this can't be undone.")
            builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int -> finish() })
            builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
            builder.show()
        }
    }
}