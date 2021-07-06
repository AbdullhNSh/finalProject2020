package com.example.finalproject2020.model

import android.graphics.Bitmap
import android.media.Image
import android.os.Parcel
import android.os.Parcelable

 class categories(var id: Int=0,
                  var name: String="",
                  var image: String=""
){

}
    /*:Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<categories> {
        override fun createFromParcel(parcel: Parcel): categories {
            return categories(parcel)
        }

        override fun newArray(size: Int): Array<categories?> {
            return arrayOfNulls(size)
        }
       /* val COL_ID = "id"
        val COL_TITLE_ = "title"
        val COL_IMAGE = "image"

        val TABLE_NAME = "categories"
        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement,$COL_TITLE_ text not null, $COL_IMAGE integer)"*/

    }

}*/