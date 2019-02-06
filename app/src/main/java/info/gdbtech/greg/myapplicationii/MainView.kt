package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View

class MainView(context: Context) : View(context) {

    private val drawable = ShapeDrawable(RectShape())
    private val point = Point(10, 10)
    private val boxWidth = 300
    private val boxHeight = 50
    private var start = System.currentTimeMillis()

    private var xVelocity = 0.1
    private var yVelocity = 0.1

    init {
        isFocusable = true

        val left = point.x
        val top = point.y
        val right = left + boxWidth
        val bottom = top + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {

        val now = System.currentTimeMillis()
        val delta = now - start
        if (delta > 50) {
            start = now
            point.x += (xVelocity * delta).toInt()
            point.y += (yVelocity * delta).toInt()

            if (point.x >= width - boxWidth && xVelocity > 0)
                xVelocity *= -1.01
            else if (point.x < 0 && xVelocity < 0)
                xVelocity *= -0.99

            if (point.y >= height - boxHeight && yVelocity > 0)
                yVelocity *= -1.02
            else if (point.y < 0 && yVelocity < 0)
                yVelocity *= -0.98
        }

        val left = point.x
        val top = point.y
        val right = point.x + boxWidth
        val bottom = point.y + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.draw(canvas)
    }
}