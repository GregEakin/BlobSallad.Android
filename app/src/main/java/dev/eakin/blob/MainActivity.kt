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

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.Window
import dev.eakin.blob.ui.main.Vector
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class MainActivity : Activity(), SensorEventListener {

    private val view: MainView by lazy { MainView(this) }

    private lateinit var sensorManager: SensorManager

    private lateinit var accelerometer: Sensor

    private val handler: Handler = IncomingHandler(this)

    private val scheduleTaskExecutor: ScheduledExecutorService = Executors.newScheduledThreadPool(5)

    private var f1: ScheduledFuture<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        // Log.w("BlobAndroid", "onCreate() ${Thread.currentThread().id}")

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStart() {
        super.onStart()
        // Log.w("BlobAndroid", "onStart() ${Thread.currentThread().id}")

        f1?.cancel(false)
        f1 = scheduleTaskExecutor.scheduleAtFixedRate(RefreshRunner(), 200, 20, TimeUnit.MILLISECONDS);
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        // Log.w("BlobAndroid", "onResume() ${Thread.currentThread().id}")
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        // Log.w("BlobAndroid", "onPause() ${Thread.currentThread().id}")
    }

    override fun onStop() {
        super.onStop()
        // Log.w("BlobAndroid", "onStop() ${Thread.currentThread().id}")
        f1?.cancel(false)
        f1 = null
    }

//    override fun onRestart() {
//        super.onRestart()
//        Log.w("BlobAndroid", "onRestart() ${Thread.currentThread().id}")
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        // Log.w("BlobAndroid", "onDestroy() ${Thread.currentThread().id}")
//        f1?.cancel(false)
//        f1 = null
//    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("Blobs", 3)
        Log.w("BlobAndroid", "onSaveInstanceState(3) ${Thread.currentThread().id}")

//        outState?.run {
//            putInt("One", this@MainActivity.taskId)
//            putInt("Two", this.hashCode())
//            putInt("Three", this@MainActivity.view.hashCode())
//        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        val blobs = savedInstanceState?.getInt("Blobs")
        Log.w("BlobAndroid", "onRestoreInstanceState($blobs) ${Thread.currentThread().id}")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val x = event.values[0] * -1e-4f
        val y = event.values[1] * 1e-4f
        val force = Vector(x, y)
        view.setGravity(force)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return view.onTouchEvent(event)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        val hit = view.dispatchKeyEvent(event)
        if (hit) return true
        return super.dispatchKeyEvent(event)
    }

    inner class RefreshRunner : Runnable {
        override fun run() {
            val message = Message()
            message.what = GuiUpdateIdentifier
            handler.sendMessage(message)
        }
    }

    companion object {
        const val GuiUpdateIdentifier = 0x101

        internal class IncomingHandler(mainActivity: MainActivity) : Handler() {
            private val mActivity: WeakReference<MainActivity> = WeakReference(mainActivity)

            override fun handleMessage(msg: Message) {
                val mainActivity = mActivity.get() ?: return
                when (msg.what) {
                    GuiUpdateIdentifier -> mainActivity.view.invalidate()
                }
            }
        }
    }
}
