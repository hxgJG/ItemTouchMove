package com.example.hxg.itemtouchmove.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

// 三阶贝塞尔曲线
class DrawCubicView : View {
    private var leftX: Int = 0
    private var leftY: Int = 0
    private var rightX: Int = 0
    private var rightY: Int = 0
    private var centerX: Int = 0
    private var centerY: Int = 0
    private var startX: Int = 0
    private var startY: Int = 0
    private var endX: Int = 0
    private var endY: Int = 0
    private var isMoveLeft: Boolean = false

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
        leftX = startX
        leftY = centerY - 250
        rightX = endX
        rightY = endY - 250
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        PAINT.color = Color.GRAY
        //画4个点
        canvas.drawCircle(startX.toFloat(), startY.toFloat(), 8f, PAINT)
        canvas.drawCircle(endX.toFloat(), endY.toFloat(), 8f, PAINT)
        canvas.drawCircle(leftX.toFloat(), leftY.toFloat(), 8f, PAINT)
        canvas.drawCircle(rightX.toFloat(), rightY.toFloat(), 8f, PAINT)

        //绘制连线
        PAINT.strokeWidth = 3f
        canvas.drawLine(startX.toFloat(), startY.toFloat(), leftX.toFloat(), leftY.toFloat(), PAINT)
        canvas.drawLine(leftX.toFloat(), leftY.toFloat(), rightX.toFloat(), rightY.toFloat(), PAINT)
        canvas.drawLine(rightX.toFloat(), rightY.toFloat(), endX.toFloat(), endY.toFloat(), PAINT)

        //画二阶贝塞尔曲线
        PAINT.color = Color.GREEN
        PAINT.style = Paint.Style.STROKE
        val path = Path()
        path.moveTo(startX.toFloat(), startY.toFloat())
        path.cubicTo(leftX.toFloat(), leftY.toFloat(), rightX.toFloat(), rightY.toFloat(), endX.toFloat(), endY.toFloat())
        canvas.drawPath(path, PAINT)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                if (isMoveLeft) {
                    leftX = event.x.toInt()
                    leftY = event.y.toInt()
                } else {
                    rightX = event.x.toInt()
                    rightY = event.y.toInt()
                }
                invalidate()
            }
        }
        return true
    }

    fun moveLeft() {
        isMoveLeft = true
    }

    fun moveRight() {
        isMoveLeft = false
    }

    companion object {
        private val PAINT = Paint().apply {
            isAntiAlias = true
        }
    }
}