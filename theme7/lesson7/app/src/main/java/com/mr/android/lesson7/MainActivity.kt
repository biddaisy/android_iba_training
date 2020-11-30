package com.mr.android.lesson7

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_menu_about -> {
                Toast.makeText(this, "Lesson 7", Toast.LENGTH_LONG).show()
                true
            }
            R.id.subitem1 -> {
                true
            }
            R.id.subitem2 -> {
                navController.navigate(R.id.action_global_itemFragment)
                true
            }
            R.id.subitem3 -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}