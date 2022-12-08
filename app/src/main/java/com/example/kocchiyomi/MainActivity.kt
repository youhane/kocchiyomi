package com.example.kocchiyomi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.example.kocchiyomi.databinding.ActivityMainBinding
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var bottomNav:BottomNavigationView
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        bottomNav = binding.bottomNav

        val navHostFragment = supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)

//        val appBarConfiguration = AppBarConfiguration
//            .Builder(
//                R.id.libraryFragment,
//                R.id.browseFragment,
//                R.id.historyFragment)
//            .build()
//        setupActionBarWithNavController(navController, appBarConfiguration)
        drawerLayout = binding.drawerLayout
//        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
//        appBarConfiguration = AppBarConfiguration
//            .Builder(setOf(
//            R.id.libraryFragment,
//            R.id.browseFragment,
//            R.id.historyFragment
//            )).setOpenableLayout(drawerLayout).build()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.libraryFragment,
                R.id.browseFragment,
                R.id.historyFragment
            ),binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this,navController, drawerLayout)


        NavigationUI.setupWithNavController(binding.drawerView, navController)
        binding.bottomNav.setOnItemReselectedListener {  }
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.drawer_close)
//        toggle.setDrawerIndicatorEnabled(true);
//        toggle.isDrawerIndicatorEnabled

//        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        val userId = intent.getStringExtra("user_id")
//        val emailId = intent.getStringExtra("email_id")
//
//        tv_user_id.text = "User ID :: $userId"
//        tv_email_id.text = "Email ID :: $emailId"
//
//        btn_signout.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//
//            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
//            finish()
//        }
    }


    override fun onSupportNavigateUp(): Boolean {
//        return  navController.navigateUp() || super.onSupportNavigateUp()
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
        return findNavController(R.id.nav_host_fragment_container).navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

}