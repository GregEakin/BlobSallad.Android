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
    // private val start = System.currentTimeMillis()

    enum class HorizontalDirection {
        LEFT, RIGHT
    }

    enum class VerticalDirection {
        UP, DOWN
    }

    private var xDirection = HorizontalDirection.RIGHT
    private var yDirection = VerticalDirection.UP

    //private var xVelocity = 10
    //private var yVelocity = 10

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

        point.x += if (xDirection == HorizontalDirection.RIGHT)
            10
        else
            -10

        point.y += if (yDirection == VerticalDirection.DOWN)
            10
        else
            -10

        if (point.x >= width - boxWidth)
            xDirection = HorizontalDirection.LEFT
        else if (point.x < 10)
            xDirection = HorizontalDirection.RIGHT

        if (point.y >= height - boxHeight)
            yDirection = VerticalDirection.UP
        else if (point.y < 10)
            yDirection = VerticalDirection.DOWN

        val left = point.x
        val top = point.y
        val right = point.x + boxWidth
        val bottom = point.y + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.draw(canvas)
    }
}