package com.persol.groceryguru

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class DB (val context : Context) {

    val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

    fun getName () : String? {
        return sharedPref.getString(context.getString(R.string.name), "defaultValue")
    }

    fun getGroceries () : MutableList<Grocery> {
        return try {
            val groceries = mutableListOf<Grocery>()
            val groceriesStr = sharedPref.getString(context.getString(R.string.groceries), "[]")
            Log.d("GG", "getGroceries: $groceriesStr")
            val groceriesArray = JSONArray(groceriesStr)
            for (x in 0 until  groceriesArray.length()){
                val grocery = groceriesArray.get(x) as JSONObject
                groceries.add(Grocery(grocery.getInt(context.getString(R.string.id)),
                    grocery.getString(context.getString(R.string.name)),
                    grocery.getDouble(context.getString(R.string.amount)),
                    grocery.getBoolean(context.getString(R.string.bought))))
            }
            groceries
        } catch (e : Exception){
            mutableListOf()
        }
    }

    fun saveGroceries (groceries : MutableList<Grocery>) : Boolean {
        return try {
            val groceriesArray = JSONArray()
            for (x in 0 until  groceries.size){
                val groceryObject = JSONObject()
                groceryObject.put(context.getString(R.string.id), groceries[x].id)
                groceryObject.put(context.getString(R.string.name), groceries[x].name)
                groceryObject.put(context.getString(R.string.amount), groceries[x].amount)
                groceryObject.put(context.getString(R.string.bought), groceries[x].bought)
                groceriesArray.put(groceryObject)
            }
            Log.d("GG", "saveGroceries: ${groceriesArray.toString()}")
            with (sharedPref.edit()) {
                putString(context.getString(R.string.groceries), groceriesArray.toString())
                apply()
            }
            true
        } catch (e : Exception){
            false
        }
    }

    fun addToGroceries(grocery : Grocery) : Boolean {
        Log.d("GG", "addToGroceries: ${grocery.toString()}")
        return try {
            val groceries = getGroceries()
            groceries.add(grocery)
            saveGroceries(groceries)
            true
        } catch (e : Exception){
            Log.d("GG", "addToGroceries: ${e.toString()}")
            false
        }
    }
}