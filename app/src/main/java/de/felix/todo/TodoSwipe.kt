package de.felix.todo

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TodoSwipe(todoListAdapter: TodoListAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val _todoList_adapter: TodoListAdapter

    init {
        _todoList_adapter = todoListAdapter
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.layoutPosition
        when (direction) {
            ItemTouchHelper.LEFT -> _todoList_adapter
            ItemTouchHelper.RIGHT -> _todoList_adapter
        }
    }
}