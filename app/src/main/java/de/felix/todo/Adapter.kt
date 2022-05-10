package de.felix.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter (private val values: MutableList<Todo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    inner class ViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        // each data item is just a string in this case
        var txtHeader: TextView = layout.findViewById<View>(R.id.firstLine) as TextView
        var txtFooter: TextView = layout.findViewById<View>(R.id.secondLine) as TextView

    }

    fun add(position: Int, item: Todo) {
        values.add(position, item)
        notifyItemInserted(position)
    }

    fun remove(position: Int) {
        values.removeAt(position)
        notifyItemRemoved(position)
    }

    fun edit(position: Int) {

        values.removeAt(position)
        values.add(position, Todo(999, "test999", "Ficken", "1.1.2000", "low"))
        notifyItemChanged(position)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        // create a new view
        val inflater = LayoutInflater.from(
            parent.context
        )
        val v: View = inflater.inflate(R.layout.todo_line, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.txtHeader.text = values[position]._title
        holder.txtHeader.setOnClickListener { remove(position) }
        holder.txtFooter.text = values[position]._description
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return values.size
    }
}