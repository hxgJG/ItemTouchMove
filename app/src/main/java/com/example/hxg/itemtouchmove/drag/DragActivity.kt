package com.example.hxg.itemtouchmove.drag

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.hxg.itemtouchmove.R
import com.example.hxg.itemtouchmove.util.ItemDragHelperCallback
import java.util.*

/**
 * 拖拽
 * Created by YoKeyword on 16/1/4.
 */
class DragActivity : AppCompatActivity() {
    private var mRecy: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        mRecy = findViewById(R.id.recy)
        init()
    }

    private fun init() {
        val items = ArrayList<String>()
        for (i in 0..17) {
            items.add("Index $i")
        }

        mRecy?.layoutManager = GridLayoutManager(this, 2)

        val callback = object : ItemDragHelperCallback() {
            override fun isLongPressDragEnabled(): Boolean {
                // 长按拖拽打开
                return true
            }
        }
        ItemTouchHelper(callback).apply { attachToRecyclerView(mRecy) }

        mRecy?.adapter = DragAdapter(this, items)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRecy = null
    }
}