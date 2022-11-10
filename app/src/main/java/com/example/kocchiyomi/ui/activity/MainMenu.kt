package com.example.kocchiyomi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.kocchiyomi.R
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainMenu : AppCompatActivity() {
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    var number: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu);

        swipeRefreshLayout = findViewById(R.id.swiperefresh)
        swipeRefreshLayout.setOnRefreshListener {
            number++
//            textView.text = " Total number = $number"
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                swipeRefreshLayout.isRefreshing = false
            }, 4000)
        }
    }
}