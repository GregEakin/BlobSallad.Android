package info.gdbtech.greg.myapplicationii.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test

public class BlobTests {
    @Test
    fun ctorTest() {
        val blob = Blob(71.0, 67.0, 11.0, 5)
        assertEquals(71.0, blob.x, 0.001)
        assertEquals(67.0, blob.y, 0.001)
        assertEquals(11.0, blob.radius, 0.001)
        assertEquals(1.0, blob.mass, 0.001)

    }

    @Test
    fun pointMass_test() {
        val blob = Blob(71.0, 67.0, 11.0, 5)
        for (i in 0..4) {
            val point = blob.points[i]

            val mass = if (i < 2) 4.0 else 1.0
            assertEquals(mass, point.mass, 0.001)

            val theta = i.toDouble() * 2.0 * Math.PI / 5
            val cx = Math.cos(theta) * 11.0 + 71.0
            val cy = Math.sin(theta) * 11.0 + 67.0
            assertEquals(cx, point.xPos, 0.01)
            assertEquals(cy, point.yPos, 0.01)
        }
    }

    @Test
    fun add_blob_test() {
        val blob1 = Blob(17.0, 19.0, 11.0, 0)
        val blob2 = Blob(59.0, 61.0, 13.0, 0)
        blob1.linkBlob(blob2)

        assertEquals(0, blob2.collisions.size);
        assertEquals(1, blob1.collisions.size);
        val collision = blob1.collisions[0];
        assertEquals(22.800, collision.shortLimit, 0.01);
        assertEquals(Double.POSITIVE_INFINITY, collision.longLimit, 0.001);
    }

    @Test
    fun removeBlobTest() {
        val blob1 = Blob(17.0, 19.0, 11.0, 0)
        val blob2 = Blob(59.0, 61.0, 13.0, 0)
        blob1.linkBlob(blob2)
        blob1.unlinkBlob(blob2)

        assertEquals(0, blob2.bones.size)
        assertEquals(0, blob1.bones.size)
    }
}