package com.chochko.xapodemo.data.POJO.github

import com.google.gson.annotations.Expose


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
data class GithubApiModel(@Expose val items:List<RepositoryApiModel>)