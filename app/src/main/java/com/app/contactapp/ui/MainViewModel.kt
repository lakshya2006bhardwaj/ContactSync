package com.app.contactapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.contactapp.domain.model.response.ContactsData
import com.app.contactapp.domain.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _contacts = MutableLiveData<Response<ContactsData>>()
    val contacts: LiveData<Response<ContactsData>> = _contacts

    fun fetchContacts() {
        viewModelScope.launch {
            try {
                val response = repository.getContacts()
                _contacts.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
