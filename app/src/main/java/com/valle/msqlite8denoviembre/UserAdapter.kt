package com.valle.msqlite8denoviembre

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.random.Random

class UserAdapter(private val context: Context, private val users: MutableList<Usuario>) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var onUserSelected: ((Usuario) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_view, parent, false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val user = users[position]
        holder.bind(user)
        holder.itemView.setOnClickListener { onUserSelected?.invoke(user) }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun updateUsers(newUsers: List<Usuario>) {
        val diffCallback = UserDiffCallback(users, newUsers)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        users.clear()
        users.addAll(newUsers)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val carnet = itemView.findViewById<TextView>(R.id.ilvCarnet)
        private val name = itemView.findViewById<TextView>(R.id.ilvNombre)
        private val lastName = itemView.findViewById<TextView>(R.id.ilvApellido)
        private val phone = itemView.findViewById<TextView>(R.id.ilvTelefono)
        private val foto = itemView.findViewById<ImageView>(R.id.ilvFoto)

        fun bind(user: Usuario) {
            carnet.text = user.getCarnet()
            name.text = user.getFirstName()
            lastName.text = user.getLastName()
            phone.text = user.getPhone()

            Glide.with(itemView)
                .load(user.getFotoUrl() ?: "https://randomuser.me/api/portraits/men/${Random.nextInt(100)}.jpg")
                .apply(RequestOptions()
                    .placeholder(R.drawable.default_user_image) // Imagen de placeholder mientras carga
                    .error(R.drawable.default_user_image)      // Imagen en caso de error
                )
                .into(foto)
        }
    }

    class UserDiffCallback(
        private val oldList: List<Usuario>,
        private val newList: List<Usuario>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].getCarnet() == newList[newItemPosition].getCarnet()

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
