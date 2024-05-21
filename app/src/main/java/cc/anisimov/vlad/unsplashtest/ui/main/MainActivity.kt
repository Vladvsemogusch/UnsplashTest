package cc.anisimov.vlad.unsplashtest.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.anisimov.vlad.unsplashtest.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}