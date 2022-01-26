package com.example.contactsrecycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsrecycler.app.App
import com.example.contactsrecycler.databinding.ActivityMainBinding
import com.example.contactsrecycler.model.User
import com.example.contactsrecycler.model.UserActionListener
import com.example.contactsrecycler.model.UsersListener
import com.example.contactsrecycler.model.UsersService
import com.example.contactsrecycler.model.adapter.UsersAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UsersAdapter

    private val usersService: UsersService
        get() = (applicationContext as App).usersService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter(object : UserActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                usersService.moveUser(user,moveBy)
            }
            override fun onUserDelete(user: User) {
                usersService.deleteUser(user)
            }
            override fun onUserDetails(user: User) {
                Toast.makeText(this@MainActivity, "User: ${user.name}", Toast.LENGTH_SHORT).show()
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        usersService.addListener(usersListener)
    }

    private val usersListener: UsersListener = {
        adapter.users = it
    }

    override fun onDestroy() {
        super.onDestroy()
        usersService.removeListener(usersListener)
    }
}