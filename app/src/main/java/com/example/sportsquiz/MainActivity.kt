package com.example.sportsquiz

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.game_screen_ui.di.GameScreenComponentDependenciesStore
import com.example.main_screen_ui.di.MainScreenComponentDependenciesStore
import com.example.sportsquiz.di.AppComponent
import com.example.sportsquiz.di.DaggerAppComponent
import com.example.wallpapers_screen_ui.di.WallpapersScreenComponentDependenciesStore

class MainApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .context(this)
            .build()

        GameScreenComponentDependenciesStore.dependencies = appComponent
        MainScreenComponentDependenciesStore.dependencies = appComponent
        WallpapersScreenComponentDependenciesStore.dependencies = appComponent
    }
}

//TODO возможно не понадобится
val Context.appComponent: AppComponent
    get() = when (this) {
        is MainApp -> appComponent
        else -> this.applicationContext.appComponent
    }

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}