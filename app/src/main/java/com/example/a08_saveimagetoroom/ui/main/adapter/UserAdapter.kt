package com.example.a08_saveimagetoroom.ui.main.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a08_saveimagetoroom.data.local.entity.User
import com.example.a08_saveimagetoroom.databinding.ItemUserBinding
import com.example.a08_saveimagetoroom.ui.main.UserViewModel

class UserAdapter(private val userViewModel: UserViewModel) : ListAdapter<User, UserAdapter.UserViewHolder>(UserComparator()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(ItemUserBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.delete(current)
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root), ViewHolder{
        override fun bind(user: User) {
            binding.name.text = user.name
            Glide.with(itemView.context).load(user.path).into(binding.image)
        }

        override fun delete(user: User) {
            binding.root.setOnLongClickListener {
                // Initialize an instance of the AlertDialog.Builder class
                val builder = AlertDialog.Builder(itemView.context)

                builder.setMessage("This is an alert dialog")

                builder.setPositiveButton("DELETE") { dialog, _ ->
                    userViewModel.delete(user)
                }

                builder.setNegativeButton("CANCEL"){dialog, _ ->
                    dialog.cancel()
                }

                val alertDialog = builder.create()
                alertDialog.show()
                true
            }
        }

    }

    class UserComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id && oldItem.name == newItem.name
        }
    }
}