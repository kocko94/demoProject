package com.chochko.xapodemo.views.fragments.repolist

import com.chochko.xapodemo.data.POJO.github.GithubApiModel
import com.chochko.xapodemo.views.fragments.IBasePresenter


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
interface IRepoListPresenter: IBasePresenter{
    fun downloadGithubData()
}