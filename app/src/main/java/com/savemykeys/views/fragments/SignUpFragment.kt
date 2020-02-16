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
import com.savemykeys.viewmodel.KeyViewModel
import com.savemykeys.views.activities.HomeActivity
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(), View.OnClickListener {

    private val TAG = "SignUpFragment"
    private lateinit var loginSignUpViewModel: LoginSignUpViewModel
    private lateinit var keyViewModel: KeyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated()")
        loginSignUpViewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel::class.java)
        keyViewModel = ViewModelProviders.of(this).get(KeyViewModel::class.java)
        btnSignUp.setOnClickListener(this)
        loginSignUpViewModel.getSignUpStatus()
            .observe(this,
                Observer<Int> { message ->
                    context?.let { signUpStatus(message) }
                }
            )
    }

    override fun onClick(view: View?) {
        when (view) {
            btnSignUp -> {
                Log.d(TAG, "onClick btnSignUp()")
                loginSignUpViewModel.doSignUp(etPin.text.toString(), etConfirmPin.text.toString())
            }
        }
    }

    private fun signUpStatus(message: Int) {
        Log.d(TAG, "signUpStatus() ${getString(message)}")
        activity?.let { AppUtils.showToastMessageById(it, message) }
        if (resources.getString(R.string.signUpSuccess).equals(
                getString(message),
                ignoreCase = true
            )
        ) {
            keyViewModel.deleteAllKeys()
            val intent = Intent(activity, HomeActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

    }

}


