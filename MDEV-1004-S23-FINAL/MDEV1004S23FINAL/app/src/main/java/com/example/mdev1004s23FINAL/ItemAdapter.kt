package com.example.mdev1004s23FINAL

// -------------------------------------------------------------
// - Robert Bettinelli - MDEV1004 - S2023
// - 090003683@student.georgianc.on.ca
// -------------------------------------------------------------
// 08/23/2023 - RBettinelli - Header and Documentation Added
// -------------------------------------------------------------

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(var items: MutableList<MyItem>, private val deleteClickListener: (MyItem) -> Unit) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    //var & Listeners.
    private var onClickListener: OnClickListener? = null

    // Fields for View.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descTextView: TextView = itemView.findViewById(R.id.descTextView)
        val btnDelMovie: ImageButton = itemView.findViewById(R.id.btnDelMovie)
    }

    //View Setup
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_item, parent, false)
        return ViewHolder(view)
    }

    // Get Data & Setup Select Button & Delete Button
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.descTextView.text = item.description

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }

        holder.btnDelMovie.setOnClickListener {
            deleteClickListener.invoke(item)
        }
    }

    // When Delete Button Pressed.
    fun remove(item: MyItem) {
        val position = items.indexOf(item)
        if (position != -1) {
            items.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // Select Button select get Movie Data.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: MyItem)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

