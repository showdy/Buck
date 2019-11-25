package com.buck.data.db

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class VideoEntity(
    @SerializedName("AddTime")
    val addTime: String,
    @SerializedName("CoverImgUrl")
    val coverImgUrl: String,
    val ID: Int,
    @SerializedName("IsVip")
    val isVip: Int,
    @SerializedName("Length")
    val length: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("Tags")
    val tags: String,
    @SerializedName("TypeID")
    val typeID: Int,
    @SerializedName("TypeName")
    val typeName: String,
    @SerializedName("Url")
    val url: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addTime)
        parcel.writeString(coverImgUrl)
        parcel.writeInt(ID)
        parcel.writeInt(isVip)
        parcel.writeInt(length)
        parcel.writeString(name)
        parcel.writeString(tags)
        parcel.writeInt(typeID)
        parcel.writeString(typeName)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoEntity> {
        override fun createFromParcel(parcel: Parcel): VideoEntity {
            return VideoEntity(parcel)
        }

        override fun newArray(size: Int): Array<VideoEntity?> {
            return arrayOfNulls(size)
        }
    }
}



