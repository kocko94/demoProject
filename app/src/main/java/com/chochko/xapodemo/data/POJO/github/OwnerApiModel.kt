package com.chochko.xapodemo.data.POJO.github

import android.os.Parcel
import android.os.Parcelable
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
data class OwnerApiModel(@SerializedName("login") val userName: String,
                         @SerializedName("avatar_url") val userAvatarUrl: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString()!!,
            source.readString()!!
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(userName)
        writeString(userAvatarUrl)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<OwnerApiModel> = object : Parcelable.Creator<OwnerApiModel> {
            override fun createFromParcel(source: Parcel): OwnerApiModel = OwnerApiModel(source)
            override fun newArray(size: Int): Array<OwnerApiModel?> = arrayOfNulls(size)
        }
    }
}