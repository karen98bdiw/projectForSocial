package com.example.projectforsocial

import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.message_recycler_item.view.*

class MessageRecyclerViewAdapter(messages:List<Message>) : RecyclerView.Adapter<MessageRecyclerViewAdapter.ViewHolder>() {

    val data:List<Message>
    val mAuth:FirebaseAuth = FirebaseAuth.getInstance()

    init {
        this.data = messages
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.message_recycler_item,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val curentMessage = data.get(0)


        holder.messageView.setText(data.get(position).message)



    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
           val messageView = itemView.showMessageView
    }
}