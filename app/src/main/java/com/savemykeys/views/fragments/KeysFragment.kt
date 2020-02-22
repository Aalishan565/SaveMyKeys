package com.savemykeys.views.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.entity.Key
import com.savemykeys.db.entity.Memory
import com.savemykeys.db.entity.Reminder
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.KeyViewModel
import com.savemykeys.views.adapters.KeyAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_keys.*

/**
 * A simple [Fragment] subclass.
 */
class KeysFragment : Fragment(), RecordDeleteListener {

    private lateinit var keyViewModel: KeyViewModel
    private var keyAdapter: KeyAdapter? = null
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
        keyAdapter = activity?.let { KeyAdapter(it, this) }
        rvKeys.adapter = keyAdapter
        keyViewModel = ViewModelProviders.of(this).get(KeyViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        Log.d(TAG, "loadData()")
        keyViewModel.getAllKeys().observe(this,
            Observer<List<Key>> { t ->
                keyAdapter!!.setDataToList(t!!)
                Log.d(TAG, "" + t.size)
            })

    }

    override fun deleteKey(key: Key) {
        Log.d(TAG, "deleteRecord() $key")
        keyViewModel.deleteKey(key)
        context?.let {
            AppUtils.showSnackBarMessageById(
                it, frameLayoutKey, R.string.recordDeletedSuccessfully
            )
        }
    }
}
