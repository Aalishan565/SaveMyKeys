package com.savemykeys.views.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.savemykeys.R
import com.savemykeys.views.activities.HomeActivity
import com.savemykeys.views.impls.SignUpPresenterImpl
import com.savemykeys.views.presenters.SignUpPresenter
import com.savemykeys.views.listeners.SignUpViewListener
import com.savemykeys.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_sign_up.*

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(), View.OnClickListener, SignUpViewListener {


    private lateinit var signUpPresenter: SignUpPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signUpPresenter = context?.let { SignUpPresenterImpl(it, this) }!!
        btnSignUp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            btnSignUp -> {
                signUpPresenter.doSignUp(etPin.text.toString(), etConfirmPin.text.toString())

            }
        }
    }

    override fun showPinDoesNotMatchError(pinDoesNotMatch: Int) {
        activity?.let { AppUtils.showToastMessageById(it, pinDoesNotMatch) }
    }

    override fun signUpSuccess(signUpSuccessMessage: Int) {
        activity?.let { AppUtils.showToastMessageById(it, signUpSuccessMessage) }
        var intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }


    override fun showEmptyPinError(emptyPinMessage: Int) {
        activity?.let { AppUtils.showToastMessageById(it, emptyPinMessage) }
    }


}


