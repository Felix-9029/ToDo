package de.felix.todo

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.felix.todo.Activity.DetailActivity
import de.felix.todo.Activity.MainActivity
import de.felix.todo.TodoListAdapter.TodoViewHolder

class TodoListAdapter : ListAdapter<Todo, TodoViewHolder>(TODO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var textViewTitle: TextView = itemView.findViewById<View>(R.id.textViewTitle) as TextView
        private var textViewDescription: TextView = itemView.findViewById<View>(R.id.textViewDescription) as TextView
        private var textViewExpirationDate: TextView = itemView.findViewById<View>(R.id.textViewExpirationDate) as TextView
        private var textViewPriority: TextView = itemView.findViewById<View>(R.id.textViewPriority) as TextView
        private var textViewTag: TextView = itemView.findViewById<View>(R.id.textViewTag) as TextView
        private var checkBoxTodoDone: CheckBox = itemView.findViewById<View>(R.id.checkBoxTodoDone) as CheckBox

        fun bind(current: Todo) {
            val id = current.id
            val title = current.title
            val description = current.description
            val expirationDate = current.expiration
            val priority = current.priority
            val tag = current.tag
            val checked = current.checked

            textViewTitle.text = title
            textViewDescription.text = description
            textViewExpirationDate.text = expirationDate
            textViewPriority.text = priority
            textViewTag.text = tag
            checkBoxTodoDone.isChecked = checked

            if (checked) {
                textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }

            checkBoxTodoDone.setOnClickListener {
                if (current.checked) {
                    textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    MainActivity.mainActivity.todoViewModel.updateChecked(current.id,false)
                }
                else {
                    textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    MainActivity.mainActivity.todoViewModel.updateChecked(current.id,true)
                }
            }

            itemView.setOnClickListener {
                setSharedPreference("isEdit", "true")
                setSharedPreference("todoID", id.toString())
                setSharedPreference("todoTitle", title)
                setSharedPreference("todoDescription", description)
                setSharedPreference("todoPriority", priority)
                setSharedPreference("todoTag", tag)
                val intent = Intent(MainActivity.mainActivity, DetailActivity::class.java)
                MainActivity.mainActivity.startActivityForResult(intent, 2)
            }

            applySharedPreferenceSettings()
        }

        private fun applySharedPreferenceSettings() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainActivity)
            val textSize = sharedPreferences?.getString("fontsize", "19")!!.toFloat()
            textViewTitle.textSize = textSize
            textViewDescription.textSize = textSize
            textViewExpirationDate.textSize = textSize
            textViewPriority.textSize = textSize
            textViewTag.textSize = textSize
        }

        private fun setSharedPreference(key: String, value: String) {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainActivity)
            sharedPreferences.edit {
                putString(key, value)
            }
        }

        companion object {
            fun create(parent: ViewGroup): TodoViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.todo_line, parent, false)
                return TodoViewHolder(view)
            }
        }
    }

    companion object {
        private val TODO_COMPARATOR = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return (oldItem.title == newItem.title && oldItem.description == newItem.description && oldItem.expiration == newItem.expiration)
            }
        }
    }
}
