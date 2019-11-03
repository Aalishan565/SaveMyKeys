package com.savemykeys.views.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.savemykeys.R
import com.savemykeys.views.activities.HomeActivity
import com.savemykeys.views.impls.LoginPresenterImpl
import com.savemykeys.views.listeners.LoginViewListener
import com.savemykeys.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), LoginViewListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnLogin.setOnClickListener {
            var loginPresenter = context?.let { LoginPresenterImpl(it, this) }
            loginPresenter?.doLogin(etPin.text.toString())
        }

    }

    override fun loginSuccess(loginSuccessMessage: Int) {
        context?.let { AppUtils.showToastMessageById(it, loginSuccessMessage) }
        var intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun loginFail(loginFailMessage: Int) {
        context?.let { AppUtils.showToastMessageById(it, loginFailMessage) }
    }

    override fun showEmptyPinError(emptyPinMessage: Int) {
        context?.let { AppUtils.showToastMessageById(it, emptyPinMessage) }
    }

}
