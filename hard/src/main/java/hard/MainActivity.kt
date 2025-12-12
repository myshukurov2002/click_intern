package hard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import hard.data.AppDatabase
import hard.data.TodoRepository
import hard.ui.AddTodoScreen
import hard.ui.TodoListScreen
import hard.ui.TodoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val db = remember { AppDatabase.get(applicationContext) }
                val repo = remember { TodoRepository(db.todoDao()) }
                val factory = remember {
                    object : ViewModelProvider.Factory {
                        override fun <T : ViewModel> create(modelClass: Class<T>): T {
                            @Suppress("UNCHECKED_CAST")
                            return TodoViewModel(repo) as T
                        }
                    }
                }
                val vm: TodoViewModel = viewModel(factory = factory)
                var showAdd by remember { mutableStateOf(false) }
                val items by vm.items.collectAsState()
                if (showAdd) {
                    AddTodoScreen(onAdd = { vm.add(it) }, onBack = { showAdd = false })
                } else {
                    TodoListScreen(items = items, onToggle = vm::toggle, onDelete = vm::remove, onAddClick = { showAdd = true })
                }
            }
        }
    }
}
