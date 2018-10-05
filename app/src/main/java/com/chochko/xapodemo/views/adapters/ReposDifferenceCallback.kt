package com.chochko.xapodemo.views.adapters

import android.support.v7.util.DiffUtil
import com.chochko.xapodemo.data.POJO.github.RepositoryApiModel


/**
 * This class is used ...
 * Created on 05.10.18.
 * For further questions please contact me at this
 * email: konstantin.pl.vankov@gmail.com
 * Cheers! :)
 *
 * @author Konstantin Vankov
 */
class ReposDifferenceCallback(private val oldData: ArrayList<RepositoryApiModel>,
                              private val newData: ArrayList<RepositoryApiModel>)
    : DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].id == newData[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition] == newData[newItemPosition]

    override fun getOldListSize(): Int = this.oldData.size

    override fun getNewListSize(): Int = this.newData.size
}