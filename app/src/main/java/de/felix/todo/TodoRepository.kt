package de.felix.todo

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */
class TodoRepository(private val todoDao: TodoDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allTodos: Flow<List<Todo>> = todoDao.getPriorityTodos()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        todoDao.delete(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateChecked(id: Int, checked: Boolean) {
        todoDao.updateChecked(id, checked)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTodo(id: Int, title: String, description: String, expiration: String, priority: String, tag: String) {
        todoDao.updateTodo(id, title, description, expiration, priority, tag)
    }
}
