package com.evaluation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.onNavDestinationSelected
import com.evaluation.R
import com.evaluation.fragment.BaseFragment
import com.evaluation.language.LanguageActivity
import com.evaluation.listener.FragmentListener
import com.evaluation.utils.LANGUAGE
import com.evaluation.utils.emptyString
import com.evaluation.viewmodel.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentListener {

    private val viewModel: MainViewModel by viewModels()

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

    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        initLoader()
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
            else -> {
                return super.onBackPressed()
            }
        }
    }

    private fun initLoader() {
        viewModel.categoryResult.observe(this) { categories ->
            val drawerMenu: Menu = navCategory.menu
            categories.forEach {
                drawerMenu.add(it.name)
            }
            navCategory.setNavigationItemSelectedListener { menu ->
                fragment()?.categorySwitched(categories.find { it.name == menu.title.toString() }?.name ?: emptyString())
                drawer.closeDrawers()
                true
            }
        }
    }

    private fun fragment(): BaseFragment? {
        val fragments = navHostFragment.childFragmentManager.fragments
        return fragments.find { it is BaseFragment && it.isVisible } as? BaseFragment
    }

    override fun startActivity() {
        startActivity(intentFor<LanguageActivity>())
    }
}