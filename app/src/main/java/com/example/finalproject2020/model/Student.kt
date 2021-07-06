package com.example.finalproject2020.model

import android.os.Parcel
import android.os.Parcelable


data class Student(var id : Int,var username: String?, var password: String?,var email:String?,var phonrnumber:String?,var login:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(email)
        parcel.writeString(phonrnumber)
        parcel.writeString(login)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Student> {
        override fun createFromParcel(parcel: Parcel): Student {
            return Student(parcel)
        }

        override fun newArray(size: Int): Array<Student?> {
            return arrayOfNulls(size)
        }
        val COL_ID = "id"
        val COL_NAME = "username"
        val COL_PASS = "password"
        val COL_EMAIL = "email"
        val COL_NUMBER = "phonrnumber"
        val COL_LOGIN = "login"

        val TABLE_NAME = "Login1"
        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement,$COL_NAME text not null, $COL_PASS text not null, $COL_EMAIL text not null, $COL_NUMBER text not null,$COL_LOGIN text not null)"
    }


}


