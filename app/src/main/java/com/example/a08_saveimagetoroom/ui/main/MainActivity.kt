package com.example.a08_saveimagetoroom.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.a08_saveimagetoroom.R
import com.example.a08_saveimagetoroom.data.local.entity.User
import com.example.a08_saveimagetoroom.databinding.ActivityMainBinding
import com.example.a08_saveimagetoroom.ui.add.AddActivity
import com.example.a08_saveimagetoroom.ui.main.adapter.UserAdapter

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as UserApplication).userRepo)
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            // get Data user
            val user = it.data?.getParcelableExtra<User>("data_user")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(userViewModel)
        userViewModel.allUser.observe(this, Observer {list ->
            list.let {
                userAdapter.submitList(it)
            }
        })
        setupRecyclerview()

        // Caller to AddActivity
        setupToAddActivity()
    }

    private fun setupRecyclerview(){
        binding.recyclerview.adapter = userAdapter
    }

    private fun setupToAddActivity(){
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            getResult.launch(intent)
        }
    }
}