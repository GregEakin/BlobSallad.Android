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

package info.gdbtech.greg.blobandroid

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
import info.gdbtech.greg.blobandroid.ui.main.Vector
import java.lang.ref.WeakReference

class MainActivity : Activity(), SensorEventListener {

    private val runner: Thread by lazy { Thread(RefreshRunner()) }

    private val view: MainView by lazy { MainView(this) }

    private lateinit var sensorManager: SensorManager

    private lateinit var accelerometer: Sensor

    val handler: Handler = Handler(object : Handler.Callback {
        override fun handleMessage(msg: Message): Boolean {
            when (msg.what) {
                MainActivity.GuiUpdateIdentifier -> view.invalidate()
            }
            return true
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        runner.start()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onStart() {
        super.onStart()
        Log.w("BlobAndroid", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        Log.w("BlobAndroid", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        Log.w("BlobAndroid", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.w("BlobAndroid", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.w("BlobAndroid", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        runner.interrupt()
        Log.w("BlobAndroid", "onDestroy()")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.w("BlobAndroid", "onSaveInstanceState()")

//        outState?.run {
//            putInt("One", this@MainActivity.taskId)
//            putInt("Two", this.hashCode())
//            putInt("Three", this@MainActivity.view.hashCode())
//        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.w("BlobAndroid", "onRestoreInstanceState()")
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
        val hit = view.keyEvent(event)
        if (hit) return true
        return super.dispatchKeyEvent(event)
    }

    inner class RefreshRunner : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                val message = Message()
                message.what = MainActivity.GuiUpdateIdentifier
                handler.sendMessage(message)

                Thread.sleep(20)
            }
        }
    }

    companion object {
        const val GuiUpdateIdentifier = 0x101
    }
}
