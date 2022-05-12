package de.felix.todo.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import de.felix.todo.*
import de.felix.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1

    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodosApplication).repository)
    }

    companion object {
        lateinit var mainActivity: MainActivity
            private set
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        setContentView(R.layout.activity_main)

        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        val todoListAdapter = TodoListAdapter()
        recyclerView.adapter = todoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoViewModel.allTodos.observe(this) { todos ->
            // Update the cached copy of the words in the adapter.
            todos.let {
                todoListAdapter.submitList(it)
            }
        }

        val itemTouchHelper = ItemTouchHelper(TodoSwipe(todoListAdapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        floatingActionButtonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
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
        // as you specify a parent Activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == RESULT_OK) {
            val title = intentData?.getStringExtra(DetailActivity.EXTRA_TITLE)
            val description = intentData?.getStringExtra(DetailActivity.EXTRA_DESCRIPTION)
            val expiration = intentData?.getStringExtra(DetailActivity.EXTRA_DATE)
            val priority = intentData?.getStringExtra(DetailActivity.EXTRA_PRIORITY)?.toInt()
            val isChecked = intentData?.getStringExtra(DetailActivity.EXTRA_ISCHECKED).toBoolean()
            if (title != null && description != null && expiration != null && priority != null) {
                val todo = Todo(title, description, expiration, priority, isChecked)
                todoViewModel.insert(todo)
            }
        }
    }
}