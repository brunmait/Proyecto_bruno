package com.valle.msqlite8denoviembre

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VentaAdapter(private val context: Context, private val ventas: ArrayList<Venta>
) : RecyclerView.Adapter<VentaAdapter.VentaViewHolder>() {

    // Definir un lambda para manejar la selección
    var onVentaSelected: ((Venta) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VentaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_venta_view, parent, false)
        return VentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: VentaViewHolder, position: Int) {
        val venta = ventas[position]
        holder.bind(venta)
        holder.itemView.setOnClickListener { onVentaSelected?.invoke(venta) }
    }

    override fun getItemCount(): Int {
        return ventas.size
    }

    inner class VentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val codigoVentaTextView: TextView = itemView.findViewById(R.id.ilvCodigoVenta)
        private val descripcionTextView: TextView = itemView.findViewById(R.id.ilvDescripcion)
        private val precioTextView: TextView = itemView.findViewById(R.id.ilvPrecio)
        private val cantidadTextView: TextView = itemView.findViewById(R.id.ilvCantidad)
        private val fechaTextView: TextView = itemView.findViewById(R.id.ilvFecha) // Nuevo campo

        fun bind(venta: Venta) {
            codigoVentaTextView.text = venta.getCodigoVenta()
            descripcionTextView.text = venta.getDescripcion()
            precioTextView.text = venta.getPrecio().toString()
            cantidadTextView.text = venta.getCantidad().toString()
            fechaTextView.text = venta.getFecha() // Mostrar la fecha
        }
    }
}
