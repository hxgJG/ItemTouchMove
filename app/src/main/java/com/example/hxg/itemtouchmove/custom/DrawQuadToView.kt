package com.example.hxg.itemtouchmove.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// 二阶贝塞尔曲线
class DrawQuadToView : View {
    private var eventX: Int = 0
    private var eventY: Int = 0
    private var centerX: Int = 0
    private var centerY: Int = 0
    private var startX: Int = 0
    private var startY: Int = 0
    private var endX: Int = 0
    private var endY: Int = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    //测量大小完成以后回调
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = w / 2
        centerY = h / 2
        startX = centerX - 250
        startY = centerY
        endX = centerX + 250
        endY = centerY
        eventX = centerX
        eventY = centerY - 250
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        PAINT.color = Color.GRAY
        //画3个点
        canvas.drawCircle(startX.toFloat(), startY.toFloat(), 8f, PAINT)
        canvas.drawCircle(endX.toFloat(), endY.toFloat(), 8f, PAINT)
        canvas.drawCircle(eventX.toFloat(), eventY.toFloat(), 8f, PAINT)

        //绘制连线
        PAINT.strokeWidth = 3f
        canvas.drawLine(startX.toFloat(), centerY.toFloat(), eventX.toFloat(), eventY.toFloat(), PAINT)
        canvas.drawLine(endX.toFloat(), centerY.toFloat(), eventX.toFloat(), eventY.toFloat(), PAINT)

        //画二阶贝塞尔曲线
        PAINT.color = Color.GREEN
        PAINT.style = Paint.Style.STROKE
        val path = Path()
        path.moveTo(startX.toFloat(), startY.toFloat())
        path.quadTo(eventX.toFloat(), eventY.toFloat(), endX.toFloat(), endY.toFloat())
        canvas.drawPath(path, PAINT)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                eventX = event.x.toInt()
                eventY = event.y.toInt()
                invalidate()
            }
        }
        return true
    }
    
    companion object {
        private val PAINT = Paint().apply {
            isAntiAlias = true
        }
    }
}