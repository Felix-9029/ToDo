package de.felix.todo.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import androidx.lifecycle.observe
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import de.felix.todo.*
import de.felix.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author <p>Felix Reichert</p>
 * <p>Matrikelnummer: 19019</p>
 * <p>Package: de.felix.todo.Activity</p>
 * <p>Datei: MainActivity.kt</p>
 * <p>Datum: 02.05.2022</p>
 * <p>Version: 1</p>
 *
 * This Project is inspired by https://developer.android.com/codelabs/android-room-with-a-view-kotlin
 */

class MainActivity : AppCompatActivity() {

    private val newTodoActivityRequestCode = 1
    private val updateTodoActivityRequestCode = 2

    val todoViewModel: TodoViewModel by viewModels {
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

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        applySharedPreferenceSettings()

        val todoListAdapter = TodoListAdapter()
        recyclerView.adapter = todoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        todoViewModel.allTodos.observe(this) { todos ->
            // Update the cached copy of the todos in the adapter.
            todos.let {
                todoListAdapter.submitList(it)
            }
        }

        val itemTouchHelper = ItemTouchHelper(TodoSwipe(todoViewModel))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        floatingActionButtonAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivityForResult(intent, newTodoActivityRequestCode)
        }
    }

    override fun onResume() {
        super.onResume()
        applySharedPreferenceSettings()
        setSharedPreference("isEdit", "false")
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
        if (resultCode == RESULT_OK) {
            val title = intentData!!.extras?.getString(DetailActivity.EXTRA_TITLE)!!
            val description = intentData.extras?.getString(DetailActivity.EXTRA_DESCRIPTION)!!
            val expiration = intentData.extras?.getString(DetailActivity.EXTRA_DATE)!!
            val priority = intentData.extras?.getString(DetailActivity.EXTRA_PRIORITY)!!
            val tag = intentData.extras?.getString(DetailActivity.EXTRA_TAG)!!
            val isChecked = intentData.extras?.getBoolean(DetailActivity.EXTRA_ISCHECKED)!!
            if (requestCode == newTodoActivityRequestCode) {
                val todo = Todo(title, description, expiration, priority, tag, isChecked)
                todoViewModel.insert(todo)
            } else if (requestCode == updateTodoActivityRequestCode) {
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                val id = sharedPreferences?.getString("todoID", "0")!!.toInt()
                todoViewModel.updateTodo(id, title, description, expiration, priority, tag)
            }
        }
    }

    private fun applySharedPreferenceSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        val darkmode = sharedPreferences?.getBoolean("darkmode", true)!!
        if (darkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setSharedPreference(key: String, value: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.edit {
            putString(key, value)
        }
    }
}