package com.app.contactapp.domain.repository

import com.app.contactapp.data.api.ApiInterface
import com.app.contactapp.domain.model.response.ContactsData
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiInterface: ApiInterface
) {
    suspend fun getContacts(): Response<ContactsData> {
        return apiInterface.getContacts()
    }
}