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
import com.savemykeys.db.entity.Memory
import com.savemykeys.utils.Constants
import com.savemykeys.views.activities.AddMemoryActivity
import com.savemykeys.views.listeners.RecordDeleteListener
import kotlinx.android.synthetic.main.row_items_memory_reminder.view.*

class MemoryAdapter(
    private val context: Context,
    private val deleteListener: RecordDeleteListener
) :
    RecyclerView.Adapter<MemoryAdapter.ViewHolder>(), Filterable {

    private val TAG = "MemoryAdapter"
    private var filteredMemoryList: List<Memory> = ArrayList()
    private var completeMemoryList: List<Memory> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view =
            LayoutInflater.from(context).inflate(R.layout.row_items_memory_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${filteredMemoryList.size}")
        return filteredMemoryList.size
    }

    fun setDataToList(memoryList: List<Memory>) {
        Log.d(TAG, "setDataToList() $memoryList")
        this.filteredMemoryList = memoryList
        this.completeMemoryList = memoryList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvTitle.text = filteredMemoryList[position].memoryTitle
        holder.tvDate.text = filteredMemoryList[position].memoryDate
        holder.tvNote.text = filteredMemoryList[position].memoryNote
        if (holder.rlMoreOrLess.isVisible) {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_less_black_24dp)
        } else {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_more_black_24dp)
        }
    }

    inner class ViewHolder constructor(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val tvTitle = view.tvTitle!!
        val tvDate = view.tvDate!!
        val ivDelete = view.ivDelete!!
        val ivMoreLess = view.ivMoreLess!!
        val ivEdit = view.ivEdit!!
        val rlMoreOrLess = view.rlMoreOrLess!!
        val tvNote = view.tvNote!!

        init {
            ivDelete.setOnClickListener(this)
            ivMoreLess.setOnClickListener(this)
            ivEdit.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            Log.d("v?.id", "${v?.id}")
            when (v?.id) {
                R.id.ivEdit -> {
                    val intent = Intent(context, AddMemoryActivity::class.java)
                    intent.putExtra(Constants.SINGLE_RECORD, filteredMemoryList[adapterPosition])
                    intent.putExtra(
                        Constants.ADD_MEMORY_SCREEN_TITLE,
                        context.getString(R.string.editMemory)
                    )
                    context.startActivity(intent)
                }
                R.id.ivDelete -> deleteListener.deleteKey(filteredMemoryList[adapterPosition])
                R.id.ivMoreLess -> {
                    if (rlMoreOrLess.isVisible) {
                        ivMoreLess.setImageResource(R.drawable.ic_expand_more_black_24dp)
                        rlMoreOrLess.visibility = View.GONE
                    } else {
                        ivMoreLess.setImageResource(R.drawable.ic_expand_less_black_24dp)
                        rlMoreOrLess.visibility = View.VISIBLE
                    }
                }
                else -> {
                }
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSearch: CharSequence?): FilterResults {
                if (charSearch != null) {
                    if (charSearch.isEmpty()) {
                        filteredMemoryList = completeMemoryList
                    } else {
                        val searchResultMemory = ArrayList<Memory>()
                        for (memoryItem in filteredMemoryList) {
                            if (memoryItem.memoryTitle.contains(charSearch, true)) {
                                searchResultMemory.add(memoryItem)
                            }
                        }
                        filteredMemoryList = searchResultMemory
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredMemoryList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredMemoryList = results?.values as ArrayList<Memory>
                notifyDataSetChanged()
            }

        }
    }
}