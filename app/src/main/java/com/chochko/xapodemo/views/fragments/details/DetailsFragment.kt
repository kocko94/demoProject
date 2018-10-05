package com.chochko.xapodemo.views.fragments.details

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.chochko.xapodemo.R
import com.github.benoitdion.ln.Ln
import kotlinx.android.synthetic.main.fragment_detail.*
import java.lang.Math.abs


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class DetailsFragment : Fragment(), AppBarLayout.OnOffsetChangedListener{

    private var mIsProfilePictureShown = true
        set(value) {synchronized(this){field = value}}
        get() {synchronized(this){return field}}
    private val mAnimationPercentage = 20
    private val mProfileImageAnimationDurationMillis = 300L
    private var mMaxScrollSize = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_detail, container, false).apply {
        ButterKnife.bind(this@DetailsFragment, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (this@DetailsFragment.activity != null) {
//            (this@DetailsFragment.activity as AppCompatActivity).setSupportActionBar(toolbar)
//        }

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                this@DetailsFragment.activity?.supportFragmentManager?.popBackStack()
            }
        }

        this.appbar.apply {
            this@DetailsFragment.mMaxScrollSize = this.totalScrollRange
            this.addOnOffsetChangedListener(this@DetailsFragment)
        }
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        if(mMaxScrollSize == 0 && p0 != null)
            mMaxScrollSize = p0.totalScrollRange

        if(mMaxScrollSize == 0){
            Ln.e("PROBLEM")
            return
        }

        val percentageScroll = abs(p1)*100/mMaxScrollSize

        if(percentageScroll >= mAnimationPercentage && mIsProfilePictureShown){
            mIsProfilePictureShown = false

            this.profile_image.clearAnimation()
            this.profile_image.animate()
                    .scaleY(0F)
                    .scaleX(0F)
                    .setDuration(mProfileImageAnimationDurationMillis)
                    .start()
        }else if(percentageScroll <= mAnimationPercentage && !mIsProfilePictureShown){
            mIsProfilePictureShown = true

            this.profile_image.clearAnimation()
            this.profile_image.animate()
                    .scaleY(1F)
                    .scaleX(1F)
                    .setDuration(mProfileImageAnimationDurationMillis)
                    .start()

        }

    }
}