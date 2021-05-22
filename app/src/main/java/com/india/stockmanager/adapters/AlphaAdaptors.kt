package com.india.stockmanager.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.india.stockmanager.EditValue
import com.india.stockmanager.R
import com.india.stockmanager.model.AlphaChar
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.ArrayList


class AlphaAdaptors : RecyclerView.Adapter<AlphaAdaptors.ItemHolder> {
     var mListener: RecyclerViewClickListener? = null
    lateinit var context:Context
    lateinit var arrayList:ArrayList<AlphaChar>

    constructor( context: Context, arrayList: ArrayList<AlphaChar>,mListener:RecyclerViewClickListener):super(){
        this.context = context
        this.arrayList = arrayList
        this.mListener = mListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_grids, parent, false)
        return ItemHolder(viewHolder,mListener!!)
    }
    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val alphaChar: AlphaChar = arrayList.get(position)


        holder.titles.text = alphaChar.title
        holder.titlescount.text = alphaChar.count
        lateinit var bitmap: Bitmap
        lateinit var uri: Uri
        Glide.with(context).asBitmap()
                .load(alphaChar.imageURI)
                .into(object : SimpleTarget<Bitmap?>() {
                    
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                        holder.icons.setImageBitmap(resource);
                        bitmap= resource;
                        uri = getImageUri(context.getApplicationContext(), bitmap)
                        holder.itemView.setOnClickListener {
                            val intent =Intent(context, EditValue::class.java).apply {
                                putExtra("Title",alphaChar.title)
                                putExtra("ImageURI",uri.toString())
                                putExtra("Count",alphaChar.count)
                                putExtra("UName",alphaChar.userName)
                                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            }
                            context.startActivity(intent)

                        }

                    }
                })

      //  Glide.with(context).load(alphaChar.imageURI).into(holder.icons)

    }

    private fun getImageUri(applicationContext: Context?, bitmap: Bitmap?): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, UUID.randomUUID().toString().toString() + ".png", "drawing")
        return Uri.parse(path)
    }

    class ItemHolder : RecyclerView.ViewHolder{
        var icons = itemView.findViewById<ImageView>(R.id.imageView)
        var titles = itemView.findViewById<TextView>(R.id.title_textView)
        var titlescount = itemView.findViewById<TextView>(R.id.textView)
        constructor(itemView: View,listener: RecyclerViewClickListener):super(itemView)
        {
            itemView.setOnLongClickListener {
                listener.OnLongClickListener(adapterPosition)
                return@setOnLongClickListener true
            }
           /*itemView.setOnloClickListener{
                listener.OnLongClickListener(adapterPosition)
            }*/
        }


    }
    public interface RecyclerViewClickListener {
        fun  OnLongClickListener(position: Int)
    }
}