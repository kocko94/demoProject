package com.chochko.xapodemo.views.fragments.repolist



/**
 * Very basic example of using MVP. It is here only for a demo purposes!
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class IRepoListPresenterImp(private var listener: IRepoListView?) : IRepoListPresenter{

    private val mModel = RepoListModel()

    override fun downloadGithubData() {
        this.listener?.onDownloadStart()
        this.mModel.downloadData { githubApiModel, throwable ->
            this@IRepoListPresenterImp.listener?.onDownloadFinish(githubApiModel, throwable)
        }

    }

    override fun onStop() {
        this.listener = null
    }

}