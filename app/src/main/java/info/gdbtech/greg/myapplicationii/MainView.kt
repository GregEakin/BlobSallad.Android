package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.Canvas
import android.view.View
import info.gdbtech.greg.myapplicationii.ui.main.BlobCollective
import info.gdbtech.greg.myapplicationii.ui.main.Environment
import info.gdbtech.greg.myapplicationii.ui.main.Vector

class MainView(context: Context) : View(context) {

    init {
        isFocusable = true
    }

    private val env = Environment(0f, 0f, 700f, 700f)
    private val gravity = Vector(0.0f, 10.0f)
    private val collective = BlobCollective(200.0f, 200.0f, 5)
    private val scaleFactor = 1.0f
    private var last = System.currentTimeMillis()

    init {
        collective.split()
        collective.split()
        collective.split()
        collective.split()
        collective.join()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null)
            return

        val now = System.currentTimeMillis()
        val delta = now - last
        last = now

        // env.draw(canvas)
        collective.move(delta / 1000f)
        collective.sc(env)
        collective.setForce(gravity)
        collective.draw(canvas, scaleFactor)

        invalidate()
    }
}
