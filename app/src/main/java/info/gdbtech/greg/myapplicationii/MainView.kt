package info.gdbtech.greg.myapplicationii

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.view.View

class MainView(context: Context) : View(context) {

    private val boxWidth = 300
    private val boxHeight = 50

    private val position = Point(10, 10)

    private val drawable = ShapeDrawable(RectShape()) // MyDrawable()
    init {

        val left = position.x
        val top = position.y
        val right = left + boxWidth
        val bottom = top + boxHeight
        this.drawable.setBounds(left, top, right, bottom)
        this.drawable.paint.color = Color.RED
    }
    
    private var last = System.currentTimeMillis()
    private var xVelocity = 0.1
    private var yVelocity = 0.1

    init {
        isFocusable = true
    }

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2.0f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null)
            return

//        val now = System.currentTimeMillis()
//        val delta = now - last
//        if (delta > 10) {
//            last = now
//            position.x += (xVelocity * delta).toInt()
//            position.y += (yVelocity * delta).toInt()
//
//            if (position.x >= width - boxWidth && xVelocity > 0)
//                xVelocity = -(0.5 * Random.nextDouble() + 0.5)
//            else if (position.x < 0 && xVelocity < 0)
//                xVelocity = 0.5 * Random.nextDouble() + 0.5
//
//            if (position.y >= height - boxHeight && yVelocity > 0)
//                yVelocity = -(0.5 * Random.nextDouble() + 0.5)
//            else if (position.y < 0 && yVelocity < 0)
//                yVelocity = 0.5 * Random.nextDouble() + 0.5
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

        var rect = RectF(0f, 0f, radius, radius)
        val time = System.currentTimeMillis() % 1000L
        if (time > 500) {
            paint.strokeWidth = 1.0f
            paint.style = Paint.Style.STROKE
            canvas.drawRect(rect, paint)

            drawEyesOpen(canvas)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2.0f
            drawSmile(canvas, paint)

            canvas.translate(0f, 2f * radius)

            paint.strokeWidth = 1.0f
            paint.style = Paint.Style.STROKE
            canvas.drawRect(rect, paint)

            drawEyesClosed(canvas)
            paint.style = Paint.Style.FILL
            paint.strokeWidth = 2.0f
            drawSmile(canvas, paint)
        } else {
            paint.strokeWidth = 1.0f
            paint.style = Paint.Style.STROKE
            canvas.drawRect(rect, paint)

            drawEyesClosed(canvas)
            paint.style = Paint.Style.FILL
            paint.strokeWidth = 2.0f
            drawSmile(canvas, paint)

            canvas.translate(0f, 2f * radius)

            paint.strokeWidth = 1.0f
            paint.style = Paint.Style.STROKE
            canvas.drawRect(rect, paint)

            drawEyesOpen(canvas)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2.0f
            drawSmile(canvas, paint)
        }

        invalidate()
    }

    // val face = PointF(50f, 50f)
    val radius = 100.0f
    val scaleFactor = 1.0f

    fun drawEyesOpen(canvas: Canvas) {
        run {
            val r = 0.12f * radius * scaleFactor
            val x = 0.35f * radius * scaleFactor
            val y = 0.30f * radius * scaleFactor

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.0f
            canvas.drawCircle(x, y, r, paint)
        }

        run {
            val r = 0.12f * radius * scaleFactor
            val x = 0.65f * radius * scaleFactor
            val y = 0.30f * radius * scaleFactor

            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 1.0f
            canvas.drawCircle(x, y, r, paint)
        }

        run {
            val r = 0.06f * radius * scaleFactor
            val x = 0.35f * radius * scaleFactor
            val y = 0.33f * radius * scaleFactor

            paint.style = Paint.Style.FILL
            paint.strokeWidth = 1.0f
            canvas.drawCircle(x, y, r, paint)
        }

        run {
            val r = 0.06f * radius * scaleFactor
            val x = 0.65f * radius * scaleFactor
            val y = 0.33f * radius * scaleFactor

            paint.style = Paint.Style.FILL
            paint.strokeWidth = 1.0f
            canvas.drawCircle(x, y, r, paint)
        }
    }

    fun drawEyesClosed(canvas: Canvas) {
        paint.style = Paint.Style.STROKE
        paint.color = Color.BLACK
        paint.strokeWidth = 1.0f

        run {
            val r = 0.12f * radius * scaleFactor
            val x = 0.35f * radius * scaleFactor
            val y = 0.30f * radius * scaleFactor

            canvas.drawCircle(x, y, r, paint)
            canvas.drawLine(x - r, y, x + r, y, paint)
        }

        run {
            val r = 0.12f * radius * scaleFactor
            val x = 0.65f * radius * scaleFactor
            val y = 0.30f * radius * scaleFactor

            canvas.drawCircle(x, y, r, paint)
            canvas.drawLine(x - r, y, x + r, y, paint)
        }
    }

    fun drawSmile(canvas: Canvas, paint: Paint) {
        val left = 0.25f * radius * scaleFactor
        val top = 0.40f * radius * scaleFactor
        val right = 0.50f * radius * scaleFactor + left
        val bottom = 0.50f * radius * scaleFactor + top

        val oval = RectF(left, top, right, bottom)
        canvas.drawArc(oval, 0f, 180f, false, paint)
    }
}

