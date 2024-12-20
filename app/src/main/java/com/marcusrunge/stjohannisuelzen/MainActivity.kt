package com.marcusrunge.stjohannisuelzen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.marcusrunge.stjohannisuelzen.core.enums.Swipe
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.core.interfaces.OnSwipeListener
import com.marcusrunge.stjohannisuelzen.databinding.MainActivityBinding
import com.marcusrunge.stjohannisuelzen.models.LinkButton
import com.marcusrunge.stjohannisuelzen.notification.interfaces.Notification
import com.marcusrunge.stjohannisuelzen.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    TabLayout.OnTabSelectedListener, OnSwipeListener {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var tabLayout: TabLayout
    private lateinit var linkButtons: Array<LinkButton>

    @Inject
    lateinit var core: Core

    @Inject
    lateinit var notification: Notification

    /*@Inject
    lateinit var newsFeed: NewsFeed*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    remoteConfig.fetchAndActivate()
                        .addOnCompleteListener { fetchTask ->
                            if (fetchTask.isSuccessful) {
                                core.apiKeys.googleMaps = remoteConfig.getString("MAPS_API_KEY")
                                core.apiKeys.youtube = remoteConfig.getString("YOUTUBE_API_KEY")
                            }
                        }
                }
            }
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val themeValues = resources.getStringArray(R.array.theme_values)
        val theme = sharedPref.getString(getString(R.string.key_theme), themeValues[0])
        if (theme != null) {
            ThemeUtils.setTheme(themeValues, theme)
        }

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_activity) as NavHostFragment).navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_web, R.id.navigation_media, R.id.navigation_map
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        OssLicensesMenuActivity.setActivityTitle(getString(R.string.custom_license_title))
        createLinkButtonsArray()
        navController.addOnDestinationChangedListener(this)
        if (navController.currentDestination?.id == R.id.navigation_web)
            setLinkButtonsActionBar()

        if (sharedPref.getBoolean(getString(R.string.key_dailymotto_pushnotifications), false))
            notification.schedule.startRecurringDailyMotto()
        else
            notification.schedule.stopRecurringDailyMotto()

        //core.gestures.swipe.addOnSwipeListener(this)
        getLocationPermission()

        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                core.back.app.onBackPressed { finish() }
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this /* lifecycle owner */,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        core.back.app.onBackPressed { finish() }
                    }
                })
        }
        /*CoroutineScope(Dispatchers.IO).launch {
            val pair = newsFeed.content.parseAsync(getString(R.string.url_stjohannis_uelzen))
            val first = pair.first;
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            navController.currentDestination?.id -> return false
            else -> return when (item.itemId) {
                R.id.navigation_settings -> {
                    navController.navigate(R.id.navigation_settings)
                    true
                }

                R.id.navigation_privacy -> {
                    navController.navigate(R.id.navigation_privacy)
                    true
                }

                R.id.navigation_licenses -> {
                    startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                    true
                }

                R.id.navigation_terms -> {
                    navController.navigate(R.id.navigation_terms)
                    true
                }

                android.R.id.home -> {
                    navController.popBackStack()
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.navigation_web) {
            if (core.back.web.isWebViewActive) {
                selectTab(getString(R.string.url_stjohannis_uelzen))
            } else {
                core.back.web.isWebViewActive = true
                setLinkButtonsActionBar()
            }
        } else {
            core.back.web.isWebViewActive = false
            supportActionBar?.setDisplayShowCustomEnabled(false)
            supportActionBar?.setDisplayShowTitleEnabled(true)
            supportActionBar?.title = destination.label
            if (destination.id == R.id.navigation_calendar) {
                supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
            }
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        core.webNavigation.requestNavigateTo(
            tab?.tag as String
        )
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        //TODO("Not yet implemented")
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        //TODO("Not yet implemented")
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(this)
        core.gestures.swipe.removeOnSwipeListener(this)
        super.onDestroy()
    }

    override fun onSwipe(swipe: Swipe, value: Int) {
        when (swipe) {
            Swipe.Left -> {
                tabLayout.getTabAt(tabLayout.selectedTabPosition + 1)?.select()
            }

            Swipe.Right -> {
                tabLayout.getTabAt(tabLayout.selectedTabPosition - 1)?.select()
            }
        }
    }

    private fun createLinkButtonsArray() {
        linkButtons = arrayOf(
            LinkButton(getString(R.string.current), getString(R.string.url_stjohannis_uelzen)),
            //LinkButton(getString(R.string.churchservices), getString(R.string.url_churchservices)),
            //LinkButton(getString(R.string.solution), getString(R.string.url_solution)),
            LinkButton(getString(R.string.thoughts), getString(R.string.url_thoughts)),
            //LinkButton(getString(R.string.sermon), getString(R.string.url_sermon)),
            LinkButton(getString(R.string.groups), getString(R.string.url_groups)),
            LinkButton(getString(R.string.churchinlife), getString(R.string.url_churchinlife)),
            //LinkButton(getString(R.string.happy_hour), getString(R.string.url_happy_hour)),
            LinkButton(getString(R.string.gallery), getString(R.string.url_gallery)),
            //LinkButton(getString(R.string.mission), getString(R.string.url_mission)),
            LinkButton(getString(R.string.history), getString(R.string.url_history)),
            //LinkButton(getString(R.string.confirmands), getString(R.string.url_confirmands)),
            //LinkButton(getString(R.string.environment), getString(R.string.url_environment)),
            LinkButton(getString(R.string.foundation), getString(R.string.url_foundation)),
            //LinkButton(getString(R.string.volunteering), getString(R.string.url_volunteering)),
            //LinkButton(getString(R.string.board), getString(R.string.url_board)),
            //LinkButton(getString(R.string.donations), getString(R.string.url_donations)),
            //LinkButton(getString(R.string.newsletter), getString(R.string.url_newsletter))
        )
    }

    private fun setLinkButtonsActionBar() {
        val linkbuttonsLayout: View = layoutInflater.inflate(R.layout.linkbuttons_layout, null)
        tabLayout = linkbuttonsLayout.findViewById(R.id.linkbuttons_tabLayout)
        linkButtons.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it.text).setTag(it.url))
        }
        tabLayout.addOnTabSelectedListener(this)
        supportActionBar?.customView = linkbuttonsLayout
        supportActionBar?.displayOptions =
            (ActionBar.DISPLAY_SHOW_CUSTOM or ActionBar.DISPLAY_SHOW_HOME)
    }

    private fun selectTab(url: String) {
        if (::tabLayout.isInitialized) {
            for (i in linkButtons.indices) {
                if (linkButtons[i].url == url) {
                    tabLayout.getTabAt(i)?.select()
                    break
                }
            }
        }
    }

    private val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    private var locationPermissionGranted = false

    /**
     * Prompts the user for permission to use the device location.
     */
    // [START maps_current_place_location_permission]
    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
    // [END maps_current_place_location_permission]

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                }
            }
        }
    }
}