package com.persol.groceryguru

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var groceriestoBuy : MutableList<Grocery>
    private lateinit var toBuyRecycler : RecyclerView;
    private lateinit var adapter : GroceriesAdapter
    private lateinit var db : DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        val ab = supportActionBar
        ab!!.setIcon(ContextCompat.getDrawable(this, R.drawable.grocery))

        toBuyRecycler = findViewById(R.id.toBuyRecycler)
        db = DB(this);

        groceriestoBuy = db.getGroceries()
        for(grocery in groceriestoBuy){
            Log.d("GG", "onCreate: ${grocery.name}")
        }

        toBuyRecycler.setHasFixedSize(false)
        // use a linear layout manager
        val layoutManager = LinearLayoutManager(this)
        toBuyRecycler.layoutManager = layoutManager
        adapter = GroceriesAdapter(this,groceriestoBuy)
        toBuyRecycler.adapter = adapter

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.addGrocery -> {
                launchGroceryDialog(true);
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun launchGroceryDialog(add : Boolean) {
        val newDialog = GroceryDialog(add)
        newDialog.show(supportFragmentManager, "groceryDialog")
    }

    public fun notifyRecycler(){
        //Toast.makeText(this, "${groceriestoBuy.size} groceries", Toast.LENGTH_SHORT).show()
        toBuyRecycler.swapAdapter(GroceriesAdapter(this,db.getGroceries()),true)
    }
}