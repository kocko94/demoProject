package com.chochko.xapodemo.views.fragments.repolist

import com.chochko.xapodemo.data.POJO.github.GithubApiModel
import com.chochko.xapodemo.network.http.GithubHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class RepoListModel{

    /**
     * Make it inline to reduce the change of creating memory leaks and unwanted anonymous class callbacks.
     * If more info is needed from the retrofit answer, we can always extend or change the higher order function.
     */
    inline fun downloadData(crossinline callback: (GithubApiModel?, Throwable?) -> Unit?){
        GithubHttpClient.githubHttpClient.getUsers().enqueue(object : Callback<GithubApiModel> {
            override fun onFailure(call: Call<GithubApiModel>, t: Throwable) {
                callback(null, t)
            }

            override fun onResponse(call: Call<GithubApiModel>, response: Response<GithubApiModel>) {
                callback(response.body(), null)
            }

        })
    }

}