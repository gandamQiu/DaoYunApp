package com.example.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(this,R.layout.activity_login)
        binding.data = ViewModelProvider(this). get(LoginViewModel::class.java)
        binding.lifecycleOwner = this
        //binding.data!!.setTitle("登录")

        //binding.loginToolbar.title = ""
        setSupportActionBar(binding.loginToolbar)
        val navController = this.findNavController(R.id.loginNavHostFragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        NavigationUI.setupWithNavController(binding.loginToolbar,navController,appBarConfiguration)
    }
}