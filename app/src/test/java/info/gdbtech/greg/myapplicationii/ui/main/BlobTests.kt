package info.gdbtech.greg.myapplicationii.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

class BlobTests {
    @Test
    fun ctorTest() {
        val blob = Blob(71.0f, 67.0f, 11.0f, 5)
        assertEquals(71.0f, blob.x, 0.001f)
        assertEquals(67.0f, blob.y, 0.001f)
        assertEquals(11.0f, blob.radius, 0.001f)
        assertEquals(1.0f, blob.mass, 0.001f)

    }

    @Test
    fun pointMass_test() {
        val blob = Blob(71.0f, 67.0f, 11.0f, 5)
        for (i in 0..4) {
            val point = blob.points[i]

            val mass = if (i < 2) 4.0f else 1.0f
            assertEquals(mass, point.mass, 0.001f)

            val theta = i.toDouble() * 2.0 * Math.PI / 5
            val cx = Math.cos(theta) * 11.0 + 71.0
            val cy = Math.sin(theta) * 11.0 + 67.0
            assertEquals(cx.toFloat(), point.xPos, 0.01f)
            assertEquals(cy.toFloat(), point.yPos, 0.01f)
        }
    }

    @Test
    fun add_blob_test() {
        val blob1 = Blob(17.0f, 19.0f, 11.0f, 0)
        val blob2 = Blob(59.0f, 61.0f, 13.0f, 0)
        blob1.linkBlob(blob2)

        assertEquals(0, blob2.collisions.size)
        assertEquals(1, blob1.collisions.size)
        val collision = blob1.collisions[0]
        assertEquals(22.800f, collision.shortLimit, 0.01f)
    }

    @Test
    fun removeBlobTest() {
        val blob1 = Blob(17.0f, 19.0f, 11.0f, 0)
        val blob2 = Blob(59.0f, 61.0f, 13.0f, 0)
        blob1.linkBlob(blob2)
        blob1.unlinkBlob(blob2)

        assertEquals(0, blob2.bones.size)
        assertEquals(0, blob1.bones.size)
    }
}