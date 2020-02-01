package com.savemykeys.views.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.savemykeys.R
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.LoginSignUpViewModel
import com.savemykeys.views.activities.HomeActivity
import com.savemykeys.views.listeners.LoginViewListener
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginViewListener {

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
        loginSignUpViewModel.setLoginViewListener(this)
        btnLogin.setOnClickListener {
            loginSignUpViewModel.doLogin(etPin.text.toString())
        }
    }

    override fun loginSuccess(loginSuccessMessage: Int) {
        Log.d(TAG, "loginSuccess()")
        context?.let { AppUtils.showToastMessageById(it, loginSuccessMessage) }
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun loginFail(loginFailMessage: Int) {
        Log.d(TAG, "loginFail() loginFailMessage: $loginFailMessage")
        context?.let { AppUtils.showToastMessageById(it, loginFailMessage) }
    }

    override fun showEmptyPinError(emptyPinMessage: Int) {
        Log.d(TAG, "showEmptyPinError() emptyPinMessage $emptyPinMessage ")
        context?.let { AppUtils.showToastMessageById(it, emptyPinMessage) }
    }

}
