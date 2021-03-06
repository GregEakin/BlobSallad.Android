/*
 * Copyright 2019 Greg Eakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.eakin.blob

import android.content.Context
import android.graphics.Canvas
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import dev.eakin.blob.ui.main.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MainView(context: Context) : View(context), KoinComponent {

    init {
        isFocusable = true
    }

    private val environment: Environment by inject()
    private val collective: BlobCollective by inject()

    private val gravity = Vector(0f, 9.8e-5f)
    fun setGravity(force: Vector) {
        gravity.setValue(force)
    }

    private var last = SystemClock.uptimeMillis()

    init {
        collective.split()
        collective.split()
    }

    private var mProfileFrames: Int = 0
    private var mProfileTime: Long = 0L
    private var mStepAverage: Long = 0L
    private var mTouchCount: Long = 0L

    // private var trace = 100;

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        environment.w = w.toFloat()
        environment.h = h.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        val time = SystemClock.uptimeMillis()
        val delta = time - last

        last = time

        // env.draw(canvas)
        // for (touch in touches)
        //     touch.draw(canvas)

        // if (trace > 0) {
        //     trace--;
        //     if (trace == 0 && android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
        //         Log.d("BlobAndroid", "Log starting...")
        //         Debug.startMethodTracing("blob")
        //     }
        // }

        val step = delta.toFloat()
        collective.move(step)
        collective.sc(environment)
        collective.setForce(gravity)
        collective.draw(canvas)

        // if (trace == 0) {
        //     Debug.stopMethodTracing()
        //     Log.d("BlobAndroid", "Log finished.")
        //     trace--;
        // }

        val endTime = SystemClock.uptimeMillis()
        val finalDelta = endTime - time
        mProfileTime += finalDelta
        mProfileFrames++
        mStepAverage += delta
        if (mProfileFrames > 200) {
            val averageFrameTime = mProfileTime.toFloat() / mProfileFrames
            val averageStep = mStepAverage.toFloat() / mProfileFrames
            // val averageTouch = mTouchCount / mProfileTime.toFloat()
            Log.d(
                "BlobAndroid",
                "Average: $averageFrameTime ms, step: $averageStep ms, touch: $mTouchCount / $mProfileTime"
            )
            mProfileTime = 0L
            mProfileFrames = 0
            mStepAverage = 0L
            mTouchCount = 0L
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
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

        return super.dispatchKeyEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mTouchCount++
        if (event.action == MotionEvent.ACTION_UP) {
            for (touch in touches)
                touch.zero()
            return true
        }

        for ((i, t) in touches.withIndex()) {
            if (i < event.pointerCount) {
                val id = event.getPointerId(i)
                val index = event.findPointerIndex(id)
                val x = event.getX(index)
                val y = event.getY(index)
                val p = event.getPressure(index)

                t.update(event.action, id, x, y, p)
            } else {
                t.zero()
            }
        }

        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    private val touches = createTouches()
    private fun createTouches(): List<Touch> {
        val list = mutableListOf<Touch>()
        for (i in 0 until 10) {
            val touch = Touch(collective, i)
            list.add(touch)
        }

        return list
    }
}
