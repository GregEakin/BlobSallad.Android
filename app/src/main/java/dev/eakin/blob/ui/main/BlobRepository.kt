package dev.eakin.blob.ui.main

interface BlobRepository {
    fun createBlobs(): MutableList<Blob>
    fun createBlob(motherBlob: Blob): Blob
    val maxNum: Int
}

class BlobRepositoryImpl(val startX: Float, val startY: Float, val maxBlobs: Int) : BlobRepository {

    init {
        if (maxBlobs < 1)
            throw Exception("Need to allow at least one blob in the collective.")
    }

    private val blobPointCount = 8
    private val blobInitialRadius = 200.0f

    override fun createBlobs(): MutableList<Blob> {
        val list = mutableListOf<Blob>()
        val blob = Blob(startX, startY, blobInitialRadius, blobPointCount)
        list.add(blob)
        return list
    }

    override fun createBlob(motherBlob: Blob): Blob {
        return Blob(motherBlob)
    }

    override val maxNum: Int
        get() = maxBlobs
}

