package info.gdbtech.greg.myapplicationii.ui.main

import org.junit.Assert.*
import org.junit.Test

public class BlobCollectiveTests {
    @Test
    fun ctorTest() {
        val collective = BlobCollective(71.0f, 67.0f, 4)

        assertEquals(4, collective.maxNum)
        assertEquals(1, collective.numActive)
    }

    @Test
    fun splitTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.split()
        assertEquals(2, collective.numActive)
    }

    @Test
    fun joinTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4);
        collective.split();
        collective.join();
        assertEquals(1, collective.numActive);
    }

    @Test
    fun findLargestTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.split()
        collective.split()

        val motherBlob = collective.findLargest(null)
        assertEquals(75.0f, motherBlob!!.radius, 0.001f)
    }

    @Test
    fun findSmallestTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.split()
        collective.split()

        val largest = collective.findLargest(null)

        // find one of the two smallest blobs
        val smallest1 = collective.findSmallest(null)
        assertNotSame(largest, smallest1)
        assertEquals(56.25f, smallest1!!.radius, 0.001f)

        // find the other smallest blob
        val smallest2 = collective.findSmallest(smallest1)
        assertNotSame(largest, smallest2)
        assertNotSame(smallest1, smallest2)
        assertEquals(56.25f, smallest2!!.radius, 0.001f)
    }

    @Test
    fun findClosestTest() {
        val environment = Environment(0.2f, 0.2f, 2.6f, 1.6f)

        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.split()
        collective.split()

        collective.move(1.0f)
        collective.sc(environment)

        val largest = collective.findLargest(null)
        val smallest1 = collective.findSmallest(null)
        val smallest2 = collective.findSmallest(smallest1)

        val closest = collective.findClosest(largest!!)
        assertSame(smallest2, closest)
    }

    @Test
    fun selectBlobMissTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.findClosest(2.0, 2.0)
        assertNull(collective.selectedBlob)
    }

    @Test
    fun selectBlobHitTest() {
        val collective = BlobCollective(1.0f, 1.0f, 4)
        collective.findClosest(1.0, 1.1)
        assertNotNull(collective.selectedBlob)
        assertTrue(collective.selectedBlob!!.selected)
    }
}