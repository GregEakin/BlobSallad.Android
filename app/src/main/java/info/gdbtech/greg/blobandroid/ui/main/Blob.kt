/*
 * Copyright 2019 Greg Eakin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.gdbtech.greg.blobandroid.ui.main

import android.graphics.*
import kotlin.random.Random

open class Blob(private val xx: Float, private val yy: Float, var radius: Float, val numPoints: Int) {
    init {
        if (radius <= 0.0f)
            throw Exception("Can't have a negative radius.")

        if (numPoints < 0)
            throw Exception("Need at least three points to draw a blob.")
    }

    constructor(mother: Blob) : this(mother.x, mother.y, mother.radius, mother.numPoints)

    enum class Eye {
        OPEN, CLOSED, CROSSED
    }

    enum class Face {
        SMILE, OPEN, OOH
    }

    val middle: PointMass = PointMass(xx, yy, 1.0f)

    val points: List<PointMass> = pointsInit()
    private fun pointsInit(): List<PointMass> {
        val list = mutableListOf<PointMass>()
        for (i in 0 until numPoints) {
            val theta = i * 2.0 * Math.PI / numPoints
            val cx = Math.cos(theta) * radius + xx
            val cy = Math.sin(theta) * radius + yy
            val mass = if (i < 2) 4.0f else 1.0f
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
            val index = pointMassIndex(i + 1)
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

            val index = pointMassIndex(i + numPoints / 2 + 1)
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

    fun pointMassIndex(x: Int): Int {
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

    fun sc(env: Environment) {
        for (j in 0 until 4) {
            for (point in points) {
                val collision = env.collision(point.pos, point.prev)
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

    fun setForce(value: Vector) {
        middle.force = value
        for (point in points)
            point.force = value
    }

    fun addForce(force: Vector) {
        middle.addForce(force)
        for (point in points)
            point.addForce(force)

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

//    fun drawEars(canvas: Canvas, scaleFactor: Float) {}

    fun drawEyesOpen(canvas: Canvas) {
        paint.strokeWidth = 1.0f

        val r1 = 0.12f * radius
        val left1 = 0.35f * radius
        val right1 = 0.65f * radius
        val y1 = 0.30f * radius

        paint.color = Color.WHITE
        paint.style = Paint.Style.FILL
        canvas.drawCircle(left1, y1, r1, paint)
        canvas.drawCircle(right1, y1, r1, paint)

        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        canvas.drawCircle(left1, y1, r1, paint)
        canvas.drawCircle(right1, y1, r1, paint)

        val r2 = 0.06f * radius
        val left2 = 0.35f * radius
        val right2 = 0.65f * radius
        val y2 = 0.33f * radius

        paint.style = Paint.Style.FILL
        canvas.drawCircle(left2, y2, r2, paint)
        canvas.drawCircle(right2, y2, r2, paint)
    }

    fun drawEyesClosed(canvas: Canvas) {
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.0f

        val r = 0.12f * radius
        val left = 0.35f * radius
        val right = 0.65f * radius
        val y = 0.30f * radius

        canvas.drawCircle(left, y, r, paint)
        canvas.drawLine(left - r, y, left + r, y, paint)

        canvas.drawCircle(right, y, r, paint)
        canvas.drawLine(right - r, y, right + r, y, paint)
    }

    fun drawSmile(canvas: Canvas, paint: Paint) {
        val left = 0.25f * radius
        val top = 0.40f * radius
        val right = 0.50f * radius + left
        val bottom = 0.50f * radius + top

        val oval = RectF(left, top, right, bottom)
        canvas.drawArc(oval, 0f, 180f, false, paint)
    }

    fun drawOohFace(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 2.0f
        drawSmile(canvas, paint)

        val x1 = 0.25f * radius
        val x2 = 0.45f * radius
        val x3 = 0.75f * radius
        val x4 = 0.55f * radius
        val y1 = 0.20f * radius
        val y2 = 0.30f * radius
        val y3 = 0.40f * radius

        canvas.drawLine(x1, y1, x2, y2, paint)
        canvas.drawLine(x2, y2, x1, y3, paint)
        canvas.drawLine(x3, y1, x4, y2, paint)
        canvas.drawLine(x4, y2, x3, y3, paint)
    }

    fun updateFace() {
        if (face == Face.SMILE && Random.nextDouble() < 0.025)
            face = Face.OPEN
        else if (face == Face.OPEN && Random.nextDouble() < 0.05)
            face = Face.SMILE

        if (eyes == Eye.OPEN && Random.nextDouble() < 0.010)
            eyes = Eye.CLOSED
        else if (eyes == Eye.CLOSED && Random.nextDouble() < 0.150)
            eyes = Eye.OPEN
    }

    fun drawFace(canvas: Canvas) {
        if (middle.velocity > 200.0f)
            drawOohFace(canvas)
        else {
            paint.style = if (face == Face.SMILE) Paint.Style.STROKE else Paint.Style.FILL
            paint.strokeWidth = 2.0f
            drawSmile(canvas, paint)

            if (eyes == Eye.OPEN) {
                drawEyesOpen(canvas)
            } else {
                drawEyesClosed(canvas)
            }
        }
    }

    fun drawBody(canvas: Canvas) {

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3.0f
        val path = Path()
        val startX = points[0].xPos
        val startY = points[0].yPos
        path.moveTo(startX, startY)
        for (i in points.indices) {
            val prevPointMass = points[pointMassIndex(i - 1)]
            val currentPointMass = points[pointMassIndex(i)]
            val nextPointMass = points[pointMassIndex(i + 1)]
            val nextNextPointMass = points[pointMassIndex(i + 2)]

            val cx = currentPointMass.xPos
            val cy = currentPointMass.yPos
            val tx = nextPointMass.xPos
            val ty = nextPointMass.yPos
            val nx = cx - prevPointMass.xPos + tx - nextNextPointMass.xPos
            val ny = cy - prevPointMass.yPos + ty - nextNextPointMass.yPos
            val px = cx * 0.5f + tx * 0.5f + nx * 0.16f
            val py = cy * 0.5f + ty * 0.5f + ny * 0.16f

            path.cubicTo(px, py, tx, ty, tx, ty)
        }

        canvas.drawPath(path, paint)

//        for (i in points.indices) {
//            val point = points[i]
//            val r2 = 0.06f * radius * point.mass
//            paint.style = Paint.Style.STROKE
//            canvas.drawCircle(point.xPos, point.yPos, r2, paint)
//
//            val force = point.force
//            val x = point.xPos + 1e5f * force.x * point.mass
//            val y = point.yPos + 1e5f * force.y * point.mass
//            canvas.drawLine(point.xPos, point.yPos, x, y, paint)
//        }
    }

    fun draw(canvas: Canvas) {
        updateFace()
        canvas.save()
        // canvas.scale(scaleFactor, scaleFactor)
        drawBody(canvas)

        val up = Vector(0.0f, -1.0f)
        val ori = points[0].pos - middle.pos
        val ang = Math.acos(ori.dotProd(up) / ori.length.toDouble())
        val radians = if (ori.x < 0.0f) -ang else ang
        val theta = (180.0 / Math.PI) * radians
        canvas.rotate(theta.toFloat(), middle.xPos, middle.yPos)

        val tx = middle.xPos - radius / 2f
        val ty = (middle.yPos - 0.35f * radius) - radius / 2f
        canvas.translate(tx, ty)
        drawFace(canvas)
        canvas.restore()
    }
}