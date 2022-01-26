package com.example.contactsrecycler.app

import android.app.Application
import com.example.contactsrecycler.model.UsersService

class App: Application() {

    val usersService = UsersService()
}