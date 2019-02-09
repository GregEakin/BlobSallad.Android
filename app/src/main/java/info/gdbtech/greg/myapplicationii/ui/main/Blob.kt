package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF


open class Blob(private val x1: Float, private val y1: Float, var radius: Float, val numPoints: Int) {
    init {
        if (radius <= 0.0f)
            throw Exception("Can't have a negative radius.")

        if (numPoints < 0)
            throw Exception("Not enough points.")
    }

    constructor(mother: Blob) : this(mother.x1, mother.y1, mother.radius, mother.numPoints) {
    }

    val middle: PointMass = PointMass(x1, y1, 1.0f)

    val points: List<PointMass> = pointsInit()
    private fun pointsInit(): List<PointMass> {
        val list = mutableListOf<PointMass>()
        for (i in 0 until numPoints) {
            val theta = i * 2.0 * Math.PI / numPoints
            val cx = (Math.cos(theta) * radius + x1).toFloat()
            val cy = (Math.sin(theta) * radius + y1).toFloat()
            val mass = if (i < 2) 4.0f else 1.0f
            val pt = PointMass(cx, cy, mass)
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

    fun drawEyesClosed(canvas: Canvas, scaleFactor: Float) {
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

    fun drawSmile(canvas: Canvas, scaleFactor: Float, paint: Paint) {
        val left = 0.25f * radius * scaleFactor
        val top = 0.40f * radius * scaleFactor
        val right = 0.50f * radius * scaleFactor + left
        val bottom = 0.50f * radius * scaleFactor + top

        val oval = RectF(left, top, right, bottom)
        canvas.drawArc(oval, 0f, 180f, false, paint)
    }
}