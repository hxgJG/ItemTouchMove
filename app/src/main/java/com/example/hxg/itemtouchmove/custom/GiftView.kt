package com.example.hxg.itemtouchmove.custom

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout

import com.example.hxg.itemtouchmove.R
import com.example.hxg.itemtouchmove.util.XLog
import com.example.hxg.itemtouchmove.util.XUI

class GiftView : RelativeLayout {
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var layoutParams: RelativeLayout.LayoutParams? = null
    private val gifts = arrayOfNulls<Drawable>(5)

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(ctx: Context) {
        gifts[0] = getPic(ctx, R.mipmap.ic1)
        gifts[1] = getPic(ctx, R.mipmap.ic2)
        gifts[2] = getPic(ctx, R.mipmap.ic3)
        gifts[3] = getPic(ctx, R.mipmap.ic4)
        gifts[4] = getPic(ctx, R.mipmap.ic5)

        layoutParams = RelativeLayout.LayoutParams(WIDTH, WIDTH).apply {
            addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        }
    }

    private fun getPic(ctx: Context, @DrawableRes id: Int) = ContextCompat.getDrawable(ctx, id)

    fun addImageView() {
        if (gifts.isEmpty()) return
        val index = (Math.random() * gifts.size).toInt()
        XLog.e("hxg", "index = $index")
        val imageView = ImageView(context).also {
            it.setImageDrawable(gifts[index])
            it.layoutParams = layoutParams
        }

        addView(imageView)
        setAnim(imageView).start()
        getBezierAnimator(imageView).start()
    }

    private fun getRandomPointF(): PointF = PointF().apply {
        x = (Math.random() * mWidth).toFloat()
        y = (Math.random() * mHeight / 4).toFloat()
    }

    private fun getBezierAnimator(target: View): ValueAnimator {
        // init start point and end point
        // make start point is center horizontal
        val startPoint = PointF(((mWidth - WIDTH) / 2).toFloat(), mHeight.toFloat())
        val endPoint = PointF((Math.random() * mWidth.toDouble() * 2.0 / 3).toFloat(), (Math.random() * 50).toFloat())
        return ValueAnimator.ofObject(BezierEvaluator(getRandomPointF(), getRandomPointF()), startPoint, endPoint).apply {
            addUpdateListener(BezierListener(target))
            setTarget(target)
            duration = 3000
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // measure current GiftView
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    private fun setAnim(view: View): AnimatorSet {
        val scaleX = ObjectAnimator.ofFloat(view, View.SCALE_X, 0.2f, 1f)
        val scaleY = ObjectAnimator.ofFloat(view, View.SCALE_Y, 0.2f, 1f)

        return AnimatorSet().apply {
            duration = 500
            interpolator = LinearInterpolator()
            playTogether(scaleX, scaleY)
            setTarget(view)
        }
    }

    private inner class BezierListener(private val target: View) : ValueAnimator.AnimatorUpdateListener {

        override fun onAnimationUpdate(animation: ValueAnimator) {
            //Assign the x, y value calculated from the Bezier curve to the view
            val pointF = animation.animatedValue as? PointF ?: return
            target.x = pointF.x
            target.y = pointF.y
            target.alpha = 1 - animation.animatedFraction
        }
    }

    inner class BezierEvaluator(private val pointF1: PointF, private val pointF2: PointF) : TypeEvaluator<PointF> {
        override fun evaluate(time: Float, startValue: PointF, endValue: PointF): PointF {
            return PointF().apply {
                val timeLeft = 1.0f - time

                val v1 = timeLeft * timeLeft * timeLeft
                val v2 = 3f * timeLeft * timeLeft * time
                val v3 = 3f * timeLeft * time * time
                val v4 = time * time * time

                x = (v1 * startValue.x + v2 * pointF1.x + v3 * pointF2.x + v4 * endValue.x)
                y = (v1 * startValue.y + v2 * pointF1.y + v3 * pointF2.y + v4 * endValue.y)
            }
        }
    }

    companion object {
        private val WIDTH = XUI.DpToPx(40f)
    }
}
