package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.Canvas
import android.os.SystemClock
import android.util.Log
import android.view.View
import info.gdbtech.greg.myapplicationii.ui.main.BlobCollective
import info.gdbtech.greg.myapplicationii.ui.main.Environment
import info.gdbtech.greg.myapplicationii.ui.main.Vector

class MainView(context: Context) : View(context) {

    init {
        isFocusable = true
    }

    private val env = Environment(0f, 0f, 700f, 700f)
    private val gravity = Vector(0.0f, 9.8e-5f)
    private val collective = BlobCollective(200.0f, 200.0f, 5)
    private val scaleFactor = 1.0f
    private var last = SystemClock.uptimeMillis()

    init {
        Log.d("BlobAndroid", "Start")

        collective.split()
        collective.split()
//        collective.split()
    }

    private var mProfileFrames: Int = 0
    private var mProfileTime: Long = 0L
    private var mFloat: Float = 0.0f

//    private var trace = 100;

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
            collective.draw(canvas, scaleFactor)
//            if (trace == 0) {
//                Debug.stopMethodTracing()
//                Log.d("BlobAndroid", "Log finished.")
//                trace--;
//            }

            invalidate()

            val endTime = SystemClock.uptimeMillis()
            val finalDelta = endTime - time
            mProfileTime += finalDelta
            mProfileFrames++
            mFloat += step
            if (mProfileFrames > 100) {
                val averageFrameTime = mProfileTime.toFloat() / mProfileFrames
                val averageStep = mFloat / mProfileTime
                Log.d("BlobAndroid", "Average: $averageFrameTime ms, step: $averageStep ms")
                mProfileTime = 0L
                mProfileFrames = 0
                mFloat = 0.0f
            }
        } else
            collective.draw(canvas, scaleFactor)
    }
}
