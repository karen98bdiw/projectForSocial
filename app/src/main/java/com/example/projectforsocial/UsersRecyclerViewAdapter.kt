package com.example.projectforsocial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.users_recycler_itemview.view.*

class UsersRecyclerViewAdapter(users:List<User>) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    val users:List<User>

    init {
        this.users = users
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.users_recycler_itemview,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.userNameView.setText(users.get(position).name)

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userNameView = itemView.userNameView

    }
}