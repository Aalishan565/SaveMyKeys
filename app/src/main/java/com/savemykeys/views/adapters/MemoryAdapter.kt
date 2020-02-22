package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    RecyclerView.Adapter<MemoryAdapter.ViewHolder>() {

    private val TAG = "MemoryAdapter"
    private var memoryList: List<Memory> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view =
            LayoutInflater.from(context).inflate(R.layout.row_items_memory_reminder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${memoryList.size}")
        return memoryList.size
    }

    fun setDataToList(memoryList: List<Memory>) {
        Log.d(TAG, "setDataToList() $memoryList")
        this.memoryList = memoryList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvTitle.text = memoryList[position].memoryTitle
        holder.tvDate.text = memoryList[position].memoryDate
        holder.tvNote.text = memoryList[position].memoryNote
        if (holder.rlMoreOrLess.isVisible) {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_less_black_24dp)
        } else {
            holder.ivMoreLess.setImageResource(R.drawable.ic_expand_more_black_24dp)
        }
        holder.ivEdit.setOnClickListener {
            val intent = Intent(context, AddMemoryActivity::class.java)
            intent.putExtra(Constants.SINGLE_RECORD, memoryList[position])
            intent.putExtra(
                Constants.ADD_KEY_SCREEN_TITLE,
                context.getString(R.string.record)
            )
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteKey(memoryList[position])
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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.tvTitle!!
        val tvDate = view.tvDate!!
        val ivDelete = view.ivDelete!!
        val ivMoreLess = view.ivMoreLess!!
        val ivEdit = view.ivEdit!!
        val rlMoreOrLess = view.rlMoreOrLess!!
        val tvNote = view.tvNote!!
    }
}