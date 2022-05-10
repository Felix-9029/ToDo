package de.felix.todo

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TodoSwipe(adapter: Adapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val _adapter: Adapter

    init {
        _adapter = adapter
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.layoutPosition
        when (direction) {
            ItemTouchHelper.LEFT -> _adapter.remove(position)
            ItemTouchHelper.RIGHT -> _adapter.edit(position)
        }
    }
}