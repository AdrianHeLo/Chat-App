package com.adrianhelo.whatsappclone.presentation.LoginActivity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.adrianhelo.whatsappclone.R
import com.adrianhelo.whatsappclone.databinding.ActivityLoginBinding
import com.adrianhelo.whatsappclone.presentation.viewmodel.RepositoryViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val repositoryViewModel: RepositoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.buttonLoginActivity.setOnClickListener {
            repositoryViewModel.signUpAnonymousUser()
        }
    }
}