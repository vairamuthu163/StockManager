package com.india.stockmanager

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.india.stockmanager.adapters.AlphaAdaptors
import com.india.stockmanager.model.AlphaChar

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList:ArrayList<AlphaChar>? = null
    private var alphaAdaptors: AlphaAdaptors? = null

    //  arrayListfinal.add(AlphaChar(uri, title,count))
    companion object {
        var arrayListfinal: ArrayList<AlphaChar> = ArrayList()
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int -> finish()})
        builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))
        //setSupportActionBar(findViewById(R.id.toolBar))
        val btnfab = findViewById<FloatingActionButton>(R.id.fab)
        btnfab.setOnClickListener {
            val intent = Intent(applicationContext,AddStock::class.java)
            startActivity(intent)
        }

        recyclerView = findViewById(R.id.dataList)
        gridLayoutManager =
            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(false)

        arrayList = ArrayList()
        arrayList = setDataInList()
        alphaAdaptors = AlphaAdaptors(applicationContext, arrayList!!)
        recyclerView?.adapter = alphaAdaptors

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // val snack = Snackbar.make(applicationContext,"This is a simple Snackbar",Snackbar.LENGTH_LONG)
        when(item.itemId) {
            R.id.item1 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext,HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.item2 -> Toast.makeText(applicationContext,"Item2 is clicked",Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(applicationContext,"Item3 is clicked",Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(applicationContext,"No item is clicked",Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    fun setDataInList(): ArrayList<AlphaChar> {
        return arrayListfinal
    }
    fun addDatainList(uri: Uri?, title:String, count:String){
        arrayListfinal.add(AlphaChar(uri,title,count))
    }

    override fun onResume() {
        super.onResume()
        Log.d("StockVairam", arrayListfinal.size.toString())
        recyclerView = findViewById(R.id.dataList)
        gridLayoutManager =
            GridLayoutManager(applicationContext, 2, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = gridLayoutManager
        recyclerView?.setHasFixedSize(false)

        arrayList = ArrayList()
        arrayList = setDataInList()
        alphaAdaptors = AlphaAdaptors(applicationContext, arrayList!!)
        recyclerView?.adapter = alphaAdaptors

    }


}