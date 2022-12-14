package com.example.kocchiyomi

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.kocchiyomi.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment

        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration
            .Builder(
                R.id.libraryFragment,
                R.id.browseFragment,
                R.id.historyFragment)
            .build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemReselectedListener {  }
//        val userId = intent.getStringExtra("user_id")
//        val emailId = intent.getStringExtra("email_id")
//
//        tv_user_id.text = "_User ID :: $userId"
//        tv_email_id.text = "Email ID :: $emailId"
//
//        btn_signout.setOnClickListener {
//            FirebaseAuthData.getInstance().signOut()
//
//            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
//            finish()
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() || super.onSupportNavigateUp()
    }

}