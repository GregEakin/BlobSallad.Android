package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.*
import kotlin.random.Random


open class Blob(private val x1: Float, private val y1: Float, var radius: Float, val numPoints: Int) {
    init {
        if (radius <= 0.0f)
            throw Exception("Can't have a negative radius.")

        if (numPoints < 0)
            throw Exception("Not enough points.")
    }

    constructor(mother: Blob) : this(mother.x1, mother.y1, mother.radius, mother.numPoints) {
    }

    enum class Eye {
        OPEN, CLOSED, CROSSED
    }

    enum class Face {
        SMILE, OPEN, OOH
    }

    val middle: PointMass = PointMass(x1, y1, 1.0f)

    val points: List<PointMass> = pointsInit()
    private fun pointsInit(): List<PointMass> {
        val list = mutableListOf<PointMass>()
        for (i in 0 until numPoints) {
            val theta = i * 2.0 * Math.PI / numPoints
            val cx = Math.cos(theta) * radius + x1
            val cy = Math.sin(theta) * radius + y1
            val mass = if (i >= numPoints - 2) 4.0f else 1.0f
            val pt = PointMass(cx.toFloat(), cy.toFloat(), mass)
            list.add(pt)
        }
        return list
    }

    val skins: List<Skin> = skinInit()
    private fun skinInit(): List<Skin> {
        val list = mutableListOf<Skin>()
        for (i in 0 until numPoints) {
            val pointMassA = points[i]
            val index = PointMassIndex(i + 1)
            val pointMassB = points[index]
            val skin = Skin(pointMassA, pointMassB)
            list.add(skin)
        }
        return list
    }

    val bones: List<Bones> = bonesInit()
    private fun bonesInit(): List<Bones> {
        val list = mutableListOf<Bones>()
        for (i in 0 until numPoints) {
            val crossShort = 0.95f
            val crossLong = 1.05f
            val middleShort = crossLong * 0.9f
            val middleLong = crossShort * 1.1f
            val pointMassA = points[i]

            val index = PointMassIndex(i + numPoints / 2 + 1)
            val pointMassB = points[index]
            val bone1 = Bones(pointMassA, pointMassB, crossShort, crossLong)
            list.add(bone1)

            val bone2 = Bones(pointMassA, middle, middleShort, middleLong)
            list.add(bone2)
        }
        return list
    }

    var selected: Boolean = false

    val x: Float
        get() = middle.xPos
    val y: Float
        get() = middle.yPos
    val mass: Float
        get() = middle.mass

    fun PointMassIndex(x: Int): Int {
        val m = numPoints
        return (x % m + m) % m
    }

    val collisions: MutableList<Collision> = mutableListOf()

    fun linkBlob(blob: Blob) {
        val dist = radius + blob.radius
        val collision = Collision(middle, blob.middle, dist * 0.95f)
        collisions.add(collision)
    }

    fun unlinkBlob(blob: Blob) {
        for (collision in collisions) {
            if (collision.pointMassB != blob.middle)
                continue

            collisions.remove(collision)
            break
        }
    }

    private var eyes: Eye = Eye.OPEN
    private var face: Face = Face.OPEN

    fun scale(scaleFactor: Float) {
        for (skin in skins)
            skin.scale(scaleFactor)

        for (bone in bones)
            bone.scale(scaleFactor)

        for (collision in collisions)
            collision.scale(scaleFactor)

        radius *= scaleFactor
    }

    fun move(dt: Float) {
        for (point in points)
            point.move(dt)

        middle.move(dt)
    }

    fun Sc(env: Environment) {
        for (j in 0 until 4) {
            for (point in points) {
                val collision = env.Collision(point.pos, point.prev)
                point.friction = if (collision) 0.75f else 0.01f
            }

            for (skin in skins)
                skin.sc(env)

            for (bone in bones)
                bone.sc(env)

            for (collision in collisions)
                collision.sc(env)
        }
    }

    var force: Vector
        get() {
            return middle.force
        }
        set(value) {
            for (point in points)
                point.force = value
            middle.force = value
        }

    fun addForce(force: Vector) {
        middle.addForce(force)
        for (point in points)
            point.addForce(force)

        if (!points.any())
            return

        // put a spin on the blob
        val pointMass = points[0]
        pointMass.addForce(force)
        pointMass.addForce(force)
        pointMass.addForce(force)
        pointMass.addForce(force)
    }

    fun moveTo(x2: Float, y2: Float) {
        val blobPos1 = middle.pos
        val x4 = x2 - blobPos1.x
        val y4 = y2 - blobPos1.y

        for (point in points) {
            val blobPos = point.pos
            blobPos.addX(x4)
            blobPos.addY(y4)
        }

        val blobPos = middle.pos
        blobPos.addX(x4)
        blobPos.addY(y4)
    }

    private val paint = Paint()

    fun drawEars(canvas: Canvas, scaleFactor: Float) {}

    fun drawEyesOpen(canvas: Canvas, scaleFactor: Float) {
        paint.strokeWidth = 1.0f

        val r1 = 0.12f * radius * scaleFactor
        val left1 = 0.35f * radius * scaleFactor
        val right1 = 0.65f * radius * scaleFactor
        val y1 = 0.30f * radius * scaleFactor

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(left1, y1, r1, paint)
        canvas.drawCircle(right1, y1, r1, paint)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(left1, y1, r1, paint)
        canvas.drawCircle(right1, y1, r1, paint)

        val r2 = 0.06f * radius * scaleFactor
        val left2 = 0.35f * radius * scaleFactor
        val right2 = 0.65f * radius * scaleFactor
        val y2 = 0.33f * radius * scaleFactor

        paint.style = Paint.Style.FILL
        canvas.drawCircle(left2, y2, r2, paint)
        canvas.drawCircle(right2, y2, r2, paint)
    }


    fun drawEyesClosed(canvas: Canvas, scaleFactor: Float) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.0f

        val r = 0.12f * radius * scaleFactor
        val left = 0.35f * radius * scaleFactor
        val right = 0.65f * radius * scaleFactor
        val y = 0.30f * radius * scaleFactor

        canvas.drawCircle(left, y, r, paint)
        canvas.drawLine(left - r, y, left + r, y, paint)

        canvas.drawCircle(right, y, r, paint)
        canvas.drawLine(right - r, y, right + r, y, paint)
    }

    fun drawSmile(canvas: Canvas, scaleFactor: Float, paint: Paint) {
        val left = 0.25f * radius * scaleFactor
        val top = 0.40f * radius * scaleFactor
        val right = 0.50f * radius * scaleFactor + left
        val bottom = 0.50f * radius * scaleFactor + top

        val oval = RectF(left, top, right, bottom)
        canvas.drawArc(oval, 0f, 180f, false, paint)
    }

    fun drawOohFace(canvas: Canvas, scaleFactor: Float) {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 2.0f
        drawSmile(canvas, scaleFactor, paint)

        val x1 = 0.25f * radius * scaleFactor
        val x2 = 0.45f * radius * scaleFactor
        val x3 = 0.75f * radius * scaleFactor
        val x4 = 0.55f * radius * scaleFactor
        val y1 = 0.20f * radius * scaleFactor
        val y2 = 0.30f * radius * scaleFactor
        val y3 = 0.40f * radius * scaleFactor

        canvas.drawLine(x1, y1, x2, y2, paint)
        canvas.drawLine(x2, y2, x1, y3, paint)
        canvas.drawLine(x3, y1, x4, y2, paint)
        canvas.drawLine(x4, y2, x3, y3, paint)
    }

    fun updateFace() {
        if (face == Face.SMILE && Random.nextDouble() < 0.05)
            face = Face.OPEN
        else if (face == Face.OPEN && Random.nextDouble() < 0.10)
            face = Face.SMILE

        if (eyes == Eye.OPEN && Random.nextDouble() < 0.025)
            eyes = Eye.CLOSED
        else if (eyes == Eye.CLOSED && Random.nextDouble() < 0.300)
            eyes = Eye.OPEN
    }


    fun drawFace(canvas: Canvas, scaleFactor: Float) {
//        if (middle.velocity > 0.004)
//            drawOohFace(canvas, scaleFactor)
//        else {
            if (face == Face.SMILE) {
                paint.style = Paint.Style.STROKE
                paint.strokeWidth = 2.0f
                drawSmile(canvas, scaleFactor, paint)
            } else {
                paint.style = Paint.Style.FILL
                paint.strokeWidth = 2.0f
                drawSmile(canvas, scaleFactor, paint)
            }

            if (eyes == Eye.OPEN) {
                drawEyesOpen(canvas, scaleFactor)
            } else {
                drawEyesClosed(canvas, scaleFactor)
            }
//        }
    }

    fun drawBody(canvas: Canvas, scaleFactor: Float) {

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.0f

        val path = Path()
        path.moveTo(points[0].xPos * scaleFactor, points[0].yPos * scaleFactor)
        for (i in points.indices) {

            val prevPointMass = points[PointMassIndex(i - 1)]
            val currentPointMass = points[PointMassIndex(i)]
            val nextPointMass = points[PointMassIndex(i + 1)]
            val nextNextPointMass = points[PointMassIndex(i + 2)]

//            var tx = nextPointMass.xPos
//            var ty = nextPointMass.yPos
//            val cx = currentPointMass.xPos
//            val cy = currentPointMass.yPos
//            var px = cx * 0.5f + tx * 0.5f
//            var py = cy * 0.5f + tx * 0.5f
//            val nx = cx - prevPointMass.xPos + tx - nextNextPointMass.xPos
//            val ny = cy - prevPointMass.yPos + ty - nextNextPointMass.yPos
//            px += nx * 0.16f
//            py += ny * 0.16f
//            px *= scaleFactor
//            py *= scaleFactor
//            tx *= scaleFactor
//            ty *= scaleFactor
//
//            path.cubicTo(px, py, tx, ty, tx, ty)

            canvas.drawCircle(currentPointMass.xPos, currentPointMass.yPos, points[i].mass, paint)

        }

//        canvas.drawPath(path, paint)
    }

    fun draw(canvas: Canvas, scaleFactor: Float) {
    }
}