package dev.eakin.blob

import dev.eakin.blob.ui.main.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.*

val appModule = module {
    single<Random> { Random() }

    single<Environment> { EnvironmentImpl(0f, 0f, 700f, 700f) }

    single<BlobRepository> { BlobRepositoryImpl(200f, 200f, 64) }

    single<BlobCollective> { BlobCollectiveImpl(get()) }

    // factory { Blob(0f, 0f, 10f, 3) }
    // factory { (activity: Context) -> MainViewModel(activity) }

    scope(named<MainActivity>()) {
        // scope(named<BlobView>(get(),get(),get()))
    }

    viewModel { MainViewModel() }
}
