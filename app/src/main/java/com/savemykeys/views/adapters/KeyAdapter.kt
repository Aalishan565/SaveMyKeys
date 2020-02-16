package com.savemykeys.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.savemykeys.R
import com.savemykeys.db.entity.Key
import com.savemykeys.utils.Constants
import com.savemykeys.views.activities.AddKeyActivity
import com.savemykeys.views.listeners.KeyDeleteListener
import kotlinx.android.synthetic.main.row_items_key.view.*


class KeyAdapter(
    private val context: Context,
    private val deleteListener: KeyDeleteListener
) :
    RecyclerView.Adapter<KeyAdapter.ViewHolder>() {

    private val TAG = "KeyAdapter"
    private var keyList: List<Key> = ArrayList()
    private lateinit var view: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder()")
        view = LayoutInflater.from(context).inflate(R.layout.row_items_key, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount ${keyList.size}")
        return keyList.size
    }

    fun setDataToList(keyList: List<Key>) {
        Log.d(TAG, "setDataToList() $keyList")
        this.keyList = keyList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder() position: $position")
        holder.tvUrl.text = keyList[position].url
        holder.tvUserName.text = keyList[position].userName
        holder.cardRowItem.setOnClickListener {
            val intent = Intent(context, AddKeyActivity::class.java)
            intent.putExtra(Constants.SINGLE_RECORD, keyList[position])
            intent.putExtra(
                Constants.ADD_KEY_SCREEN_TITLE,
                context.getString(R.string.record)
            )
            context.startActivity(intent)
        }
        holder.ivDelete.setOnClickListener {
            deleteListener.deleteKey(keyList[position])
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardRowItem = view.cardRowItem!!
        val tvUrl = view.tvUrl!!
        val tvUserName = view.tvUserName!!
        val ivDelete = view.ivDelete!!
    }
}