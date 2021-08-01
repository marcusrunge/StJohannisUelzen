package com.marcusrunge.stjohannisuelzen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcusrunge.stjohannisuelzen.ui.web.WebFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, WebFragment.newInstance())
                .commitNow()
        }
    }
}