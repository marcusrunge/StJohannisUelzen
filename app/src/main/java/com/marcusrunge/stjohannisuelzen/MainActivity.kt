package com.marcusrunge.stjohannisuelzen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.databinding.MainActivityBinding
import com.marcusrunge.stjohannisuelzen.interfaces.Navigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    @Inject
    lateinit var core: Core

    @Inject
    lateinit var navigation: Navigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigation.activate()

        val navView: BottomNavigationView = binding.navView

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_web, R.id.navigation_media, R.id.navigation_counseling
            )
        )
        setupActionBarWithNavController(navigation.navController, appBarConfiguration)
        navView.setupWithNavController(navigation.navController)
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.custom_license_title))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_settings -> {
                navigation.navController.navigate(R.id.navigation_settings)
                true
            }
            R.id.navigation_privacy -> {
                navigation.navController.navigate(R.id.navigation_privacy)
                true
            }
            R.id.navigation_licenses -> {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                true
            }
            R.id.navigation_terms -> {
                navigation.navController.navigate(R.id.navigation_terms)
                true
            }
            //TODO: -> {
            // navigation.navigateBack()
            // true
            // }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        core.back.publisher.onBack { super.onBackPressed() }
    }
}