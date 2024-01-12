package com.example.uas_waroengujang.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.uas_waroengujang.R
import com.example.uas_waroengujang.model.Waitress
import com.example.uas_waroengujang.viewmodel.LoginViewModel
import com.example.uas_waroengujang.viewmodel.WaitressViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var waitressModel: WaitressViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.login()
        waitressModel = ViewModelProvider(this).get(WaitressViewModel::class.java)

        if (isUserLoggedIn()) {
            redirectToMainActivity()
            return
        }

        btnSignIn.setOnClickListener {
            val username = txtUsername.text.toString()
            val password = txtPassword.text.toString()

            val listUser = viewModel.usersLD.value

            if (listUser != null) {
                var isUserFound = false
                for (user in listUser) {
                    waitressModel.addWaitress(
                        Waitress(
                            id = "${user.id}",
                            name = "${user.name}",
                            username = "${user.username}",
                            password = "${user.password}",
                            workSince = "${user.workSince}",
                            photoUrl = "${user.photoUrl}"
                        )
                    )
                    if (user.username == username && user.password == password) {
                        isUserFound = true

                        saveUserSession(user.id)

                        redirectToMainActivity()
                        finish()
                    }
                }
                if (!isUserFound) {
                    Toast.makeText(this, "Username atau password salah", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUserSession(userId: String) {
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.putString("user_id", userId)
        editor.apply()
    }

    private fun isUserLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    private fun redirectToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
