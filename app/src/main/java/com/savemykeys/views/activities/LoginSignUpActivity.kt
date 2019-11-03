package com.savemykeys.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.savemykeys.views.adapters.ViewPagerAdapter
import com.savemykeys.views.fragments.LoginFragment
import com.savemykeys.views.fragments.SignUpFragment
import kotlinx.android.synthetic.main.activity_login_sign_up.*
import androidx.viewpager.widget.ViewPager
import com.savemykeys.R


class LoginSignUpActivity : AppCompatActivity() {

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_sign_up)
        setupViewPager(viewPagerLoginSignUp)
        tabMode.setupWithViewPager(viewPagerLoginSignUp)

    }

    private fun setupViewPager(viewPager: ViewPager) {
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(LoginFragment(), getString(R.string.login_lbl))
        viewPagerAdapter.addFragment(SignUpFragment(), getString(R.string.signup_lbl))
        viewPager.adapter = viewPagerAdapter
    }
}
