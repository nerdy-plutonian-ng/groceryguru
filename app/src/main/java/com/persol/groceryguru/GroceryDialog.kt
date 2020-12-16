package com.persol.groceryguru

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class GroceryDialog(val add : Boolean,val groceries : MutableList<Grocery>) : DialogFragment() {

    private lateinit var db: DB;

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;
            val view = inflater.inflate(R.layout.grocery_card,null);
            val nameTIL = view.findViewById<TextInputLayout>(R.id.name_TIL)
            val amountTIL = view.findViewById<TextInputLayout>(R.id.amount_TIL)
            val nameET = view.findViewById<TextInputEditText>(R.id.name_ET)
            val amountET = view.findViewById<TextInputEditText>(R.id.amount_ET)
            val addEditButton = view.findViewById<MaterialButton>(R.id.addEditGroceryBtn)

            db = DB(activity!!)

            addEditButton.setOnClickListener(View.OnClickListener {

                if (nameET.text.isNullOrEmpty()) {
                    nameTIL.error = getString(R.string.empty_error)
                } else if (amountET.text.isNullOrEmpty()) {
                    amountTIL.error = getString(R.string.empty_error)
                } else {
                    val grocery = Grocery(generateID(), nameET.text.toString().trim(), amountET.text.toString().trim().toDouble(), false)
                    if (db.addToGroceries(grocery)) {
                        groceries.add(grocery)
                        Log.d("GG", "onCreateDialog: Grocery added")
                        Toast.makeText(activity!!, "Grocery added", Toast.LENGTH_SHORT).show()
                        (activity as MainActivity).notifyInsertion()
                        dialog?.cancel()
                    } else {
                        Toast.makeText(activity!!, "Failed to add", Toast.LENGTH_SHORT).show()
                    }
                }

            })

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                    .setTitle(if(add) getString(R.string.add_grocery) else getString(R.string.edit_grocery))
                    .setNegativeButton(android.R.string.cancel,
                            DialogInterface.OnClickListener { dialog, id ->
                                getDialog()?.cancel()
                            })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun generateID() : Int {
        return (Math.random() * 9000000).toInt() + 1000000
    }

}