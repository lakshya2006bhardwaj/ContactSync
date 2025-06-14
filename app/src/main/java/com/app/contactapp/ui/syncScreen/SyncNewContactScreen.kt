package com.app.contactapp.ui.syncScreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.contactapp.R
import com.app.contactapp.databinding.ActivitySyncNewContactScreenBinding
import com.app.contactapp.domain.model.response.UserData
import com.app.contactapp.presentation.viewmodel.MainViewModel
import com.app.contactapp.ui.addManually.AddManually
import com.app.contactapp.ui.contactScreen.MainActivity
import com.app.contactapp.ui.syncScreen.adapter.SyncContactAdapter
import com.google.type.Date
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class SyncNewContactScreen : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding:ActivitySyncNewContactScreenBinding
    private lateinit var adapter: SyncContactAdapter
    private var editPosition: Int = -1

    private val editContactLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val updatedContact = result.data?.getParcelableExtra<UserData>("editedContact")
            if (updatedContact != null && editPosition != -1) {
                val newList = adapter.currentList.toMutableList()
                newList[editPosition] = updatedContact
                adapter.submitList(newList)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       
        binding = ActivitySyncNewContactScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = SyncContactAdapter { pos, model, src ->
            onItemClick(pos,model,src)
        }
        binding.rvSyncedContacts.adapter = adapter
        viewModel.fetchContacts()
        initClickListeners()
        syncContactObserver()


    }
    private fun initClickListeners()=binding.apply{
        btnSync.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val syncedList = adapter.currentList
            val resultIntent = Intent().apply {
                putParcelableArrayListExtra("syncedContacts", ArrayList(syncedList))
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
    private fun syncContactObserver()=binding.apply{
        viewModel.contacts.observe(this@SyncNewContactScreen) { response ->
            binding.progressBar.visibility = View.GONE
            if (response.isSuccessful && response.body()?.success == true) {
                val list = response.body()?.Data
                adapter.submitList(list?.users)
                // Update sync date text
                val currentDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(
                    java.util.Date()
                )
                binding.tvLastSynced.text = "Last synced: $currentDate"
            } else {
                Toast.makeText(this@SyncNewContactScreen, "Failed to fetch contacts", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun onItemClick(pos:Int,data:UserData,src:String){
        when(src){
            "EDIT"->{
                editPosition = pos
                val intent = Intent(this, AddManually::class.java).apply {
                    putExtra("editMode", true)
                    putExtra("contactToEdit", data)
                }
                editContactLauncher.launch(intent)
            }

        }

    }
}