package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.*
import android.view.View
import info.gdbtech.greg.myapplicationii.ui.main.Blob
import info.gdbtech.greg.myapplicationii.ui.main.BlobCollective
import info.gdbtech.greg.myapplicationii.ui.main.Environment
import info.gdbtech.greg.myapplicationii.ui.main.Vector
import kotlin.random.Random

class MainView(context: Context) : View(context) {

    private val boxWidth = 300
    private val boxHeight = 50

    private val position = PointF(10.0f, 10.0f)

//    private val drawable = ShapeDrawable(RectShape()) // MyDrawable()
//    init {
//
//        val left = position.x
//        val top = position.y
//        val right = left + boxWidth
//        val bottom = top + boxHeight
//        this.drawable.setBounds(left, top, right, bottom)
//        this.drawable.paint.color = Color.RED
//    }

    private var last = System.currentTimeMillis()
    private var xVelocity = 0.1f
    private var yVelocity = 0.1f

    init {
        isFocusable = true
    }

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2.0f
    }

    private val env = Environment(0f, 0f, 700f, 700f)
    private val gravity = Vector(0.0f, 10.0f)
    // private val blob = Blob(250f, 250f, 100f, 8)
    private val collective = BlobCollective(200.0f, 200.0f, 5)

    init {
        collective.split()
        collective.split()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null)
            return

        val now = System.currentTimeMillis()
        val delta = now - last
//        if (delta > 10) {
//            last = now
//            position.x += xVelocity * delta
//            position.y += yVelocity * delta
//
//            if (position.x >= width - boxWidth && xVelocity > 0f)
//                xVelocity = -(0.004f * Random.nextFloat() + 0.1f)
//            else if (position.x < 0f && xVelocity < 0f)
//                xVelocity = 0.004f * Random.nextFloat() + 0.1f
//
//            if (position.y >= height - boxHeight && yVelocity > 0f)
//                yVelocity = -(0.004f * Random.nextFloat() + 0.1f)
//            else if (position.y < 0f && yVelocity < 0f)
//                yVelocity = 0.004f * Random.nextFloat() + 0.1f
//        }
//
//        val left = position.x
//        val top = position.y
//        val right = left + boxWidth
//        val bottom = top + boxHeight
//        this.drawable.setBounds(left, top, right, bottom)
//        this.drawable.draw(canvas)

//        canvas.translate(position.x / 2f, position.y / 2f)
//        canvas.scale(1.1f, 1.1f)
//
//        val radius = 10.0f
//        canvas.drawCircle(x, y, radius, paint);
//
//        canvas.drawLine(0f, 0f, 20f, 20f, paint);
//        canvas.drawLine(20f, 0f, 0f, 20f, paint);

//        drawEyesClosed(canvas)
//        blob.moveTo(position.x, position.y)

        collective.move(0.05f) // delta / 1000f)
        collective.sc(env)
        collective.setForce(gravity)
        collective.draw(canvas, scaleFactor)

        invalidate()
    }

    // val face = PointF(50f, 50f)
    val radius = 100.0f
    val scaleFactor = 1.0f
}

