package com.example.hxg.itemtouchmove.drag

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.hxg.itemtouchmove.R
import com.example.hxg.itemtouchmove.util.OnDragVHListener
import com.example.hxg.itemtouchmove.util.OnItemMoveListener

/**
 * 仅拖拽排序
 * Created by YoKeyword on 16/1/4.
 */
class DragAdapter(context: Context, private val mItems: MutableList<String>) : RecyclerView.Adapter<DragAdapter.DragViewHolder>(), OnItemMoveListener {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DragViewHolder {
        return DragViewHolder(mInflater.inflate(R.layout.item_drag, parent, false))
    }

    override fun onBindViewHolder(holder: DragViewHolder, position: Int) {
        holder.tv.text = mItems[position]
    }

    override fun getItemCount() = mItems.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val from = mItems[fromPosition]
        val to = mItems[toPosition]
//        val item = mItems[fromPosition]
        mItems[fromPosition] = to
        mItems[toPosition] = from
        notifyDataSetChanged()
//        mItems.removeAt(fromPosition)
//        mItems.add(toPosition, item)
//        notifyItemMoved(fromPosition, toPosition)
    }

    inner class DragViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnDragVHListener {
        var tv: TextView = itemView.findViewById(R.id.tv)

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemFinish() {
            itemView.setBackgroundColor(0)
        }
    }
}
