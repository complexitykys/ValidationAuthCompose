package test.auth.testauth.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import test.auth.testauth.authscreen.AuthScreenViewModel
import test.auth.testauth.authscreen.utils.StringResourceManager
import test.auth.testauth.authscreen.utils.StringResourceManagerImpl

val appModule = module {
    single<StringResourceManager>{ StringResourceManagerImpl(androidContext())}
    viewModel { AuthScreenViewModel(get()) }
}
