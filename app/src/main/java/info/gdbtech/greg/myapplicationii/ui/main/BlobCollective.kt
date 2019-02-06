package info.gdbtech.greg.myapplicationii.ui.main

import android.graphics.Point
import kotlin.random.Random

class BlobCollective(val x: Double, val y: Double, val maxNum: Int) {
    private val blobPointMass = 8
    private val blobInitialRadius = 0.4

    val blobs = createBlobs()
    fun createBlobs(): MutableList<Blob> {
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

        val motherBlob = findLargest(null)
        if (motherBlob == null)
            return;

        motherBlob.scale(0.75)
        val newBlob = Blob(motherBlob)
        for (blob in blobs) {
            blob.linkBlob(newBlob)
            newBlob.linkBlob(blob)
        }

        blobs.add(newBlob)
    }

    fun join() {
        if (numActive <= 1)
            return

        val smallest = findSmallest(null)
        if (smallest == null)
            return
        val closest = findClosest(smallest)
        if (closest == null)
            return

        val length = Math.sqrt(smallest.radius * smallest.radius + closest.radius * closest.radius)
        closest.scale(0.945 * length / closest.radius)

        blobs.remove(smallest)
        for (blob in blobs)
            blob.unlinkBlob(smallest)
    }

    fun findLargest(exclude: Blob?): Blob? {
        var maxRadius = Double.MIN_VALUE
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
        var minRadius = Double.MAX_VALUE
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
        var minDistance = Double.MAX_VALUE
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

    fun selectedBlobMoveTo(x: Double, y: Double) {
        selectedBlob!!.moveTo(x, y)
    }

    fun move(dt: Double) {
        for (blob in blobs)
            blob.move(dt)
    }

    fun sc(env: Environment) {
        for (blob in blobs)
            blob.Sc(env)
    }

    var force: Vector
        get() {
            return Vector(0.0, 0.0)
        }
        set(value) {
            for (blob in blobs) {
                val force = if (blob === selectedBlob) Vector(0.0, 0.0) else value
                blob.force = force
            }
        }

    fun addForce(force: Vector) {
        for (blob in blobs) {
            if (blob === selectedBlob)
                continue

            val x = force.x * (Random.nextDouble() * 0.75 + 0.25)
            val y = force.y * (Random.nextDouble() * 0.75 + 0.25)
            val newForce = Vector(x, y)
            blob.addForce(newForce)
        }
    }

    // draw...
}