package de.felix.todo.Activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import de.felix.todo.R
import de.felix.todo.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        buttonCancel.setOnClickListener {
            intent.putExtra("todoNew", false)
            finish()
        }

        buttonSave.setOnClickListener { view ->
            if (!(textViewTodoTitle.text.toString().isEmpty() || textViewTodoTitle.text.toString().isEmpty() || textViewTodoDescription.text.toString().isEmpty() || datePickerExpiration.toString().isEmpty() || textViewTodoTitle.text.toString().isEmpty())) {
                intent.putExtra("todoNew", true)
                intent.putExtra("todoID", "1")//Integer.parseInt(binding.textViewTodoTitle.text.toString()))
                intent.putExtra("todoTitle", textViewTodoTitle.text.toString())
                intent.putExtra("todoDescription", textViewTodoDescription.text.toString())
                intent.putExtra("todoExpiration", datePickerExpiration.toString())
                intent.putExtra("todoPriority", "low")
                finish()
            }
            else {
                Snackbar.make(view, "asdasf", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }

        }

        toolbar.setNavigationOnClickListener {
            intent.putExtra("todoNew", false)
            finish()
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
                val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.actionUp -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}