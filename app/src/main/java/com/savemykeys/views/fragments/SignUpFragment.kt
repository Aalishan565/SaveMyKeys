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
import com.savemykeys.viewmodel.RecordViewModel
import com.savemykeys.views.activities.HomeActivity
import com.savemykeys.views.listeners.SignUpViewListener
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(), View.OnClickListener, SignUpViewListener {

    private val TAG = "SignUpFragment"
    private lateinit var loginSignUpViewModel: LoginSignUpViewModel
    private lateinit var recordViewModel: RecordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")
        loginSignUpViewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel::class.java)
        recordViewModel = ViewModelProviders.of(this).get(RecordViewModel::class.java)
        loginSignUpViewModel.setSignUpViewListener(this)
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            btnSignUp -> {
                Log.d(TAG, "onClick btnSignUp")
                loginSignUpViewModel.doSignUp(etPin.text.toString(), etConfirmPin.text.toString())
            }
        }
    }

    override fun showPinDoesNotMatchError(pinDoesNotMatch: Int) {
        Log.d(TAG, "showPinDoesNotMatchError")
        activity?.let { AppUtils.showToastMessageById(it, pinDoesNotMatch) }
    }

    override fun signUpSuccess(signUpSuccessMessage: Int) {
        Log.d(TAG, "signUpSuccess")
        activity?.let { AppUtils.showToastMessageById(it, signUpSuccessMessage) }
        recordViewModel.deleteAllRecord();
        var intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    override fun showEmptyPinError(emptyPinMessage: Int) {
        Log.d(TAG, "showEmptyPinError")
        activity?.let { AppUtils.showToastMessageById(it, emptyPinMessage) }
    }
}


