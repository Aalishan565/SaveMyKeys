package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.savemykeys.R
import com.savemykeys.db.entity.Key
import com.savemykeys.utils.Constants
import com.savemykeys.views.activities.AddKeyActivity
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.row_items_memory_reminder.view.*

class KeyAdapter(
    private val context: Context,
    private val deleteListener: RecordDeleteListener
) :
    RecyclerView.Adapter<KeyAdapter.ViewHolder>(), Filterable {

    private val TAG = "KeyAdapter"
    var filteredKeyList: List<Key> = ArrayList()
    var completeKeyList: List<Key> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view =
            LayoutInflater.from(context).inflate(R.layout.row_items_memory_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${filteredKeyList.size}")
        return filteredKeyList.size
    }

    fun setDataToList(keyList: List<Key>) {
        Log.d(TAG, "setDataToList() $keyList")
        this.filteredKeyList = keyList
        this.completeKeyList = keyList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvUrl.text = filteredKeyList[position].url
        holder.tvUserName.text = filteredKeyList[position].userName
        holder.tvNote.text = filteredKeyList[position].note
        if (holder.rlMoreOrLess.isVisible) {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_less_black_24dp)
        } else {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_more_black_24dp)
        }
        holder.ivEdit.setOnClickListener {
            navigateToNextPage(position)
        }
        holder.cardRowItem.setOnClickListener {
            navigateToNextPage(position)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteKey(filteredKeyList[position])
        }
        holder.ivMoreLess.setOnClickListener {
            if (holder.rlMoreOrLess.isVisible) {
                holder.ivMoreLess.setImageResource(R.drawable.ic_expand_more_black_24dp)
                holder.rlMoreOrLess.visibility = View.GONE
            } else {
                holder.ivMoreLess.setImageResource(R.drawable.ic_expand_less_black_24dp)
                holder.rlMoreOrLess.visibility = View.VISIBLE
            }
        }
    }

    private fun navigateToNextPage(position: Int) {
        val intent = Intent(context, AddKeyActivity::class.java)
        intent.putExtra(Constants.SINGLE_RECORD, filteredKeyList[position])
        intent.putExtra(
            Constants.ADD_KEY_SCREEN_TITLE,
            context.getString(R.string.editKey)
        )
        context.startActivity(intent)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUrl = view.tvTitle!!
        val tvNote = view.tvNote!!
        val tvUserName = view.tvDate!!
        val ivDelete = view.ivDelete!!
        val ivMoreLess = view.ivMoreLess!!
        val ivEdit = view.ivEdit!!
        val rlMoreOrLess = view.rlMoreOrLess!!
        val cardRowItem = view.cardRowItem!!
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSearch: CharSequence?): FilterResults {
                if (charSearch != null) {
                    if (charSearch.isEmpty()) {
                        filteredKeyList = completeKeyList
                    } else {
                        val searchResultKeys = ArrayList<Key>()
                        for (keyItem in filteredKeyList) {
                            if (keyItem.url.contains(charSearch, true)) {
                                searchResultKeys.add(keyItem)
                            }
                        }
                        filteredKeyList = searchResultKeys
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredKeyList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredKeyList = results?.values as ArrayList<Key>
                notifyDataSetChanged()
            }

        }
    }
}
