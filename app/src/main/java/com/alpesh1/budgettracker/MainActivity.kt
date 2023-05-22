package com.alpesh1.budgettracker

import DatabaseClass.DBHelper
import Fragment.Add_Fragment
import Fragment.Home_Fragment
import Fragment.Transaction_Fragment
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.alpesh1.budgettracker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var dbHelper: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomicon.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {

                    R.id.bt_home -> {
                        loadFragment(Home_Fragment())
                    }

                    R.id.bt_add -> {
                        loadFragment(Add_Fragment())
                    }

                    R.id.bt_trans -> {
                        loadFragment(Transaction_Fragment())
                    }
                }

                return true
            }

            private fun loadFragment(fragment: Fragment) {

                supportFragmentManager.beginTransaction().replace(R.id.fragFrame, fragment).commit()
            }

        })

    }

}