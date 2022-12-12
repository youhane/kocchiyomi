package com.example.kocchiyomi.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.replace
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.kocchiyomi.R
import com.example.kocchiyomi.databinding.ActivityAuthBinding


class AuthActivity : AppCompatActivity(), CallbackFragment {
    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_auth_container_view) as NavHostFragment

        navController = navHostFragment.navController

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<RegisterFragment>(R.id.fragment_auth_container_view)
            }
        }
//        if (savedInstanceState == null) {
//            addFragment()
//        }
    }

    private fun replaceFragment() {
        val fragment: LoginFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_auth_container_view) as LoginFragment
        supportFragmentManager.commit {
            replace<LoginFragment>(R.id.fragment_auth_container_view)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun addFragment() {
        val fragment:RegisterFragment = RegisterFragment()
        fragment.setCallbackFragment(this)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<RegisterFragment>(R.id.fragment_auth_container_view)
        }
    }


    public fun isValidEmail(str: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }

    public fun makeToast(msg: String) {
        Toast.makeText(
            this@AuthActivity,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun changeFragment() {
        replaceFragment()
    }
}