package info.gdbtech.greg.blobandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import info.gdbtech.greg.blobandroid.ui.main.BlobCollective
import info.gdbtech.greg.blobandroid.ui.main.Environment
import info.gdbtech.greg.blobandroid.ui.main.Vector

class MainView(context: Context) : View(context) {

    init {
        isFocusable = true
    }

    private val env = Environment(0f, 0f, 700f, 700f)
    private val gravity = Vector(0f, 9.8e-5f)
    fun setGravity(force: Vector) {
        gravity.set(force)
    }

    private val collective = BlobCollective(200f, 200f, 25)
    private var last = SystemClock.uptimeMillis()

    init {
        Log.d("BlobAndroid", "Start")

        collective.split()
        collective.split()
//        collective.split()
    }

    private var mProfileFrames: Int = 0
    private var mProfileTime: Long = 0L
    private var mStepAverage: Long = 0L

//    private var trace = 100;

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        env.width = w.toFloat()
        env.height = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {

        val time = SystemClock.uptimeMillis()
        val delta = time - last

        if (delta >= 0L) {
            last = time

            // env.draw(canvas)

//            if (trace > 0) {
//                trace--;
//                if (trace == 0 && android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
//                    Log.d("BlobAndroid", "Log starting...")
//                    Debug.startMethodTracing("blob")
//                }
//            }

            val step = delta.toFloat()
            collective.move(step)
            collective.sc(env)
            collective.setForce(gravity)
            collective.draw(canvas)
//            if (trace == 0) {
//                Debug.stopMethodTracing()
//                Log.d("BlobAndroid", "Log finished.")
//                trace--;
//            }

            //invalidate()

            val endTime = SystemClock.uptimeMillis()
            val finalDelta = endTime - time
            mProfileTime += finalDelta
            mProfileFrames++
            mStepAverage += delta
            if (mProfileFrames > 200) {
                val averageFrameTime = mProfileTime.toFloat() / mProfileFrames
                val averageStep = mStepAverage.toFloat() / mProfileFrames
                Log.d("BlobAndroid", "Average: $averageFrameTime ms, step: $averageStep ms")
                mProfileTime = 0L
                mProfileFrames = 0
                mStepAverage = 0L
            }
        } else
            collective.draw(canvas)
    }

    fun keyEvent(event: KeyEvent?): Boolean {
        if (event != null && event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    collective.split()
                    return true
                }

                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    collective.join()
                    return true
                }
            }
        }
        return false
    }

    var selectedOffset: Point? = null

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) return false

        val xp = event.rawX
        val yp = event.rawY
        val action = event.action

        when (action) {
            MotionEvent.ACTION_UP -> {
                selectedOffset = null
                collective.unselectBlob()
                Log.d("BlobAndroid", "onTouch up $xp, $yp, $action, ${selectedOffset}")
            }
            MotionEvent.ACTION_DOWN -> {
                if (selectedOffset == null) {
                    selectedOffset = collective.findClosest(xp, yp)
                    Log.d("BlobAndroid", "onTouch down $xp, $yp, $action, ${selectedOffset}")
                }
            }
            else -> {
                if (selectedOffset != null)
                    collective.selectedBlobMoveTo(xp, yp)
            }
        }

        return super.onTouchEvent(event)
    }
}