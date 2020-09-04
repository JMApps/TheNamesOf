package jmapps

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.google.android.material.navigation.NavigationView
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.ActivityMainBinding
import jmapps.thenamesof.mvp.other.OtherContract
import jmapps.thenamesof.mvp.other.OtherPresenterImpl
import jmapps.thenamesof.ui.other.AboutUsBottomSheet
import jmapps.thenamesof.ui.other.DonateProjectBottomSheet

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OtherContract.OtherView {

    private lateinit var binding: ActivityMainBinding

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var otherPresenterImpl: OtherPresenterImpl

    private var valNightMode: Boolean = false
    private lateinit var itemNightMode: MenuItem

    companion object {
        const val keyNightTheme = "key_night_theme"
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.appBarMain.toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        valNightMode = preferences.getBoolean(keyNightTheme, valNightMode)
        darkTheme(valNightMode)

        otherPresenterImpl = OtherPresenterImpl(this, this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        itemNightMode = menu.findItem(R.id.action_night_mode)
        itemNightMode.isChecked = valNightMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.action_night_mode -> {
                otherPresenterImpl.darkTheme(!item.isChecked)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.main_fragment_container)
        when (item.itemId) {
            R.id.nav_book_content -> navController.navigate(R.id.nav_book_content)

            R.id.nav_names_content -> navController.navigate(R.id.nav_names_content)

            R.id.nav_all_names -> navController.navigate(R.id.nav_all_names)

            R.id.nav_name_flip_card -> navController.navigate(R.id.nav_name_flip_card)

            R.id.nav_name_input_card -> navController.navigate(R.id.nav_name_input_card)

            R.id.nav_quiz -> navController.navigate(R.id.nav_quiz)

            R.id.nav_download_audio -> otherPresenterImpl.downloadAllAudios()

            R.id.nav_donate -> otherPresenterImpl.donateProject()

            R.id.nav_about_us -> otherPresenterImpl.aboutUs()

            R.id.nav_rate -> otherPresenterImpl.rateApp()

            R.id.nav_share -> otherPresenterImpl.shareAppLink()
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun darkTheme(themeMode: Boolean) {
        if (themeMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        saveThemeMode(themeMode)
    }

    override fun donateProject() {
        val donateProjectBottomSheet = DonateProjectBottomSheet()
        donateProjectBottomSheet.show(
            supportFragmentManager,
            DonateProjectBottomSheet.keyDonateProject
        )
    }

    override fun aboutUs() {
        val aboutUsBottomSheet = AboutUsBottomSheet()
        aboutUsBottomSheet.show(supportFragmentManager, AboutUsBottomSheet.keyAboutUs)
    }

    private fun saveThemeMode(themeModeKey: Boolean) {
        editor.putBoolean(keyNightTheme, themeModeKey).apply()
    }
}