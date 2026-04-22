package com.marcusrunge.stjohannisuelzen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
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

/**
 * The main activity of the application.
 *
 * This activity serves as the main entry point for the user interface. It hosts the
 * navigation component, manages the bottom navigation view, the action bar, and handles
 * various user interactions and system events.
 *
 * It implements:
 * - [NavController.OnDestinationChangedListener] to react to navigation events.
 * - [TabLayout.OnTabSelectedListener] to handle tab selections in the custom action bar.
 * - [OnSwipeListener] to respond to swipe gestures.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener,
    TabLayout.OnTabSelectedListener, OnSwipeListener {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var tabLayout: TabLayout
    private lateinit var linkButtons: Array<LinkButton>
    private var locationPermissionGranted = false

    /** Injected instance of the Core component. */
    @Inject
    lateinit var core: Core

    /** Injected instance of the Notification component. */
    @Inject
    lateinit var notification: Notification

    // region Lifecycle Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initializeApp()

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupActionBar()
        setupNotifications()
        setupOnBackPressed()
        requestLocationPermission()

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onDestroy() {
        // Clean up listeners to avoid memory leaks
        navController.removeOnDestinationChangedListener(this)
        if (::tabLayout.isInitialized) {
            tabLayout.removeOnTabSelectedListener(this)
        }
        core.gestures.swipe.removeOnSwipeListener(this)
        super.onDestroy()
    }
    // endregion

    // region Menu Handling
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Avoid navigating to the same destination
        if (item.itemId == navController.currentDestination?.id) {
            return false
        }

        return when (item.itemId) {
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
    // endregion

    // region Navigation
    /**
     * Called when the current destination in the [NavController] changes.
     *
     * This method is responsible for updating the action bar based on the current screen.
     * For the web view destination, it shows a custom tab layout. For other destinations,
     * it shows the destination's label as the title.
     */
    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.id) {
            R.id.navigation_web -> {
                if (core.back.web.isWebViewActive) {
                    selectTab(getString(R.string.url_stjohannis_uelzen))
                } else {
                    core.back.web.isWebViewActive = true
                    setLinkButtonsActionBar()
                }
            }
            else -> {
                core.back.web.isWebViewActive = false
                supportActionBar?.apply {
                    setDisplayShowCustomEnabled(false)
                    setDisplayShowTitleEnabled(true)
                    title = destination.label
                    if (destination.id == R.id.navigation_calendar) {
                        displayOptions = ActionBar.DISPLAY_SHOW_TITLE
                    }
                }
            }
        }
    }
    // endregion

    // region Tab Handling
    /**
     * Called when a tab in the custom action bar is selected.
     *
     * This triggers a navigation request within the web view.
     */
    override fun onTabSelected(tab: TabLayout.Tab?) {
        val url = tab?.tag as? String
        url?.let { core.webNavigation.requestNavigateTo(it) }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
        // Not needed for now
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
        // Not needed for now
    }
    // endregion

    // region Swipe Gesture Handling
    /**
     * Called when a swipe gesture is detected.
     *
     * This allows for changing tabs in the web view by swiping left or right.
     */
    override fun onSwipe(swipe: Swipe, value: Int) {
        if (!::tabLayout.isInitialized) return
        when (swipe) {
            Swipe.Left -> {
                val nextTab = tabLayout.getTabAt(tabLayout.selectedTabPosition + 1)
                nextTab?.select()
            }
            Swipe.Right -> {
                val previousTab = tabLayout.getTabAt(tabLayout.selectedTabPosition - 1)
                previousTab?.select()
            }
        }
    }
    // endregion

    // region Permission Handling
    /**
     * Handles the result of the location permission request.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionGranted = false
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true
            }
        }
    }
    // endregion

    // region Private Helper Methods
    /**
     * Performs initial setup for the application, including Firebase, theme, and API keys.
     */
    private fun initializeApp() {
        initializeFirebase()
        applyAppTheme()
    }

    /**
     * Initializes Firebase and fetches remote config values.
     */
    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
        val remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                core.apiKeys.googleMaps = remoteConfig.getString("MAPS_API_KEY")
                core.apiKeys.youtube = remoteConfig.getString("YOUTUBE_API_KEY")
            }
        }
    }

    /**
     * Applies the user-selected theme from shared preferences.
     */
    private fun applyAppTheme() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        val themeValues = resources.getStringArray(R.array.theme_values)
        val defaultTheme = themeValues[0]
        val selectedTheme = sharedPref.getString(getString(R.string.key_theme), defaultTheme)
        ThemeUtils.setTheme(themeValues, selectedTheme ?: defaultTheme)
    }


    /**
     * Sets up the main navigation controller and bottom navigation view.
     */
    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main_activity) as NavHostFragment
        navController = navHostFragment.navController

        val navView: BottomNavigationView = binding.navView
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(this)
    }

    /**
     * Configures the action bar, including the custom view for link buttons.
     */
    private fun setupActionBar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_web, R.id.navigation_media, R.id.navigation_map)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        OssLicensesMenuActivity.setActivityTitle(getString(R.string.custom_license_title))

        createLinkButtonsArray()

        if (navController.currentDestination?.id == R.id.navigation_web) {
            setLinkButtonsActionBar()
        }
    }

    /**
     * Creates the array of [LinkButton] objects used for the custom tab layout.
     */
    private fun createLinkButtonsArray() {
        linkButtons = arrayOf(
            LinkButton(getString(R.string.current), getString(R.string.url_stjohannis_uelzen)),
            LinkButton(getString(R.string.thoughts), getString(R.string.url_thoughts)),
            LinkButton(getString(R.string.groups), getString(R.string.url_groups)),
            LinkButton(getString(R.string.churchinlife), getString(R.string.url_churchinlife)),
            LinkButton(getString(R.string.gallery), getString(R.string.url_gallery)),
            LinkButton(getString(R.string.history), getString(R.string.url_history)),
            LinkButton(getString(R.string.foundation), getString(R.string.url_foundation))
        )
    }

    /**
     * Sets up and displays the custom action bar with tab-like buttons for web navigation.
     */
    private fun setLinkButtonsActionBar() {
        val linkButtonsLayout: View = layoutInflater.inflate(R.layout.linkbuttons_layout, null)
        tabLayout = linkButtonsLayout.findViewById(R.id.linkbuttons_tabLayout)
        linkButtons.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it.text).setTag(it.url))
        }
        tabLayout.addOnTabSelectedListener(this)
        supportActionBar?.customView = linkButtonsLayout
        supportActionBar?.displayOptions =
            (ActionBar.DISPLAY_SHOW_CUSTOM or ActionBar.DISPLAY_SHOW_HOME)
    }

    /**
     * Schedules or cancels daily motto notifications based on user preferences.
     */
    private fun setupNotifications() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedPref.getBoolean(getString(R.string.key_dailymotto_pushnotifications), false)) {
            notification.schedule.startRecurringDailyMotto()
        } else {
            notification.schedule.stopRecurringDailyMotto()
        }
    }

    /**
     * Configures the back press handling logic for different Android versions.
     */
    private fun setupOnBackPressed() {
        if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {
                core.back.app.onBackPressed { finish() }
            }
        } else {
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    core.back.app.onBackPressed { finish() }
                }
            })
        }
    }

    /**
     * Selects a tab in the custom action bar based on the provided URL.
     */
    private fun selectTab(url: String) {
        if (!::tabLayout.isInitialized) return
        for (i in linkButtons.indices) {
            if (linkButtons[i].url == url) {
                tabLayout.getTabAt(i)?.select()
                break
            }
        }
    }

    /**
     * Checks for and requests location permission if not already granted.
     */
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION
            )
        }
    }
    // endregion

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
    }
}
