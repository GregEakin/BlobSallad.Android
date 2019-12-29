package dev.eakin.blob

import dev.eakin.blob.ui.main.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

val appModule = module {
    single<Random> { Random() }

    single<Environment> { EnvironmentImpl(0f, 0f, 700f, 700f) }

    single<BlobCollective> { BlobCollectiveImpl(maxBlobs) }

    factory { createBlobList(200.0f, 200.0f) }

    factory { (mother: Blob) -> createBlob(mother) }

    scope(named<MainActivity>()) {
        scoped { MainView(get()) }
    }

    // viewModel { MainViewModel() }
}

val blobPointCount = 8
val blobInitialRadius = 200.0f
val maxBlobs = 64

fun createBlobList(startX: Float, startY: Float): MutableList<Blob> {
    val list = mutableListOf<Blob>()
    val blob = Blob(startX, startY, blobInitialRadius, blobPointCount)
    list.add(blob)
    return list
}

fun createBlob(motherBlob: Blob): Blob {
    return Blob(motherBlob)
}
