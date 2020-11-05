package com.evaluation.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.evaluation.R
import com.evaluation.language.LanguageActivity
import com.evaluation.listener.FragmentListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentListener {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController: NavController by lazy {
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        setupWithNavController(toolBar, navController, drawer)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment)) ||
                super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        when {
            drawer.isDrawerOpen(GravityCompat.START) -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            drawer.isDrawerOpen(GravityCompat.END) -> {
                drawer.closeDrawer(GravityCompat.END)
            }
            else -> {
                return super.onBackPressed()
            }
        }
    }

    override fun startActivity() {
        startActivity(intentFor<LanguageActivity>())
    }
}