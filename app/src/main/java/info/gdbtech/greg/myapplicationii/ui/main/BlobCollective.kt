package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Canvas
import android.graphics.Point
import android.util.Log
import kotlin.random.Random

class BlobCollective(val x: Float, val y: Float, val maxNum: Int) {
    private val blobPointMass = 8
    private val blobInitialRadius = 100.0f

    private val blobs = createBlobs()
    private fun createBlobs(): MutableList<Blob> {
        val list = mutableListOf<Blob>()
        val blob = Blob(x, y, blobInitialRadius, blobPointMass)
        list.add(blob)
        return list
    }

    val numActive: Int
        get() = blobs.size

    var selectedBlob: Blob? = null

    fun split() {
        if (numActive >= maxNum)
            return

        val motherBlob = findLargest(null) ?: return
        motherBlob.scale(0.75f)
        val newBlob = Blob(motherBlob)
        for (blob in blobs) {
            blob.linkBlob(newBlob)
            newBlob.linkBlob(blob)
        }

        blobs.add(newBlob)
        Log.d("BlobAndroid", "New blob ${blobs.size}")
    }

    fun join() {
        if (numActive <= 1)
            return

        val smallest = findSmallest(null) ?: return
        val closest = findClosest(smallest) ?: return

        val length = Math.sqrt((smallest.radius * smallest.radius + closest.radius * closest.radius).toDouble())
        closest.scale((0.945 * length / closest.radius).toFloat())

        blobs.remove(smallest)
        for (blob in blobs)
            blob.unlinkBlob(smallest)

        Log.d("BlobAndroid", "Delete blob ${blobs.size}")
    }

    fun findLargest(exclude: Blob?): Blob? {
        var maxRadius = Float.MIN_VALUE
        var largest: Blob? = null
        for (blob in blobs) {
            if (blob === exclude)
                continue

            if (blob.radius <= maxRadius)
                continue

            largest = blob
            maxRadius = blob.radius
        }

        return largest
    }

    fun findSmallest(exclude: Blob?): Blob? {
        var minRadius = Float.MAX_VALUE
        var smallest: Blob? = null
        for (blob in blobs) {
            if (blob === exclude)
                continue

            if (blob.radius >= minRadius)
                continue

            smallest = blob
            minRadius = blob.radius
        }

        return smallest
    }

    fun findClosest(neighbor: Blob): Blob? {
        var minDistance = Float.MAX_VALUE
        var closest: Blob? = null
        for (blob in blobs) {
            if (blob === neighbor)
                continue

            val aXbX = neighbor.x - blob.x
            val aYbY = neighbor.y - blob.y
            val distance = aXbX * aXbX + aYbY * aYbY
            if (distance >= minDistance)
                continue

            closest = blob
            minDistance = distance
        }

        return closest
    }

    fun findClosest(x: Double, y: Double): Point? {
        if (selectedBlob != null)
            return null

        var minDistance = Double.MAX_VALUE
        var selectOffset: Point? = null
        var closestBlob: Blob? = null
        for (blob in blobs) {
            val aXbX = x - blob.x
            val aYbY = y - blob.y
            val distance = aXbX * aXbX + aYbY * aYbY
            if (distance >= minDistance)
                continue

            minDistance = distance
            if (distance >= blob.radius / 2.0)
                continue

            selectOffset = Point(aXbX.toInt(), aYbY.toInt())
            closestBlob = blob
        }

        if (closestBlob != null)
            closestBlob.selected = true

        selectedBlob = closestBlob
        return selectOffset
    }

    fun unselectBlob() {
        val blob = selectedBlob ?: return

        blob.selected = false
        selectedBlob = null
    }

    fun selectedBlobMoveTo(x: Float, y: Float) {
        selectedBlob!!.moveTo(x, y)
    }

    fun move(dt: Float) {
        for (blob in blobs)
            blob.move(dt)
    }

    fun sc(env: Environment) {
        for (blob in blobs)
            blob.sc(env)
    }

    fun setForce(value: Vector) {
        for (blob in blobs) {
            val force = if (blob === selectedBlob) Vector(0.0f, 0.0f) else value
            blob.setForce(force)
        }
    }

    fun addForce(force: Vector) {
        for (blob in blobs) {
            if (blob === selectedBlob)
                continue

            val x = force.x * (Random.nextFloat() * 0.75f + 0.25f)
            val y = force.y * (Random.nextFloat() * 0.75f + 0.25f)
            val newForce = Vector(x, y)
            blob.addForce(newForce)
        }
    }

    fun draw(canvas: Canvas, scaleFactor: Float) {
        for (blob in blobs)
            blob.draw(canvas, scaleFactor)
    }
}