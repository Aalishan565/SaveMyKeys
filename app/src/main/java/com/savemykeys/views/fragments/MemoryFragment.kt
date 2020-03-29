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
import com.savemykeys.db.entity.Memory
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.MemoryViewModel
import com.savemykeys.views.adapters.MemoryAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_keys.*

/**
 * A simple [Fragment] subclass.
 */
class MemoryFragment : Fragment(), RecordDeleteListener {

    private lateinit var memoryViewModel: MemoryViewModel
    private var memoryAdapter: MemoryAdapter? = null
    private val TAG = "MemoryFragment"

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
        memoryAdapter = activity?.let { MemoryAdapter(it, this) }
        rvKeys.adapter = memoryAdapter
        memoryViewModel = ViewModelProviders.of(this).get(MemoryViewModel::class.java)
        loadData()

    }

    private fun loadData() {
        showProgressBar()
        Log.d(TAG, "loadData()")
        memoryViewModel.getAllMemory().observe(this,
            Observer<List<Memory>> { t ->
                hideProgressBar()
                memoryAdapter!!.setDataToList(t!!)
                Log.d(TAG, "" + t.size)
            })

    }

    override fun deleteKey(memory: Memory) {
        Log.d(TAG, "deleteRecord() $memory")
        memoryViewModel.deleteMemory(memory)
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
