package com.chochko.xapodemo.views

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.transition.Fade
import android.support.transition.TransitionInflater
import android.support.transition.TransitionSet
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import com.chochko.xapodemo.views.fragments.details.DetailsFragment
import com.chochko.xapodemo.views.fragments.repolist.ReposListFragment
import com.github.benoitdion.ln.Ln
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(),
            ReposListFragment.OnItemClickListener{

    private val TAG_REPO_LIST_FRAGMENT = "RepoListFragment"
    private val TAG_DETAILS_FRAGMENT = "DetailsFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ReposListFragment(), TAG_REPO_LIST_FRAGMENT)
                .addToBackStack(null)
                .commit()

    }

    private fun showDetailedFragment(clickedRepo: RepositoryApiModel, sharedElement: CircleImageView){
        val detailFragment = DetailsFragment()

        //shared transition logic
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            val repoFragment = supportFragmentManager.findFragmentByTag(TAG_REPO_LIST_FRAGMENT)
            val fade = Fade().also{it.duration = 1000L}
            //Exit the previous fragment
            repoFragment?.exitTransition = fade

            //Shared element tranistion
            TransitionSet().apply{
                addTransition(TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.move))
                duration = 1000L

                detailFragment.sharedElementEnterTransition = this
            }

            //enter tranistion for the new fragment
            detailFragment.enterTransition = fade
//            detailFragment.apply {
//                this.sharedElementEnterTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.image_transition)
//                this.returnTransition = TransitionInflater.from(this@MainActivity).inflateTransition(R.transition.image_transition)
//                this.enterTransition = TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.explode)
//                this.exitTransition = TransitionInflater.from(this@MainActivity).inflateTransition(android.R.transition.explode)
//            }
        }

        this.supportFragmentManager.beginTransaction()
                .addSharedElement(sharedElement, getString(R.string.fragment_shared_image_view_transition_name))
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit()
    }


    //Callbacks

    override fun onRepoListItemClicked(clickedRepo: RepositoryApiModel, sharedElement: CircleImageView) {
        Ln.e("Clicked $clickedRepo")
        this.showDetailedFragment(clickedRepo, sharedElement)

    }
}
