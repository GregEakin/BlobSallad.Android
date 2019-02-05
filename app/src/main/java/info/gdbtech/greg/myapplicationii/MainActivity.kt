package info.gdbtech.greg.myapplicationii

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.Window

class MainActivity : AppCompatActivity() {

    private val runner: Thread by lazy { Thread(RefreshRunner())}

    val view: MainView by lazy { MainView(this) }

    val myGUIUpdateHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MainActivity.GuiUpdateIdentifier -> this@MainActivity.view!!.invalidate()
            }
            super.handleMessage(msg)
        }
    }

//    val handler = UpdateHandler(this)
//
//    class UpdateHandler(val main:MainActivity) : Handler() {
//        override fun handleMessage(msg: Message) {
//            when (msg.what) {
//                MainActivity.GuiUpdateIdentifier -> main.view!!.invalidate()
//            }
//            super.handleMessage(msg)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(view)
        runner.start()
    }

    inner class RefreshRunner : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                val message = Message()
                message.what = MainActivity.GuiUpdateIdentifier
                this@MainActivity.myGUIUpdateHandler.sendMessage(message)
            }
        }
    }

    companion object {
        const val GuiUpdateIdentifier = 0x101
    }
}
