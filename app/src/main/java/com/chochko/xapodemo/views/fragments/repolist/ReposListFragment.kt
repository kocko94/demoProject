package com.chochko.xapodemo.views.fragments.repolist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import com.chochko.xapodemo.R
import com.chochko.xapodemo.data.POJO.github.GithubApiModel
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel
import com.chochko.xapodemo.views.adapters.ReposListAdapter
import com.github.benoitdion.ln.Ln
import kotlinx.android.synthetic.main.fragment_repos_list.*


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
        IRepoListView,
        ReposListAdapter.OnItemClickListener{


    private var mPresenter: IRepoListPresenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_repos_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.recycler_view.apply {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = ReposListAdapter().also {
                it.onClickListener = this@ReposListFragment
            }
            //some small performance optimisation
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        this.mPresenter = IRepoListPresenterImp(this).also {
            it.downloadGithubData()
        }
    }

    override fun onPause() {
        super.onPause()

        //Prevent memory leaks
        this.mPresenter?.onStop()
    }

    override fun onItemClick(clickedRepo: RepositoryApiModel) {
        Ln.e("Clicked $clickedRepo")
    }

    override fun onDownloadStart() {
        //Start animation
    }

    override fun onDownloadFinish(downloadedRepos: GithubApiModel?, t: Throwable?) {
        Ln.e("TUKA")
        if(downloadedRepos == null){//error while downloading
            val errorReason = t?.message ?: getString(R.string.fragment_repos_list_download_error_no_reason)
            Toast.makeText(this@ReposListFragment.context, getString(R.string.fragment_repos_list_download_error_msg, errorReason), Toast.LENGTH_LONG).show()
        }else{//the API call was successful
            //update the adapter
            (this.recycler_view.adapter as ReposListAdapter).updateListData(ArrayList(downloadedRepos.items))
        }
    }

}
