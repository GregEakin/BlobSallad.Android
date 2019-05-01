package dev.eakin.blobandroid.ui.main

import dev.eakin.blobandroid.ui.main.PointMass
import dev.eakin.blobandroid.ui.main.Skin
import dev.eakin.blobandroid.ui.main.Vector
import org.junit.Assert.assertEquals
import org.junit.Test

class SkinTests {
    @Test
    fun ctorTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val skin = Skin(pointMassA, pointMassB)

        assertEquals(5.0f, skin.length, 0.01f)
        assertEquals(25.0f, skin.lengthSquared, 0.01f)
    }

    @Test
    fun scaleTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 0.0f)

        val skin = Skin(pointMassA, pointMassB)
        skin.scale(10.0f)

        assertEquals(50.0f, skin.length, 0.01f)
        assertEquals(2500.0f, skin.lengthSquared, 0.01f)
    }

    @Test
    fun scMovePositionTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 1.0f)

        val skin = Skin(pointMassA, pointMassB)
        pointMassB.force = Vector(1.0f, 1.0f)
        pointMassB.move(1.0f)
        skin.sc()

        assertEquals(1.4848485f, pointMassA.xPos, 0.01f)
        assertEquals(3.6060605f, pointMassA.yPos, 0.01f)
        assertEquals(4.5151515f, pointMassB.xPos, 0.01f)
        assertEquals(7.3939395f, pointMassB.yPos, 0.01f)
    }

    @Test
    fun scMoveLengthTest() {
        val cxA = 1.0f
        val cyA = 3.0f
        val pointMassA = PointMass(cxA, cyA, 0.0f)

        val cxB = 4.0f
        val cyB = 7.0f
        val pointMassB = PointMass(cxB, cyB, 1.0f)

        val skin = Skin(pointMassA, pointMassB)
        pointMassB.force = Vector(1.0f, 1.0f)
        pointMassB.move(1.0f)
        skin.sc()

        val aXbX = pointMassA.xPos - pointMassB.xPos
        val aYbY = pointMassA.yPos - pointMassB.yPos
        val lengthSquared = aXbX * aXbX + aYbY * aYbY
        val length = Math.sqrt(lengthSquared.toDouble()).toFloat()

        assertEquals(4.850852f, length, 0.01f)
        assertEquals(23.530764f, lengthSquared, 0.01f)
    }
}