package com.chochko.xapodemo.data.POJO.github

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
data class RepositoryApiModel(@Expose val id: String,
                              @Expose val name: String,
                              @Expose val owner: OwnerApiModel,
                              @Expose val description: String,
                              @SerializedName("watchers_count") val watchersCount: Int,
                              @Expose val language: String,
                              @SerializedName("forks_count") val forksCount: Int)