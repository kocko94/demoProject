package com.chochko.xapodemo.network.http

import com.chochko.xapodemo.network.control.EndPoints
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


/**
 * This class is used ...
 * Created on 04.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
interface IGithubRepositories {

    /**
     * All of the parameters are changable but
     */
    @GET(EndPoints.ENDPOINT_REPOSITORIES)
    fun getUsers(@QueryMap(encoded = true) searchParams: HashMap<String, String> = hashMapOf("q" to "topic:android"),
                 @Query("sort") sort: String = "stars",
                 @Query("order") order: String = "desc"): Call<Any>
}