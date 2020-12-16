package com.persol.groceryguru

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class GroceriesAdapter(private val context: Context, private var groceries: MutableList<Grocery>) :
    RecyclerView.Adapter<GroceriesAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkbox : CheckBox
        val amountTV : TextView
        val moreIV : ImageView
        init {
            checkbox = itemView.findViewById(R.id.groceryCheckBox)
            amountTV = itemView.findViewById(R.id.amount_TV)
            moreIV = itemView.findViewById(R.id.more_IV)
        }

    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.grocery_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groceries.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.checkbox.text = groceries[position].name
        holder.checkbox.isChecked = groceries[position].bought
        holder.amountTV.text = groceries[position].amount.toString()
        holder.moreIV.setOnClickListener{
            val popup = PopupMenu(context, holder.moreIV)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.grocery_options, popup.menu)
            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.delete) {
                    val db = DB(context)
                    groceries.removeAt(position)
                    db.saveGroceries(groceries)
                    this.notifyItemRemoved(position)
                    Toast.makeText(context, "Grocery removed", Toast.LENGTH_SHORT).show()
                }
                false
            }
            popup.show()
        }

        holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            val db = DB(context)
            groceries[position].bought = b
            db.saveGroceries(groceries)
        })

    }

}