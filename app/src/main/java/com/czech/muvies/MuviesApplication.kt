package com.czech.muvies

import android.app.Application
import timber.log.Timber

class MuviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
