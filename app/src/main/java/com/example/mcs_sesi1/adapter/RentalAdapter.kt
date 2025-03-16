package com.example.mcs_sesi1.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mcs_sesi1.R
import com.example.mcs_sesi1.models.RentalRoom
import androidx.core.content.ContextCompat

class RentalAdapter(private val roomList: List<RentalRoom>) :
    RecyclerView.Adapter<RentalAdapter.RentalViewHolder>() {

    class RentalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRoomId: TextView = itemView.findViewById(R.id.tv_room_id)
        val tvConsoleType: TextView = itemView.findViewById(R.id.tv_console_type)
        val tvRentalPrice: TextView = itemView.findViewById(R.id.tv_price)
        val tvAvailable: TextView = itemView.findViewById(R.id.tv_status)
        val btnBook: Button = itemView.findViewById(R.id.btn_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rental_room, parent, false)
        return RentalViewHolder(view)
    }

    override fun onBindViewHolder(holder: RentalViewHolder, position: Int) {
        val room = roomList[position]
        holder.tvRoomId.text = "Room: ${room.roomId}"
        holder.tvConsoleType.text = "Console: ${room.consoleType}"
        holder.tvRentalPrice.text = "Price: ${room.rentalPrice}/hour"
        holder.tvAvailable.text = if (room.status) "Available" else "Not Available"

        val context = holder.itemView.context
        val availableColor = ContextCompat.getColor(context, R.color.available_green)
        val notAvailableColor = ContextCompat.getColor(context, R.color.not_available_red)
        holder.tvAvailable.setTextColor(if (room.status) availableColor else notAvailableColor)

        if (room.status) {
            holder.btnBook.setOnClickListener {
                Toast.makeText(context, "Booking Room ${room.roomId}", Toast.LENGTH_SHORT).show()

            }
        } else {
            holder.btnBook.isEnabled = false
            holder.btnBook.setBackgroundColor(ContextCompat.getColor(context, R.color.gray))
        }
    }



    override fun getItemCount(): Int = roomList.size
}
