package com.mr.android.lesson7

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.runBlocking

interface A{
    fun test();
}

fun <T> f(b : A.(Int)->T){
    val a = object : A {
        override fun test() {
            fun test() {}
        }
    }
    b.invoke(a, 5)
}

fun <T> f2(b : (Int)->T){
    b(5)
}

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        navController = navHostFragment.navController
        runBlocking { f { i: Int->test(); print("test=$i")} }
        runBlocking { f2 { print("test2=$it")} }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_menu_about -> {
                val toast = Toast.makeText(baseContext, "About: Lesson 7", Toast.LENGTH_SHORT)
                toast.show()
                true
            }
            R.id.subitem1 -> {
                navController.navigate(R.id.action_global_dialogsFragment)
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