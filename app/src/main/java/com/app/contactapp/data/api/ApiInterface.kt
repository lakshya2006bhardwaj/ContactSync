package com.app.contactapp.data.api

import com.app.contactapp.common.ApiConstants
import com.app.contactapp.domain.model.response.ContactsData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiInterface {

    @GET(ApiConstants.GET_NEW_CONTACTS)
    suspend fun getContacts(): Response<ContactsData>

}