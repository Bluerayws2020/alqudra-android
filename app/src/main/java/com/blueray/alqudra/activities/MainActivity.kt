package com.blueray.alqudra.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // get navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // set the bottom App Bar with the nav controller
        NavigationUI.setupWithNavController(binding.bottomAppBar, navController)

        // disable placeHolders
        binding.bottomAppBar.menu.findItem(R.id.placeHolder1).isEnabled = false
        binding.bottomAppBar.menu.findItem(R.id.placeHolder2).isEnabled = false

        // handel bottom nav bar
        binding.bottomAppBar.setOnItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.home ->{
                    navController.navigate(R.id.home)
                    true
                }
                R.id.travel->{
                    navController.navigate(R.id.travel)
                    true
                }
                else ->{
                    false
                }
            }
        }

    }
}