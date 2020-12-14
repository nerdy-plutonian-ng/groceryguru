package com.persol.groceryguru

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class GroceriesAdapter (private val context : Context, private var groceries : MutableList<Grocery>,
private val boughtGroceries : MutableList<Grocery>) :
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
        val view = inflater.inflate(R.layout.grocery_item, parent,false)
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
            Log.d("GG", "onBindViewHolder: More clicked")
            Toast.makeText(context, "CLicked", Toast.LENGTH_SHORT).show()
        }

        holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
            if(b){
                val boughtGrocery = groceries[position]
                groceries.removeAt(position)
                boughtGrocery.bought = true
                boughtGroceries.add(boughtGrocery)
            } else {
                val boughtGrocery = groceries[position]
                groceries.removeAt(position)
                boughtGrocery.bought = true
                boughtGroceries.add(boughtGrocery)
            }
        })

    }
}