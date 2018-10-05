package com.chochko.xapodemo.views.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import kotlinx.android.synthetic.main.repos_list_item.view.*
import java.lang.IllegalArgumentException


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class ReposListAdapter(private var mReposData: ArrayList<RepositoryApiModel> = arrayListOf())
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //used for notifying the creating fragment for the clicked item
    var onClickListener: OnItemClickListener? = null

    //helper for animating the views only once
    private var mAnimatedPosition = -1

    private enum class ViewType{
        Repo, EndOfList
    }

//    override fun getItemViewType(position: Int): Int = if(mReposData.lastIndex == position) ViewType.EndOfList.ordinal else ViewType.Repo.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            when(viewType){
                ViewType.Repo.ordinal -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repos_list_item, parent, false))
//                ViewType.EndOfList.ordinal -> ViewHolderLoadingMore(LayoutInflater.from(parent.context).inflate(R.layout.repo_list_end_item, parent, false))
                else -> { throw IllegalArgumentException("Unknown view type provided $viewType! Possibles view types are ${ViewType.Repo.ordinal} and ${ViewType.EndOfList.ordinal}...")}
            }

    override fun getItemCount(): Int = mReposData.size

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        this.applyAnimation(p0.itemView, p1)
        (p0 as UpdateViewHolder).bindViews(mReposData[p1])
    }

    /**
     * Simple left to right one time animation for all of the view
     * elements presented within the given [viewToAnimate].
     */
    private fun applyAnimation(viewToAnimate: View, position: Int){
        if (position > mAnimatedPosition) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            this.mAnimatedPosition = position
        }
    }

    /**
     * Update the adapter in an efficient way.
     */
    fun updateListData(newData: ArrayList<RepositoryApiModel>){
        //very efficient way of updating adapter with new values
        DiffUtil.calculateDiff(ReposDifferenceCallback(this.mReposData, newData)).apply {
            //clear the old data
            this@ReposListAdapter.mReposData.clear()
            this@ReposListAdapter.mReposData.addAll(newData)

        }.dispatchUpdatesTo(this@ReposListAdapter)

    }

    /**
     * The idea behind this view holder is in case that we can somehow
     * separate the pile of data to be received in smaller packages and only
     * download new 'package' of data when the user has scrolled to the last
     * item in [mReposData]. Unfortunately, the GitHub's API is not supporting
     * such separation and the only reasonable way of achieving such separation
     * would be to initially download the data and store it in a DB. Room
     * is a very useful tool from the JatPack arsenal. Using some DB we can
     * save the data there and retrieve it when needed. Thus, reducing the
     * network usage of the device but slightly increasing the storage used
     * by this app. Implementing such complex structure for a small demo as
     * this app makes no sense but I wanted to point this out as something
     * that I'd have done for a real project :).
     */
    inner class ViewHolderLoadingMore(view: View): RecyclerView.ViewHolder(view), UpdateViewHolder{
        override fun bindViews(repository: RepositoryApiModel) {
        }
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view), UpdateViewHolder{
        private val repoOwnerAvatar = view.repo_list_item_image
        private val repoShortName = view.repo_list_item_short_name
        private val repoShortDescription = view.repo_list_item_short_description

        override fun bindViews(repository: RepositoryApiModel) {

            Glide.with(repoOwnerAvatar.context)
                    .load(repository.owner.userAvatarUrl)
                    .into(repoOwnerAvatar)

            repoShortName.text = repository.name
            repoShortDescription.text = repository.description

            this.itemView.setOnClickListener{
                this@ReposListAdapter.onClickListener?.onItemClick(repository)
            }
        }

    }

    private interface UpdateViewHolder{
        fun bindViews(repository: RepositoryApiModel)
    }

    interface OnItemClickListener{
        /**
         * Callback for the clicked [RepositoryApiModel] that are assigned
         * through the constructor of this [ReposListAdapter] class.
         *
         * @param clickedRepo the clicked object itself holding all of the data.
         */
        fun onItemClick(clickedRepo: RepositoryApiModel)
    }
}