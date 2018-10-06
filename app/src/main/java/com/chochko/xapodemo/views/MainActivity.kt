package com.chochko.xapodemo.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import com.chochko.xapodemo.views.fragments.repolist.ReposListFragment
import com.github.benoitdion.ln.Ln
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(),
            ReposListFragment.OnItemClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, ReposListFragment()).addToBackStack(null).commit()

    }

    override fun onItemClick(clickedRepo: RepositoryApiModel, sharedElement: CircleImageView) {
        Ln.e("Clicked $clickedRepo")
    }
}
