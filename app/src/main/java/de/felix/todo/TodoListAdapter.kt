package de.felix.todo

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import de.felix.todo.Activity.MainActivity
import de.felix.todo.TodoListAdapter.TodoViewHolder

class TodoListAdapter : ListAdapter<Todo, TodoViewHolder>(TODO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.description, current.expiration, "low", "standard", false)
    }

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var textViewTitle: TextView = itemView.findViewById<View>(R.id.textViewTitle) as TextView
        private var textViewDescription: TextView = itemView.findViewById<View>(R.id.textViewDescription) as TextView
        private var textViewExpirationDate: TextView = itemView.findViewById<View>(R.id.textViewExpirationDate) as TextView
        private var textViewPriority: TextView = itemView.findViewById<View>(R.id.textViewPriority) as TextView
        private var textViewTag: TextView = itemView.findViewById<View>(R.id.textViewTag) as TextView
        private var checkBoxTodoDone: CheckBox = itemView.findViewById<View>(R.id.checkBoxTodoDone) as CheckBox

        fun bind(title: String?, description: String?, expirationDate: String?, priority: String?, tag: String?, done: Boolean) {
            textViewTitle.text = title
            textViewDescription.text = description
            textViewExpirationDate.text = expirationDate
            textViewPriority.text = priority
            textViewTag.text = tag
            checkBoxTodoDone.isChecked = done
            if (done) {
                textViewTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }
            else {
                textViewTitle.paintFlags = textViewTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            }
            applyTextSizeFromSharedPreference()
        }

        private fun applyTextSizeFromSharedPreference() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.mainActivity)
            val myCheck = sharedPreferences?.getString("fontsize", "19")
            if (myCheck != null) {
                textViewTitle.textSize = myCheck.toFloat()
                textViewDescription.textSize = myCheck.toFloat()
                textViewExpirationDate.textSize = myCheck.toFloat()
                textViewPriority.textSize = myCheck.toFloat()
                textViewTag.textSize = myCheck.toFloat()
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