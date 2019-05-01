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

package dev.eakin.blob.ui.main

import android.graphics.Canvas
import kotlin.random.Random

class BlobCollective(val x: Float, val y: Float, val maxNum: Int) {
    init {
        if (maxNum < 1)
            throw Exception("Need to allow at least one blob in the collective.")
    }

    private val blobPointCount = 8
    private val blobInitialRadius = 200.0f

    private val blobs = createBlobs()
    private fun createBlobs(): MutableList<Blob> {
        val list = mutableListOf<Blob>()
        val blob = Blob(x, y, blobInitialRadius, blobPointCount)
        list.add(blob)
        return list
    }

    val numActive: Int
        get() = blobs.size

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
        //Log.d("BlobAndroid", "New blob ${blobs.size}")
        //newBlob
    }

    fun join() {
        if (numActive <= 1)
            return

        val smallest = findSmallest(null) ?: return
        val closest = findClosest(smallest) ?: return

        val r1 = smallest.radius.toDouble()
        val r2 = closest.radius.toDouble()
        val length = Math.sqrt(r1 * r1 + r2 * r2)
        val factor = 0.945 * length / closest.radius
        closest.scale(factor.toFloat())

        blobs.remove(smallest)
        for (blob in blobs)
            blob.unlinkBlob(smallest)

        //Log.d("BlobAndroid", "Delete blob ${blobs.size}")
        //xsmallest
    }

    fun findLargest(exclude: Blob?): Blob? {
        var maxRadius = Float.MIN_VALUE
        var largest: Blob? = null
        for (blob in blobs) {
            if (blob === exclude)
                continue

            if (blob.radius <= maxRadius)
                continue

            maxRadius = blob.radius
            largest = blob
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

            minRadius = blob.radius
            smallest = blob
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

            minDistance = distance
            closest = blob
        }

        return closest
    }

    fun findClosest(x: Float, y: Float, radius: Float): Blob? {
        var minDistance = Float.MAX_VALUE
        var closest: Blob? = null
        for (blob in blobs) {
            val aXbX = x - blob.x
            val aYbY = y - blob.y
            val distance = aXbX * aXbX + aYbY * aYbY
            if (distance >= minDistance)
                continue

            val hit = blob.radius + radius
            if (distance > hit * hit)
                continue

            minDistance = distance
            closest = blob
        }

        return closest
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
            val force = if (blob.selected?.blob === blob) Vector(0.0f, 0.0f) else value
            blob.setForce(force)
        }
    }

    fun addForce(force: Vector) {
        for (blob in blobs) {
            if (blob.selected?.blob === blob)
                continue

            val x = force.x * (Random.nextFloat() * 0.75f + 0.25f)
            val y = force.y * (Random.nextFloat() * 0.75f + 0.25f)
            val newForce = Vector(x, y)
            blob.addForce(newForce)
        }
    }

    fun draw(canvas: Canvas) {
        for (blob in blobs)
            blob.draw(canvas)
    }
}
