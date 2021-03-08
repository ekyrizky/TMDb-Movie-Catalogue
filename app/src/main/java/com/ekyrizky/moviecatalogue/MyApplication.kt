package com.ekyrizky.moviecatalogue

import android.app.Application
import com.ekyrizky.moviecatalogue.core.di.CoreComponent
import com.ekyrizky.moviecatalogue.core.di.DaggerCoreComponent
import com.ekyrizky.moviecatalogue.di.AppComponent
import com.ekyrizky.moviecatalogue.di.DaggerAppComponent

open class MyApplication : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(applicationContext)
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(coreComponent)
    }
}