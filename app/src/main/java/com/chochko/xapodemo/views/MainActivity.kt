package com.chochko.xapodemo.views

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionInflater
import android.widget.ImageView
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import com.chochko.xapodemo.views.fragments.details.DetailsFragment
import com.chochko.xapodemo.views.fragments.repolist.ReposListFragment
import com.github.benoitdion.ln.Ln

class MainActivity : AppCompatActivity(),
            ReposListFragment.OnItemClickListener{

    private val TAG_REPO_LIST_FRAGMENT = "RepoListFragment"
    private val TAG_DETAILS_FRAGMENT = "DetailsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ReposListFragment(), TAG_REPO_LIST_FRAGMENT)
                    .addToBackStack(null)
                    .commit()
        }

    }

    private fun showDetailedFragment(clickedRepo: RepositoryApiModel, sharedElement: ImageView){
        val detailFragment = DetailsFragment.newInstance(clickedRepo)

        //shared transition logic
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            detailFragment.apply {
                val transitionMove = TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.move)
                val transitionFade = Fade()
                sharedElementEnterTransition = transitionMove
                sharedElementReturnTransition = transitionMove
                enterTransition = transitionFade
                exitTransition = transitionFade
            }
        }

        this.supportFragmentManager.beginTransaction()
                .addSharedElement(sharedElement, getString(R.string.fragment_shared_image_view_transition_name))
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    //Callbacks

    override fun onRepoListItemClicked(clickedRepo: RepositoryApiModel, sharedElement: ImageView) {
        Ln.e("Clicked $clickedRepo")
        this.showDetailedFragment(clickedRepo, sharedElement)

    }
}
