package com.example.uas_waroengujang.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.model.Waitress
import com.example.uas_waroengujang.viewmodel.LoginViewModel
import com.example.uas_waroengujang.viewmodel.WaitressViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel:LoginViewModel
    private lateinit var  waitressModel: WaitressViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.login()
        waitressModel = ViewModelProvider(this).get(WaitressViewModel::class.java)

        btnSignIn.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            val listUser = viewModel.usersLD.value

            if (listUser != null) {
                var isUserFound = false
                for (user in listUser) {
                    waitressModel.addWaitress(Waitress(id = "${user.id}", name = "${user.name}", username = "${user.username}", password = "${user.password}", workSince = "${user.workSince}", photoUrl = "${user.photoUrl}"))
                    if (user.username == username && user.password == password) {
                        isUserFound = true
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("waitressid", user.id)
                        startActivity(intent)
                        finish()
                    }
                }
                if (!isUserFound) {
                    Toast.makeText(this, "Username atau password salah",  Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "Eror",  Toast.LENGTH_SHORT).show()
            }
        }

    }
}
