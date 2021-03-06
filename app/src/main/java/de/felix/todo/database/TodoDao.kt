package de.felix.todo.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * The Room Magic is in this file, where you map a method call to an SQL query.
 *
 * When you are using complex data types, such as Date, you have to also supply type converters.
 * To keep this example basic, no types that require type converters are used.
 * See the documentation at
 * https://developer.android.com/topic/libraries/architecture/room.html#type-converters
 */

@Dao
interface TodoDao {

    // The flow always holds/caches latest version of data. Notifies its observers when the
    // data has changed.
    @Query("SELECT * FROM todo_table ORDER BY title ASC")
    fun getPriorityTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo_table WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("UPDATE todo_table SET checked=:checked WHERE id=:id")
    suspend fun updateChecked(id: Int, checked: Boolean)

    @Query("UPDATE todo_table SET title=:title, description=:description, expiration=:expiration, priority=:priority, tag=:tag WHERE id=:id")
    suspend fun updateTodo(id: Int, title: String, description: String, expiration: String, priority: String, tag: String)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()
}
