package de.felix.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * View Model to keep a reference to the word repository and
 * an up-to-date list of all words.
 */

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allTodos: LiveData<List<Todo>> = repository.allTodos.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(todo: Todo) = viewModelScope.launch {
        repository.insert(todo)
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }

    fun updateChecked(id: Int, checked: Boolean) = viewModelScope.launch {
        repository.updateChecked(id, checked)
    }

    fun updateTodo(id: Int, title: String, description: String, expiration: String, priority: String, tag: String) = viewModelScope.launch {
        repository.updateTodo(id, title, description, expiration, priority, tag)
    }
}

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
