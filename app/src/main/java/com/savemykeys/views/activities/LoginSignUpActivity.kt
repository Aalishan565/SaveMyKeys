package com.savemykeys.views.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.savemykeys.R
import com.savemykeys.views.adapters.ViewPagerAdapter
import com.savemykeys.views.fragments.LoginFragment
import com.savemykeys.views.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_login_sign_up.*


class LoginSignUpActivity : AppCompatActivity() {

    private val TAG = "LoginSignUpActivity"
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "LoginSignUpActivity")
        setContentView(R.layout.activity_login_sign_up)
        setupViewPager(viewPagerLoginSignUp)
        tabMode.setupWithViewPager(viewPagerLoginSignUp)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        Log.d(TAG, "setupViewPager")
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(LoginFragment(), getString(R.string.login_lbl))
        viewPagerAdapter.addFragment(SignUpFragment(), getString(R.string.signup_lbl))
        viewPager.adapter = viewPagerAdapter
    }
}
