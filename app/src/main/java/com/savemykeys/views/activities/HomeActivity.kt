package com.savemykeys.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.savemykeys.R
import com.savemykeys.utils.Constants
import com.savemykeys.views.adapters.ViewPagerAdapter
import com.savemykeys.views.fragments.KeysFragment
import com.savemykeys.views.fragments.MemoryFragment
import com.savemykeys.views.fragments.ReminderFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var fragmentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbarHome)
        Log.d(TAG, "onCreate()")
        setupViewPager(viewPagerHome)
        tabHome.setupWithViewPager(viewPagerHome)
        viewPagerHome.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                fragmentPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        fabAddHome.setOnClickListener {

            if (fragmentPosition == 0) {
                val intent = Intent(this, AddMemoryActivity::class.java)
                intent.putExtra(
                    Constants.ADD_MEMORY_SCREEN_TITLE, getString(R.string.addMemory)
                )
                startActivity(intent)
            } else if (fragmentPosition == 1) {
                val intent = Intent(this, AddReminderActivity::class.java)
                intent.putExtra(
                    Constants.ADD_REMINDER_SCREEN_TITLE, getString(R.string.addReminder)
                )
                startActivity(intent)
            } else {
                val intent = Intent(this, AddKeyActivity::class.java)
                intent.putExtra(
                    Constants.ADD_KEY_SCREEN_TITLE, getString(R.string.addKey)
                )
                startActivity(intent)
            }

        }
    }

    private fun setupViewPager(viewPager: ViewPager) {
        Log.d(TAG, "setupViewPager()")
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(MemoryFragment(), getString(R.string.memories))
        viewPagerAdapter.addFragment(ReminderFragment(), getString(R.string.reminders))
        viewPagerAdapter.addFragment(KeysFragment(), getString(R.string.keys))
        viewPager.adapter = viewPagerAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "onCreateOptionsMenu()")
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected()")
        when (item.itemId) {
            /*R.id.menu_search -> {
            }*/
            R.id.menu_share -> {
                shareApp()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareApp() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val shareBody =
            "https://play.google.com/store/apps/details?id=${application.packageName}"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Save My Keys App")
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
        startActivity(Intent.createChooser(sharingIntent, "Share Via :"))
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume()")
    }
}
