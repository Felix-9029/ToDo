package de.felix.todo.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import de.felix.todo.R
import de.felix.todo.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "com.example.android.wordlistsql.TITLE"
        const val EXTRA_DESCRIPTION = "com.example.android.wordlistsql.DESCRIPTION"
        const val EXTRA_DATE = "com.example.android.wordlistsql.DATE"
        const val EXTRA_PRIORITY = "com.example.android.wordlistsql.PRIORITY"
        const val EXTRA_ISCHECKED = "com.example.android.wordlistsql.ISCHECKED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val binding: ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        val replyIntent = Intent()
        buttonCancel.setOnClickListener {
            setResult(RESULT_CANCELED, replyIntent)
            finish()
        }

        buttonSave.setOnClickListener { view ->
            if (TextUtils.isEmpty(textViewTodoTitle.text) || TextUtils.isEmpty(textViewTodoDescription.text)) {
                setResult(RESULT_CANCELED, replyIntent)
                Snackbar.make(view, R.string.emptyBlock, Snackbar.LENGTH_LONG).setAction("Action", null).show()
            } else {
                val title = textViewTodoTitle.text.toString()
                val description = textViewTodoDescription.text.toString()
                val day: Int = Calendar.DAY_OF_MONTH
                val month: Int = Calendar.MONTH
                val year: Int = Calendar.YEAR
                val expiration = "lala" //DateTimeFormatter.ofPattern("yyyy-MM-dd").format(datePickerExpiration)
                val priority = 1
                val isChecked = false
                replyIntent.putExtra(EXTRA_TITLE, title)
                replyIntent.putExtra(EXTRA_DESCRIPTION, description)
                replyIntent.putExtra(EXTRA_DATE, expiration)
                replyIntent.putExtra(EXTRA_PRIORITY, priority)
                replyIntent.putExtra(EXTRA_ISCHECKED, isChecked)
                setResult(RESULT_OK, replyIntent)
                finish()
            }
        }

        toolbar.setNavigationOnClickListener {
            setResult(RESULT_CANCELED, replyIntent)
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
        // as you specify a parent Activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@DetailActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}