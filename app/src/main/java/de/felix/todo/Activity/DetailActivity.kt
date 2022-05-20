package de.felix.todo.Activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import de.felix.todo.R
import de.felix.todo.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.todo_line.*
import java.time.format.DateTimeFormatter
import java.util.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "de.felix.todo.todosql.TITLE"
        const val EXTRA_DESCRIPTION = "de.felix.todo.todosql.DESCRIPTION"
        const val EXTRA_DATE = "de.felix.todo.todosql.DATE"
        const val EXTRA_PRIORITY = "de.felix.todo.todosql.PRIORITY"
        const val EXTRA_ISCHECKED = "de.felix.todo.todosql.ISCHECKED"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val binding: ActivityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbar)

        applySharedPreferenceSettings()

        val replyIntent = Intent()

        buttonCancel.setOnClickListener {
            setResult(RESULT_CANCELED, replyIntent)
            finish()
        }

        buttonSave.setOnClickListener { view ->
            if (TextUtils.isEmpty(textViewTodoTitle.text) || TextUtils.isEmpty(textViewTodoDescription.text)) {
                Snackbar.make(view, R.string.emptyBlock, Snackbar.LENGTH_LONG).setAction("Action", null).show()
            } else {
                val title = textViewTodoTitle.text.toString()
                val description = textViewTodoDescription.text.toString()
                val day: Int = datePickerExpiration.dayOfMonth
                val month: Int = datePickerExpiration.month + 1
                val year: Int = datePickerExpiration.year
                val expiration = "$day-$month-$year"
                val priority = 1
                val isChecked = true
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

    override fun onResume() {
        super.onResume()
        applySharedPreferenceSettings()
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

    private fun applySharedPreferenceSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val textsizeString = sharedPreferences.getString("fontsize", "19")!!
        textViewTodoTitle.textSize = textsizeString.toFloat()
        textViewTodoDescription.textSize = textsizeString.toFloat()
        buttonCancel.textSize = textsizeString.toFloat()
        buttonSave.textSize = textsizeString.toFloat()

        val darkmode = sharedPreferences.getBoolean("darkmode", true)
        if (darkmode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}