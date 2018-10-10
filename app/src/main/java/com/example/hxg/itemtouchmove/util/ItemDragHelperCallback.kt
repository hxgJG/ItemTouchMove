package com.example.hxg.itemtouchmove.util

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * ItemDragHelperCallback
 * Created by YoKeyword on 15/12/29.
 */
open class ItemDragHelperCallback : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags: Int
        val manager = recyclerView.layoutManager
        dragFlags = if (manager is GridLayoutManager || manager is StaggeredGridLayoutManager) {
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        } else {
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        }
        // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
        val swipeFlags = 0
        return ItemTouchHelper.Callback.makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // 不同Type之间不可移动
        if (viewHolder.itemViewType != target.itemViewType) {
            return false
        }

        if (recyclerView.adapter is OnItemMoveListener) {
            (recyclerView.adapter as OnItemMoveListener).onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        }
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        // 不在闲置状态
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is OnDragVHListener) {
                (viewHolder as OnDragVHListener).onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is OnDragVHListener) {
            (viewHolder as OnDragVHListener).onItemFinish()
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun isLongPressDragEnabled(): Boolean {
        // 不支持长按拖拽功能 手动控制
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        // 不支持滑动功能
        return false
    }
}
