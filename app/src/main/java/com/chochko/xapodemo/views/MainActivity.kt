package com.chochko.xapodemo.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chochko.xapodemo.R
import com.chochko.xapodemo.views.fragments.repolist.ReposListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ReposListFragment()).addToBackStack(null).commit()

    }
}
