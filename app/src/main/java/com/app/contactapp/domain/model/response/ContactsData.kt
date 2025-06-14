package com.app.contactapp.domain.model.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ContactsData(
    val success: Boolean,
    val Data: ContactDataBody
)
data class ContactDataBody(

    val date: String,
    val totalUsers: Int,
    val users: ArrayList<UserData>
)
@Parcelize
data class UserData(
    val id: String,
    val fullName: String,
    val phone: String,
    val email:String,
    val course: String,
    val enrolledOn:String
):Parcelable


