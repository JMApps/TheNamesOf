package jmapps

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import jmapps.thenamesof.R
import jmapps.thenamesof.databinding.ActivityMainBinding
import jmapps.thenamesof.ui.main.MainFragment
import jmapps.thenamesof.ui.main.chapters.MainContentChaptersBottomSheet

class MainActivity : AppCompatActivity(), MainContentChaptersBottomSheet.ToMainContentViewPager {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.appBarMain.toolbar)

        val navController = findNavController(R.id.main_fragment_container)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_names_content,
                R.id.nav_book_content,
                R.id.nav_name_flip_card,
                R.id.nav_name_input_card,
                R.id.nav_quiz
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navigationView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_fragment_container)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun toMainContentViewPager(currentItem: Int) {
        Toast.makeText(this, "yes = $currentItem", Toast.LENGTH_SHORT).show()
    }
}