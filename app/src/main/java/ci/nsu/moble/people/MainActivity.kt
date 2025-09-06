package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ci.nsu.moble.people.ui.theme.PeopleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                AppContent()
            }
        }
    }
}

// Map с доступными цветами
val colorMap = mapOf(
    "red" to Color.Red,
    "green" to Color.Green,
    "blue" to Color.Blue,
    "yellow" to Color.Yellow,
    "cyan" to Color.Cyan,
    "magenta" to Color.Magenta,
    "black" to Color.Black,
    "white" to Color.White,
    "gray" to Color.Gray,
    "lightgray" to Color.LightGray,
    "darkgray" to Color.DarkGray
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    var text by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("NeAndroi") }
    var colorInput by remember { mutableStateOf("") }
    var backgroundColor by remember { mutableStateOf(Color.White) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Первое текстовое поле для имени
            TextField(
                value = text,
                onValueChange = {
                    text = it
                    name = it.ifEmpty { "NeAndroi" }
                },
                label = { Text("Введите ваше имя") },
                placeholder = { Text("Например, Android") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(0.5f)
            )

            // Второе текстовое поле для цвета
            TextField(
                value = colorInput,
                onValueChange = { colorInput = it },
                onDone = {
                    val colorName = colorInput.trim().lowercase()
                    if (colorMap.containsKey(colorName)) {
                        backgroundColor = colorMap[colorName]!!
                        Log.d("ColorChange", "Цвет фона изменен на: $colorName")
                    } else if (colorName.isNotEmpty()) {
                        Log.e("ColorError", "Цвет '$colorName' не найден в списке доступных цветов")
                        // Можно показать сообщение пользователю
                    }
                },
                label = { Text("Введите цвет фона") },
                placeholder = { Text("Например: red, green, blue") },
                singleLine = true,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(0.5f)
            )

            // Приветствие
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PeopleTheme {
        AppContent()
    }
}