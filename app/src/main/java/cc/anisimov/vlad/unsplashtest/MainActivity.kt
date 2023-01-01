package cc.anisimov.vlad.unsplashtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cc.anisimov.vlad.unsplashtest.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}