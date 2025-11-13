package com.example.crm.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crm.data.Client
import com.example.crm.databinding.ItemClientBinding

class ClientAdapter(
    private var items: MutableList<Client>,
    private val onEdit: (Client) -> Unit,
    private val onDelete: (Client) -> Unit
) : RecyclerView.Adapter<ClientAdapter.VH>() {

    inner class VH(val b: ItemClientBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemClientBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = items[position]
        with(holder.b) {
            tvName.text = c.name
            tvEmail.text = c.email
            tvPhone.text = c.phone

            root.setOnClickListener { onEdit(c) }
            root.setOnLongClickListener {
                AlertDialog.Builder(root.context)
                    .setTitle("Eliminar")
                    .setMessage("¿Seguro que quieres eliminar a ${c.name}?")
                    .setPositiveButton("Sí") { _, _ -> onDelete(c) }
                    .setNegativeButton("No", null)
                    .show()
                true
            }
        }
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: MutableList<Client>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun removeById(id: Long) {
        val idx = items.indexOfFirst { it.id == id }
        if (idx >= 0) {
            items.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }
}
