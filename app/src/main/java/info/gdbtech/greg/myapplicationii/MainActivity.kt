package info.gdbtech.greg.myapplicationii

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Window

class MainActivity : Activity() {

    private val runner: Thread by lazy { Thread(RefreshRunner()) }

    private val view: MainView by lazy { MainView(this) }

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
    }

    inner class RefreshRunner : Runnable {
        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                val message = Message()
                message.what = MainActivity.GuiUpdateIdentifier
                this@MainActivity.handler.sendMessage(message)

                Thread.sleep(100)
            }
        }
    }

    companion object {
        const val GuiUpdateIdentifier = 0x101
    }
}
