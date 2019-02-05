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
    private val point = Point(0, 0)

    enum class HorizontalDirection {
        LEFT, RIGHT
    }

    enum class VerticalDirection {
        UP, DOWN
    }

    private var xDirection = HorizontalDirection.RIGHT
    private var yDirection = VerticalDirection.UP

    init {
        isFocusable = true

        val x = 10
        val y = 10
        val width = 300
        val height = 50
        this.drawable.setBounds(x, y, x + width, y + height)
        this.drawable.paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        val x = point.x
        val y = point.y

        point.x += if (xDirection == HorizontalDirection.RIGHT)
            10
        else
            -10

        point.y += if (yDirection == VerticalDirection.DOWN)
            10
        else
            -10

        if (x >= width - 300)
            xDirection = HorizontalDirection.LEFT
        else if (x < 10)
            xDirection = HorizontalDirection.RIGHT

        if (y >= height - 50)
            yDirection = VerticalDirection.UP
        else if (y < 10)
            yDirection = VerticalDirection.DOWN

        this.drawable.setBounds(x, y, x + 50, y + 50)
        this.drawable.draw(canvas)
    }
}