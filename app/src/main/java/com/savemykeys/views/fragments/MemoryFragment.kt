package com.savemykeys.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.savemykeys.R
import com.savemykeys.db.entity.Memory
import com.savemykeys.utils.AppUtils
import com.savemykeys.viewmodel.MemoryViewModel
import com.savemykeys.views.activities.HomeActivity
import com.savemykeys.views.adapters.MemoryAdapter
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.fragment_keys.*

class MemoryFragment : Fragment(), RecordDeleteListener, SearchView.OnQueryTextListener {

    private lateinit var searchView: SearchView
    private lateinit var memoryViewModel: MemoryViewModel
    private var memoryAdapter: MemoryAdapter? = null
    private val TAG = "MemoryFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        Log.d(TAG, "onCreateOptionsMenu()")
        inflater?.inflate(R.menu.main_menu, menu)
        val searchItem = menu!!.findItem(R.id.menu_search)
        searchView = searchItem.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE;
        searchView.queryHint = "Search "
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected()")
        when (item.itemId) {
            R.id.menu_search -> {
            }
            R.id.menu_share -> {
                (activity as HomeActivity?)?.shareApp()
            }
            else -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        memoryAdapter?.filter?.filter(newText)
        return true
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

    override fun onDestroyView() {
        super.onDestroyView()
        rvKeys.adapter=null
    }
}
