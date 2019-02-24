package info.gdbtech.greg.myapplicationii

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
import android.view.Window
import info.gdbtech.greg.myapplicationii.ui.main.Vector

class MainActivity : Activity(), SensorEventListener {

    private val runner: Thread by lazy { Thread(RefreshRunner()) }

    private val view: MainView by lazy { MainView(this) }

    private lateinit var sensorManager: SensorManager

    private lateinit var accelerometer: Sensor


    val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MainActivity.GuiUpdateIdentifier -> this@MainActivity.view.invalidate()
            }
            super.handleMessage(msg)
        }
    }

//    val handler = UpdateHandler(this)
//    class UpdateHandler(val main:MainActivity) : Handler() {
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                MainActivity.GuiUpdateIdentifier -> main.view.invalidate()
//            }
//            super.handleMessage(msg)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        runner.start()

//        val clazz = handler.javaClass
//        val anonymous = clazz.isAnonymousClass
//        val member = clazz.isMemberClass
//        val local = clazz.isLocalClass
//        val static = clazz.modifiers and Modifier.STATIC
//
//        if ((anonymous || member || local) && (static == 0))
//            var x = 0;
        val systemService = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager = systemService
        accelerometer = systemService.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer != null) {
            systemService.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }
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
        Log.w("BlobAndroid", "onDestroy()")
        runner.interrupt()
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
        this@MainActivity.view.setGravity(force)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    inner class RefreshRunner : Runnable {
        override fun run() {
            Log.w("BlobAndroid", "Runnable start $this")
            while (!Thread.currentThread().isInterrupted) {
                val message = Message()
                message.what = MainActivity.GuiUpdateIdentifier
                this@MainActivity.handler.sendMessage(message)

                Thread.sleep(20)
            }
            Log.w("BlobAndroid", "Runnable exit $this")
        }
    }

    companion object {
        const val GuiUpdateIdentifier = 0x101
    }
}
