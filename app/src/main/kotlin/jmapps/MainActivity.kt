package jmapps

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var otherPresenterImpl: OtherPresenterImpl

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.appBarMain.toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences.edit()

        otherPresenterImpl = OtherPresenterImpl(this, this)

        val navController = findNavController(R.id.main_fragment_container)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_book_content,
                R.id.nav_names_content,
                R.id.nav_all_names,
                R.id.nav_name_flip_card,
                R.id.nav_name_input_card,
                R.id.nav_quiz
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
        binding.navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_download_audio -> otherPresenterImpl.downloadAllAudios()

            R.id.nav_donate -> otherPresenterImpl.donateProject()

            R.id.nav_about_us -> otherPresenterImpl.aboutUs()

            R.id.nav_rate -> otherPresenterImpl.rateApp()

            R.id.nav_share -> otherPresenterImpl.shareAppLink()
            }
        return true
    }

    override fun donateProject() {
        val donateProjectBottomSheet = DonateProjectBottomSheet()
        donateProjectBottomSheet.show(supportFragmentManager, DonateProjectBottomSheet.keyDonateProject)
    }

    override fun aboutUs() {
        val aboutUsBottomSheet = AboutUsBottomSheet()
        aboutUsBottomSheet.show(supportFragmentManager, AboutUsBottomSheet.keyAboutUs)
    }
}