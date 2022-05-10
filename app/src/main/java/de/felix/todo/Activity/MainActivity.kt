package de.felix.todo.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var input: MutableList<Todo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        floatingActionButtonAdd.setOnClickListener {

            //val activityResult = registerForActivityResult(StartActivityForResult()) { result: ActivityResult ->
            //    if (result.resultCode == Activity.RESULT_OK) {
            //        val data = result.data?.extras
            //        val todo = Todo(data?.get("todoID") as Int, data.get("todoTitle") as String, data.get("todoDescription") as String, data.get("todoExpiration") as String, data.get("todoPriority") as String)
            //        input.add(todo)
            //    }
            //}
            startActivity(Intent(this@MainActivity, DetailActivity::class.java))
        }

        recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = Adapter(input)
        recyclerView.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(TodoSwipe(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)
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