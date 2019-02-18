package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.View

class Arcs : GraphicsActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(SampleView(this))
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        Log.d("Arcs", "onWindowFocusChanged $hasFocus")
    }

    private class SampleView(context: Context) : View(context) {
//        init {
//            val info = context.applicationInfo
//            info.
//            Log.d("Arcs", "$(x)")
//        }

        private val mPaints: Array<Paint>

        init {
            val paint0 = Paint()
            paint0.isAntiAlias = true
            paint0.style = Paint.Style.FILL
            paint0.color = -0x77010000

            val paint1 = Paint(paint0)
            paint1.color = -0x77ff0100

            val paint2 = Paint(paint0)
            paint2.style = Paint.Style.STROKE
            paint2.strokeWidth = 4f
            paint2.color = -0x77ffff01

            val paint3 = Paint(paint2)
            paint3.color = -0x77777778

            mPaints = arrayOf(paint0, paint1, paint2, paint3)
            Log.d("Arcs", "Init for Paints[4]")
        }

        private val mFramePaint: Paint = Paint()

        init {
            mFramePaint.isAntiAlias = true
            mFramePaint.style = Paint.Style.STROKE
            mFramePaint.strokeWidth = 0f
            Log.d("Arcs", "Init for FramePaint")
        }

        private val mUseCenters: BooleanArray = booleanArrayOf(false, true, false, true)

        private val mOvals: Array<RectF>

        init {
            val oval0 = RectF(10f, 270f, 70f, 330f)
            val oval1 = RectF(90f, 270f, 150f, 330f)
            val oval2 = RectF(170f, 270f, 230f, 330f)
            val oval3 = RectF(250f, 270f, 310f, 330f)
            mOvals = arrayOf(oval0, oval1, oval2, oval3)
            Log.d("Arcs", "Init for Ovals[4]")
        }

        private val mBigOval: RectF = RectF(40f, 10f, 280f, 250f)
        private var mStart: Float = 0.toFloat()
        private var mSweep: Float = 0.toFloat()
        private var mBigIndex: Int = 0

        private fun drawArcs(canvas: Canvas, oval: RectF, useCenter: Boolean, paint: Paint) {
            canvas.drawRect(oval, mFramePaint)
            canvas.drawArc(oval, mStart, mSweep, useCenter, paint)
        }

        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)

            Log.d("Arcs", "Width: $w")
            Log.d("Arcs", "Height: $h")
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawColor(Color.WHITE)
            drawArcs(
                canvas, mBigOval, mUseCenters[mBigIndex],
                mPaints[mBigIndex]
            )
            for (i in 0..3) {
                drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i])
            }
            mSweep += SWEEP_INC
            if (mSweep > 360) {
                mSweep -= 360f
                mStart += START_INC
                if (mStart >= 360) {
                    mStart -= 360f
                }
                mBigIndex = (mBigIndex + 1) % mOvals.size
            }
            invalidate()
        }

        companion object {
            private const val SWEEP_INC = 2f
            private const val START_INC = 15f
        }
    }
}