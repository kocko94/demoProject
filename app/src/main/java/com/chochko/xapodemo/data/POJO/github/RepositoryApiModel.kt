package com.chochko.xapodemo.data.POJO.github

import android.os.Parcel
import android.os.Parcelable
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
                              @SerializedName("forks_count") val forksCount: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readParcelable(OwnerApiModel::class.java.classLoader)!!,
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readInt())


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(description)
        parcel.writeInt(watchersCount)
        parcel.writeString(language)
        parcel.writeInt(forksCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepositoryApiModel> {
        override fun createFromParcel(parcel: Parcel): RepositoryApiModel {
            return RepositoryApiModel(parcel)
        }

        override fun newArray(size: Int): Array<RepositoryApiModel?> {
            return arrayOfNulls(size)
        }
    }
}