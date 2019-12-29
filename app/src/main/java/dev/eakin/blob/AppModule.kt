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

package dev.eakin.blob

import dev.eakin.blob.ui.main.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

const val blobPointCount = 8
const val blobInitialRadius = 200.0f
const val maxBlobs = 64
const val startX = 200.0f
const val startY = 200.0f
const val envX = 0.0f
const val envY = 0.0f
const val envW = 700.0f
const val envH = 700.0f

val appModule = module {
    single<Random> { Random() }

    single<Environment> { EnvironmentImpl(envX, envY, envW, envH) }

    single<BlobCollective> { BlobCollectiveImpl(maxBlobs) }

    factory { createBlobList() }

    factory { (mother: Blob) -> splitBlob(mother) }

    scope(named<MainActivity>()) {
        scoped { MainView(get()) }
    }

    // viewModel { MainViewModel() }
}

fun createBlobList(): MutableList<Blob> {
    val list = mutableListOf<Blob>()
    val blob = BlobImpl(startX, startY, blobInitialRadius, blobPointCount)
    list.add(blob)
    return list
}

fun splitBlob(mother: Blob): Blob {
    return BlobImpl(mother)
}
