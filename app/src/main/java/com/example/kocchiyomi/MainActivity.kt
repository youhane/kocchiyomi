package com.example.kocchiyomi

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kocchiyomi.databinding.ActivityMainBinding
import com.example.kocchiyomi.ui.auth.AuthActivity
import com.example.kocchiyomi.utils.AuthUtil
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserData()

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment = supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.libraryFragment,
                R.id.browseFragment,
                R.id.searchFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.appBarMain.bottomNav.setupWithNavController(navController)
        binding.appBarMain.bottomNav.setOnItemReselectedListener {  }

        setDrawerHeader()
        setDrawerBody()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return  navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setDrawerHeader() {
        val navigationView : NavigationView = findViewById(R.id.nav_view)
        val headerNav = navigationView.getHeaderView(0)
        val tvHeaderUsername = headerNav.findViewById<TextView>(R.id.tv_header_username)
        val tvHeaderEmail = headerNav.findViewById<TextView>(R.id.tv_header_email)

        viewModel.userDataResponse.observe(this){
                response ->
            run {
                tvHeaderUsername.text = response.userName
                tvHeaderEmail.text = response.email
            }
        }
    }

    private fun setDrawerBody() {
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val menuNav = navigationView.menu
        val navLogout = menuNav.findItem(R.id.nav_logout)

        navLogout.setOnMenuItemClickListener {
            if (AuthUtil.firebaseAuthInstance().currentUser != null) {
                viewModel.signOut()
                viewModel.isSignOut.observe(this) { response ->
                    run {
                        if (response) {
                            navigateToAuth()
                        }
                    }
                }
            }
            true
        }
    }

    private fun navigateToAuth() {
        val intent = Intent(this@MainActivity, AuthActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
        finish()
    }

}