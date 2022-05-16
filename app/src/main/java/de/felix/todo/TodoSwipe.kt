package de.felix.todo

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.felix.todo.Activity.MainActivity

class TodoSwipe(todoViewModel: TodoViewModel) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private val _todoViewModel: TodoViewModel

    init {
        _todoViewModel = todoViewModel
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.
        when (direction) {
            ItemTouchHelper.LEFT -> _todoViewModel.delete()
            ItemTouchHelper.RIGHT -> _todoViewModel.
        }
    }
}