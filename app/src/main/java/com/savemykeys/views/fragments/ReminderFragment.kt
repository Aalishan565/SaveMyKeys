package com.savemykeys.views.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.entity.Reminder
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.ReminderViewModel
import com.savemykeys.views.adapters.ReminderAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_keys.*

/**
 * A simple [Fragment] subclass.
 */
class ReminderFragment : Fragment(), RecordDeleteListener {

    private lateinit var reminderViewModel: ReminderViewModel
    private var reminderAdapter: ReminderAdapter? = null
    private val TAG = "KeysFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keys, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvKeys.layoutManager = LinearLayoutManager(activity)
        reminderAdapter = activity?.let { ReminderAdapter(it, this) }
        rvKeys.adapter = reminderAdapter
        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        showProgressBar()
        Log.d(TAG, "loadData()")
        reminderViewModel.getAllReminder().observe(this,
            Observer<List<Reminder>> { t ->
                hideProgressBar()
                reminderAdapter!!.setDataToList(t!!)
                Log.d(TAG, "" + t.size)
            })

    }

    override fun deleteKey(reminder: Reminder) {
        Log.d(TAG, "deleteRecord() $reminder")
        reminderViewModel.deleteReminder(reminder)
        context?.let {
            AppUtils.showSnackBarMessageById(
                it, frameLayoutKey, R.string.recordDeletedSuccessfully
            )
        }
    }

    private fun hideProgressBar() {
        if (null != progressBar && progressBar.isVisible) {
            progressBar.visibility = View.GONE
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

}
