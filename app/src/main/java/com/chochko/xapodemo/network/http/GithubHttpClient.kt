package com.chochko.xapodemo.network.http

import com.chochko.xapodemo.network.control.EndPoints
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * This class is used ...
 * Created on 04.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
object GithubHttpClient{
    //building it every time is CPU consuming because build is expensive function. Therefore, it will be built only once during its lifetime
    private var retrofit: Retrofit = Retrofit.Builder()
                                    .baseUrl(EndPoints.BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                                    .build()

    //the singleton object of this class
    @JvmStatic//for our old school java buddies
    var githubHttpClient = retrofit.create(IGithubRepositories::class.java)

}