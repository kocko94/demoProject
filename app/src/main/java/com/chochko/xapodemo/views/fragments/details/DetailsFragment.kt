package com.chochko.xapodemo.views.fragments.details

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.view.*
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import kotlinx.android.synthetic.main.fragment_detail.*
import java.lang.IllegalArgumentException
import java.lang.Math.abs

private const val KEY_REPO_MODEL = "repoModelKey"

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

    private lateinit var mRepoModel: RepositoryApiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Postpone the enter transition until the image is loaded
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.activity?.postponeEnterTransition()
        }

        arguments?.let {
            this@DetailsFragment.mRepoModel = it.getParcelable(KEY_REPO_MODEL) ?: throw IllegalArgumentException("A RepositoryApiModel has to be provided upon creation!")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.fragment_detail, container, false).apply {
        ButterKnife.bind(this@DetailsFragment, this) //just because you like butterknife although it usage with kotlin is quiite limited
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.initLayout()
    }

    private fun initLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Glide.with(this.context!!)
                    .load(this.mRepoModel.owner.userAvatarUrl)
                    .apply(RequestOptions().dontAnimate().fitCenter())
                    .listener(object : RequestListener<Drawable>{
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            this@DetailsFragment.scheduleStartPostponedTransition()
                            return false
                        }

                    })
                    .into(this.profile_image)
        }else{
            Glide.with(this.context!!)
                    .load(this.mRepoModel.owner.userAvatarUrl)
                    .apply(RequestOptions().fitCenter())
                    .into(this.profile_image)
        }

        //Dummy way of doing it but quite fast given my time limitations :)
        this.repo_full_info.text = mRepoModel.toDetailFragmentString(this.context!!)

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


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun scheduleStartPostponedTransition() {
        this.profile_image.viewTreeObserver.addOnPreDrawListener(object: ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                this@DetailsFragment.profile_image.viewTreeObserver.removeOnPreDrawListener(this)
                this@DetailsFragment.activity?.startPostponedEnterTransition()
                return true
            }
        })
    }

    override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
        if(mMaxScrollSize == 0 && p0 != null)
            mMaxScrollSize = p0.totalScrollRange

        if(mMaxScrollSize == 0){
            return
        }

        val percentageScroll = abs(p1)*100/mMaxScrollSize

        if(percentageScroll >= mAnimationPercentage && mIsProfilePictureShown){
            mIsProfilePictureShown = false

            this.profile_image.clearAnimation()
            this.github_image.clearAnimation()
            this.scaleDownAnimation(this.profile_image)
            this.scaleDownAnimation(this.github_image)

        }else if(percentageScroll <= mAnimationPercentage && !mIsProfilePictureShown){
            mIsProfilePictureShown = true


            this.profile_image.clearAnimation()
            this.github_image.clearAnimation()
            this.scaleUpAnimation(this.profile_image)
            this.scaleUpAnimation(this.github_image)
        }
    }

    private fun scaleDownAnimation(view: View, animationDuration: Long = mProfileImageAnimationDurationMillis){
        view.animate()
            .scaleY(0F)
            .scaleX(0F)
            .setDuration(animationDuration)
            .start()
    }

    private fun scaleUpAnimation(view: View, animationDuration: Long = mProfileImageAnimationDurationMillis){
        view.animate()
            .scaleY(1F)
            .scaleX(1F)
            .setDuration(animationDuration)
            .start()
    }

    companion object {
        @JvmStatic
        fun newInstance(clickedModel: RepositoryApiModel) =
                DetailsFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(KEY_REPO_MODEL, clickedModel)
                    }
                }
    }
}