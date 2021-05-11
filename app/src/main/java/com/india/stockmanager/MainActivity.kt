package com.india.stockmanager

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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