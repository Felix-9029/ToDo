package de.felix.todo.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import de.felix.todo.Adapter
import de.felix.todo.R
import de.felix.todo.Todo
import de.felix.todo.TodoSwipe
import de.felix.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    companion object Factory {
        fun input(): MutableList<Todo> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = Adapter(input())
        recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(TodoSwipe(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        floatingActionButtonAdd.setOnClickListener {view ->
            startActivity(Intent(this@MainActivity, DetailActivity::class.java))
            adapter = Adapter(input())
            recyclerView.adapter = adapter
            if (input().size > 0) {
                Snackbar.make(view, input().get(input().size - 1)._title, Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
            else {
                Snackbar.make(view, "empty", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}