package hard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import hard.data.TodoItem

@Composable
fun TodoListScreen(items: List<TodoItem>, onToggle: (TodoItem) -> Unit, onDelete: (TodoItem) -> Unit, onAddClick: () -> Unit) {
    Box(Modifier.fillMaxSize()) {
        if (items.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Bo'sh") } else LazyColumn(Modifier.fillMaxSize()) { items(items, key = { it.id }) { item -> TodoRow(item, onToggle, onDelete) } }
        FloatingActionButton(onClick = onAddClick, modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)) { Icon(Icons.Default.Add, contentDescription = null) }
    }
}

@Composable
private fun TodoRow(item: TodoItem, onToggle: (TodoItem) -> Unit, onDelete: (TodoItem) -> Unit) {
    val dismissState = rememberDismissState(confirmStateChange = {
        if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) { onDelete(item); true } else false
    })
    SwipeToDismiss(state = dismissState, background = {}, dismissContent = {
        Row(Modifier.fillMaxWidth().background(Color.White).padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = item.isCompleted, onCheckedChange = { onToggle(item) })
            Text(text = item.title, modifier = Modifier.weight(1f).padding(horizontal = 8.dp))
        }
    })
}

@Composable
fun AddTodoScreen(onAdd: (String) -> Unit, onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(value = text, onValueChange = { text = it }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onAdd(text); onBack() }, enabled = text.isNotBlank(), modifier = Modifier.fillMaxWidth()) { Text("Qo'shish") }
    }
}
