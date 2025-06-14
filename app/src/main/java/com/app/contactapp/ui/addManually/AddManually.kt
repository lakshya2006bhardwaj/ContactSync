package com.app.contactapp.ui.addManually

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.contactapp.R
import com.app.contactapp.databinding.ActivityAddManuallyBinding
import com.app.contactapp.databinding.ActivityMainBinding
import com.app.contactapp.domain.model.response.UserData
import com.app.contactapp.ui.syncScreen.SyncNewContactScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddManually : AppCompatActivity() {
    private lateinit var binding: ActivityAddManuallyBinding
    var isEdit = false
    var contactToEdit:UserData? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddManuallyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isEdit=intent.getBooleanExtra("editMode", false)
        contactToEdit = intent.getParcelableExtra<UserData?>("contactToEdit")?:null
        if (isEdit && contactToEdit != null) {
            val nameParts = contactToEdit?.fullName?.trim()?.split(" ")

            val firstName = nameParts?.getOrNull(0) ?: ""
            val lastName = nameParts?.drop(1)?.joinToString(" ") ?: ""

            binding.etFirstName.setText(firstName)
            binding.etSurname.setText(lastName)
            binding.etMail.setText(contactToEdit?.email)
            binding.etPhone.setText(contactToEdit?.phone)
            binding.etCompany.setText(contactToEdit?.course)
            binding.tvAddContact.text = "Edit Contact"
        } else {
            binding.tvAddContact.text = "Add Contact"

        }

        initClickListeners()

    }

    private fun initClickListeners() = binding.apply {

        btnSave.setOnClickListener {
            if (isEdit) {
                val updatedContact = UserData(
                    id = contactToEdit?.id.toString(),
                    fullName = etFirstName.text.toString().trim() + " " + etSurname.text.toString().trim(),
                    email = binding.etMail.text.toString(),
                    phone = binding.etPhone.text.toString(),
                    course = binding.etCompany.text.toString(),
                    enrolledOn = contactToEdit?.enrolledOn.toString()
                )

                val resultIntent = Intent().apply {
                    putExtra("editedContact", updatedContact)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                val name =
                    etFirstName.text.toString().trim() + " " + etSurname.text.toString().trim()
                val phone = etPhone.text.toString().trim()
                val email = etMail.text.toString().trim()
                val job = etCompany.text.toString().trim()

                if (name.isEmpty()) {
                    etFirstName.error = "Name is required"
                    return@setOnClickListener
                }

                if (phone.isEmpty()) {
                    etPhone.error = "Phone number is required"
                    return@setOnClickListener
                }

                if (phone.length != 10 || !phone.all { it.isDigit() }) {
                    etPhone.error = "Enter a valid 10-digit number"
                    return@setOnClickListener
                }

                val newContact = UserData(
                    fullName = name,
                    email = email,
                    phone = phone,
                    course = job,
                    id = "151",
                    enrolledOn = "14-06-2025"
                )

                val resultIntent = Intent().apply {
                    putExtra("newContact", newContact)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}