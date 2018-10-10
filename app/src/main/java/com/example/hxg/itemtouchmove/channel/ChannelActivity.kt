package com.example.hxg.itemtouchmove.channel

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.Toast
import com.example.hxg.itemtouchmove.R
import com.example.hxg.itemtouchmove.util.ItemDragHelperCallback
import java.util.*

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 */
class ChannelActivity : AppCompatActivity() {

    private var mRecy: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        mRecy = findViewById(R.id.recy)
        init()
    }

    private fun init() {
        val items = ArrayList<ChannelEntity>()
        for (i in 0..17) {
            val entity = ChannelEntity().apply { name = "频道$i" }
            items.add(entity)
        }
        val otherItems = ArrayList<ChannelEntity>()
        for (i in 0..19) {
            val entity = ChannelEntity().apply { name = "其他$i" }
            otherItems.add(entity)
        }

        val helper = ItemTouchHelper(ItemDragHelperCallback()).apply {
            attachToRecyclerView(mRecy)
        }
        val adapter = ChannelAdapter(this, helper, items, otherItems)

        mRecy?.let {
            it.layoutManager = GridLayoutManager(this, 4).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        val viewType = adapter.getItemViewType(position)
                        return if (viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER) 1 else 4
                    }
                }
            }

            it.adapter = adapter
        }

        adapter.mChannelItemClickListener = object: ChannelAdapter.OnMyChannelItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                Toast.makeText(this@ChannelActivity, items[position].name, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRecy = null
    }
}
