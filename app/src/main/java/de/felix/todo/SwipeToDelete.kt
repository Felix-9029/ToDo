package de.felix.todo

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

//Diese Klasse nutzt Gestures und realisiert die Callbacks zur Behandlung von Gesten
class SwipeToDelete(adapter: Adapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val mAdapter: Adapter

    init {
        //Hier können wir entscheiden welche Swipes wir zulassen. Aktuell ist
        //dies nur in eine Richtung erlauibt. Für beide Richtungen den auskommentierten
        //Teil hinzufügen und dann in onSwiped() per switch case unterscheiden
        mAdapter = adapter
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    //Direction sagt uns in welche Richtung wir swipen:
    //LEFT, RIGHT, START, END, UP, DOWN
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position: Int = viewHolder.layoutPosition
        when (direction) {
            ItemTouchHelper.LEFT -> mAdapter.remove(position)
            ItemTouchHelper.RIGHT -> {}
        }
    }
}