package com.savemykeys.views.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.LoginSignUpViewModel
import com.savemykeys.views.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val TAG = "LoginFragment"
    private lateinit var loginSignUpViewModel: LoginSignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated()")
        loginSignUpViewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel::class.java)
        btnLogin.setOnClickListener {
            loginSignUpViewModel.doLogin(etPin.text.toString())

        }
        loginSignUpViewModel.getLoginStatus()
            .observe(this,
                Observer<Int> { message ->
                    context?.let { loginStatus(message) }
                }
            )
    }

    private fun loginStatus(message: Int) {
        Log.d(TAG, "loginStatus() $message")
        context?.let { AppUtils.showToastMessageById(it, message) }
        if (resources.getString(R.string.loginSuccess).equals(
                getString(message),
                ignoreCase = true
            )
        ) {
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}
