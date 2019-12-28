package dev.eakin.blob.ui.main

import org.junit.Assert.*
import org.junit.Test

class BlobCollectiveTests {
    @Test
    fun ctorTest() {
        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)

        assertEquals(4, blobRepository.maxNum)
        assertEquals(1, collective.numActive)
    }

    @Test
    fun splitTest() {
        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)
        collective.split()
        assertEquals(2, collective.numActive)
    }

    @Test
    fun joinTest() {
        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)
        collective.split()
        collective.join()
        assertEquals(1, collective.numActive)
    }

    @Test
    fun findLargestTest() {
        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)
        collective.split()
        collective.split()

        val motherBlob = collective.findLargest(null)
        assertEquals(150.0f, motherBlob!!.radius, 0.001f)
    }

    @Test
    fun findSmallestTest() {
        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)
        collective.split()
        collective.split()

        val largest = collective.findLargest(null)

        // find one of the two smallest blobs
        val smallest1 = collective.findSmallest(null)
        assertNotSame(largest, smallest1)
        assertEquals(112.5f, smallest1!!.radius, 0.001f)

        // find the other smallest blob
        val smallest2 = collective.findSmallest(smallest1)
        assertNotSame(largest, smallest2)
        assertNotSame(smallest1, smallest2)
        assertEquals(112.5f, smallest2!!.radius, 0.001f)
    }

    @Test
    fun findClosestTest() {
        val environment = EnvironmentImpl(0.2f, 0.2f, 2.6f, 1.6f)

        val blobRepository = BlobRepositoryImpl(1.0f, 1.0f, 4)
        val collective = BlobCollectiveImpl(blobRepository)
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
}