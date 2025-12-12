package hard.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hard.data.TodoItem
import hard.data.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    val items: StateFlow<List<TodoItem>> = repository.getAllTodos().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    fun add(title: String) { viewModelScope.launch { repository.insert(TodoItem(title = title)) } }
    fun toggle(item: TodoItem) { viewModelScope.launch { repository.update(item.copy(isCompleted = !item.isCompleted)) } }
    fun remove(item: TodoItem) { viewModelScope.launch { repository.delete(item) } }
}
