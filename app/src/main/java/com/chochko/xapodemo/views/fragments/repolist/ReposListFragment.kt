package com.chochko.xapodemo.views.fragments.repolist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.GithubApiModel
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import com.chochko.xapodemo.views.adapters.ReposListAdapter
import com.github.benoitdion.ln.Ln
import kotlinx.android.synthetic.main.fragment_repos_list.*

private const val KEY_SCROLL_POSITION = "scrollPosition"
private const val KEY_ADAPTER_ITEMS = "reposListAdapterItems"
/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class ReposListFragment : Fragment(),
        IRepoListView{


    private var mPresenter: IRepoListPresenter? = null
    private var mItemClickListener: OnItemClickListener? = null
    private var mScrollPosition = -1
    private var mAdapterItems: ArrayList<RepositoryApiModel>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_repos_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.recycler_view.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = ReposListAdapter().also {
                it.onClickListener = this@ReposListFragment.mItemClickListener!!
            }
            //some small performance optimisation
            setHasFixedSize(true)
        }

        //init the pull to refresh layout
        this.pull_refresh.setOnRefreshListener {
            this.mPresenter?.downloadGithubData()
        }

        //continue only if the app is restoring after rotation
        savedInstanceState ?: return
        //immediately restore the state
        this.restoreStateAfterRotation(savedInstanceState)
    }

    private fun restoreStateAfterRotation(savedInstanceState: Bundle){
        //restore state values
        this.mScrollPosition = savedInstanceState.getInt(KEY_SCROLL_POSITION)
        this.mAdapterItems = savedInstanceState.getParcelableArrayList(KEY_ADAPTER_ITEMS)

        //update the adapter right away
        this.updateAdapterData(this.mAdapterItems!!)

        //correct the animation value in the class so it wont animate object above the current ones
        (this.recycler_view.adapter as ReposListAdapter).animatedPosition = this.mScrollPosition

        //scroll to the previous item in the list
        this.recycler_view.scrollToPosition(this.mScrollPosition)

    }

    private fun updateAdapterData(newData: ArrayList<RepositoryApiModel>){
        this.mAdapterItems = newData
        (this.recycler_view.adapter as ReposListAdapter).updateListData(newData)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            val scrollPosition = (this@ReposListFragment.recycler_view.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
            putInt(KEY_SCROLL_POSITION, scrollPosition)
            val adapterList = (this@ReposListFragment.recycler_view.adapter as ReposListAdapter).reposData
            putParcelableArrayList(KEY_ADAPTER_ITEMS, adapterList)
        }
    }

    override fun onResume() {
        super.onResume()
        this.mPresenter = IRepoListPresenterImp(this).also {
            if(mAdapterItems == null)//only if the device was not rotated
                it.downloadGithubData()
            else
                this@ReposListFragment.updateAdapterData(mAdapterItems!!) //update the adapter right away from the local data
        }
    }

    override fun onPause() {
        super.onPause()

        //Prevent memory leaks
        this.mPresenter?.onStop()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        //return if the context is null
        context ?: return

        if(context is OnItemClickListener)
            this.mItemClickListener = context
        else
            throw RuntimeException(context.toString() + " must implement ReposListAdapter.OnItemClickListener!")

    }

    override fun onDetach() {
        super.onDetach()
        this.mItemClickListener = null
    }

    override fun onDownloadStart() {
        Toast.makeText(this.context, getString(R.string.fragment_repos_list_download_start), Toast.LENGTH_LONG).show()
    }

    override fun onDownloadFinish(downloadedRepos: GithubApiModel?, t: Throwable?) {
        //dismiss the animation
        this.pull_refresh?.isRefreshing = false

        if(downloadedRepos == null){//error while downloading
            val errorReason = t?.message ?: getString(R.string.fragment_repos_list_download_error_no_reason)
            Toast.makeText(this@ReposListFragment.context, getString(R.string.fragment_repos_list_download_error_msg, errorReason), Toast.LENGTH_LONG).show()
        }else{//the API call was successful
            //update the adapter
            this.updateAdapterData(ArrayList(downloadedRepos.items))
            //notify the user
            Toast.makeText(this@ReposListFragment.context, getString(R.string.fragment_repos_list_download_successful_finished), Toast.LENGTH_LONG).show()
        }
    }

    interface OnItemClickListener{
        /**
         * Callback for the clicked [RepositoryApiModel] that are assigned
         * through the constructor of this [ReposListAdapter] class.
         *
         * @param clickedRepo the clicked object itself holding all of the data.
         * @param sharedElement for APIs >= 21 this would be the shared element between fragments transition
         *
         */
        fun onRepoListItemClicked(clickedRepo: RepositoryApiModel, sharedElement: ImageView)
    }

}
