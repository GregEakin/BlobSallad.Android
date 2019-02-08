package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View
import kotlin.random.Random

class MainView(context: Context) : View(context) {

    private val drawable = ShapeDrawable(RectShape())
    private val boxWidth = 300
    private val boxHeight = 50

    private val position = Point(10, 10)
    private var last = System.currentTimeMillis()
    private var xVelocity = 0.1
    private var yVelocity = 0.1

    init {
        isFocusable = true

        val left = position.x
        val top = position.y
        val right = left + boxWidth
        val bottom = top + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        val now = System.currentTimeMillis()
        val delta = now - last
        if (delta > 10) {
            last = now
            position.x += (xVelocity * delta).toInt()
            position.y += (yVelocity * delta).toInt()

            if (position.x >= width - boxWidth && xVelocity > 0)
                xVelocity = -(0.5 * Random.nextDouble() + 0.5)
            else if (position.x < 0 && xVelocity < 0)
                xVelocity = 0.5 * Random.nextDouble() + 0.5

            if (position.y >= height - boxHeight && yVelocity > 0)
                yVelocity = -(0.5 * Random.nextDouble() + 0.5)
            else if (position.y < 0 && yVelocity < 0)
                yVelocity = 0.5 * Random.nextDouble() + 0.5
        }

        val left = position.x
        val top = position.y
        val right = left + boxWidth
        val bottom = top + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.draw(canvas!!)
    }
}