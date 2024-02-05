package com.blueray.alqudra.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.blueray.alqudra.R
import com.blueray.alqudra.databinding.ActivityMainBinding
import com.blueray.alqudra.fragments.CustomDrawerLayout
import com.blueray.alqudra.helpers.HelpersUtils.clearSharedPreferences
import com.blueray.alqudra.helpers.HelpersUtils.getName

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    companion object{
        var NAME = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        getName(this){
            name->
            NAME = name
        }

        // get navController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        // set the bottom App Bar with the nav controller
        NavigationUI.setupWithNavController(binding.bottomAppBar, navController)
        // disable placeHolders
        binding.bottomAppBar.menu.findItem(R.id.placeHolder1).isEnabled = false
        binding.bottomAppBar.menu.findItem(R.id.placeHolder2).isEnabled = false

        // test for Drawer
        handelDrawerClick()

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
                R.id.info->{
                    navController.navigate((R.id.myProfileActivity))
                    true
                }
                else ->{
                    false
                }
            }
        }

    }

    fun openDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else
            binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun handelDrawerClick(){
        // drawer on click listener
        CustomDrawerLayout.onItemSelectedListener {
            when(it){
                CustomDrawerLayout.Options.PROFILE_LAYOUT ->{
                    navController.navigate((R.id.myProfileActivity))
                    binding.bottomAppBar.selectedItemId = R.id.info
                    openDrawer()
                }

                CustomDrawerLayout.Options.DRIVING_VIOLATION ->{
                    startActivity(Intent(this,VehicleViolationsActivity::class.java))
                    openDrawer()
                }

                CustomDrawerLayout.Options.NOTIFICATIONS ->{
                    navController.navigate(R.id.notifications)
//                    binding.bottomAppBar.selectedItemId = R.id.travel
                    openDrawer()
                }

                CustomDrawerLayout.Options.TERMS_AND_CONDITIONS ->{
                    startActivity(Intent(this,PrivacyPolicyActivity::class.java))
                    openDrawer()
                }

                CustomDrawerLayout.Options.CALL_SUPPORT ->{
                    startActivity(Intent(this,TechnicalSupportActivity::class.java))
                    openDrawer()
                }

                CustomDrawerLayout.Options.SIGN_OUT ->{

                    // clean shared preferences and redirect to login activity

                    clearSharedPreferences(this)
                    startActivity(Intent(this,LoginActivaty::class.java).apply {
                        // Add a unique flag or action to the intent
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    })
                    finish()
                }

                CustomDrawerLayout.Options.MY_DATA ->{

                    openDrawer()
                }
            }
        }
    }

    fun hideMenu(){
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }
    fun showDrawer(){
        binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }



}