package com.india.stockmanager.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.india.stockmanager.EditValue
import com.india.stockmanager.R
import com.india.stockmanager.model.AlphaChar


class AlphaAdaptors(var context: Context, var arrayList: ArrayList<AlphaChar>) : RecyclerView.Adapter<AlphaAdaptors.ItemHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.custom_grids, parent, false)
        return ItemHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        val alphaChar: AlphaChar = arrayList.get(position)

        holder.icons.setImageURI(alphaChar.iconsChar)
        holder.titles.text = alphaChar.alphaChar
        holder.titlescount.text = alphaChar.alphaCount

        holder.icons.setOnClickListener {
            val intent =Intent(context, EditValue::class.java).apply {
                setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)

        }
    }

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var icons = itemView.findViewById<ImageView>(R.id.imageView)
        var titles = itemView.findViewById<TextView>(R.id.title_textView)
        var titlescount = itemView.findViewById<TextView>(R.id.textView)
    }
}