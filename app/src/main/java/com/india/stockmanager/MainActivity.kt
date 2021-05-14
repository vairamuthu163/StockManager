package com.india.stockmanager

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.india.stockmanager.adapters.AlphaAdaptors
import com.india.stockmanager.model.AlphaChar


class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var arrayList:ArrayList<AlphaChar>? = null
    private var alphaAdaptors: AlphaAdaptors? = null
    var auth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var root:DatabaseReference = FirebaseDatabase.getInstance().getReference().child("data").child(
        auth!!.currentUser.uid
    )
    //  arrayListfinal.add(AlphaChar(uri, title,count))
    companion object {
        var arrayListfinal: ArrayList<AlphaChar> = ArrayList()
    }

    override fun onBackPressed() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure!")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int -> finish() })
        builder.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolBar))
        //setSupportActionBar(findViewById(R.id.toolBar))
        val btnfab = findViewById<FloatingActionButton>(R.id.fab)
        btnfab.setOnClickListener {
            val intent = Intent(applicationContext, AddStock::class.java)
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
        root.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (datasnapshot: DataSnapshot in snapshot.children) {
                    val alpha: AlphaChar? = datasnapshot.getValue(AlphaChar::class.java)
                    if (alpha != null) {
                        arrayListfinal.add(alpha)
                        alphaAdaptors!!.notifyDataSetChanged()
                    }

                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       // val snack = Snackbar.make(applicationContext,"This is a simple Snackbar",Snackbar.LENGTH_LONG)
        when(item.itemId) {
            R.id.item1 -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            R.id.item2 -> Toast.makeText(applicationContext, "Item2 is clicked", Toast.LENGTH_SHORT)
                .show()
            R.id.item3 -> Toast.makeText(applicationContext, "Item3 is clicked", Toast.LENGTH_SHORT)
                .show()
            else -> Toast.makeText(applicationContext, "No item is clicked", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    fun setDataInList(): ArrayList<AlphaChar> {
        return arrayListfinal
    }
    fun addDatainList(uri: String, title: String, count: String){
        arrayListfinal.add(AlphaChar(uri, title, count))
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


