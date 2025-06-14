package com.app.contactapp.ui.contactScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.app.contactapp.R
import com.app.contactapp.databinding.ActivityMainBinding
import com.app.contactapp.databinding.ActivitySyncNewContactScreenBinding
import com.app.contactapp.domain.model.response.UserData
import com.app.contactapp.ui.addManually.AddManually
import com.app.contactapp.ui.contactScreen.adapter.ContactsAdapter
import com.app.contactapp.ui.syncScreen.SyncNewContactScreen
import com.app.contactapp.ui.syncScreen.adapter.SyncContactAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactsAdapter
    private var existingList = arrayListOf<UserData>()
    val addContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newContact = result.data?.getParcelableExtra<UserData>("newContact")
            newContact?.let {
                existingList.add(0, it)
                existingList.sortBy { contact -> contact.fullName?.lowercase() }
                adapter.submitList(existingList.toMutableList())
            }
        }
    }
    private val syncContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val newContacts = result.data?.getParcelableArrayListExtra<UserData>("syncedContacts")
            if (!newContacts.isNullOrEmpty()) {
                existingList.addAll(newContacts)
                existingList.sortBy { contact -> contact.fullName?.lowercase() }
                adapter.submitList(existingList.toMutableList())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ContactsAdapter { pos, model, src ->
            onItemClick(pos,model,src)
        }
        binding.rvContactList.adapter = adapter
        initClickListeners()
    }
    private fun initClickListeners()=binding.apply{

        fabSync.setOnClickListener {
            val intent = Intent(this@MainActivity, SyncNewContactScreen::class.java)
            syncContactLauncher.launch(intent)
        }
        fabAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, AddManually::class.java).apply {
                putExtra("editMode", false)
            }
            addContactLauncher.launch(intent)
        }


    }
    private fun onItemClick(pos:Int,data:UserData,src:String){

    }
}