package com.example.projet_tdm

import android.app.Application
import com.example.projet_tdm.models.PannierSingleton

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PannierSingleton.initialize(this)
    }
}