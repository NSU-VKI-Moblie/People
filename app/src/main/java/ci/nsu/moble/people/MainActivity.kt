package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                ColorChangerApp()
            }
        }
    }
}

// Список доступных цветов
val availableColors = mapOf(
    "red" to Color.Red,
    "blue" to Color.Blue,
    "green" to Color.Green,
    "yellow" to Color.Yellow,
    "black" to Color.Black,
    "white" to Color.White,
    "cyan" to Color.Cyan,
    "magenta" to Color.Magenta,
    "gray" to Color.Gray,
    "lightgray" to Color.LightGray,
    "darkgray" to Color.DarkGray
)

@Composable
fun ColorChangerApp() {
    var textInput by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Gray) }
    var statusMessage by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Основной контент - выровнен по центру
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ColorChangerContent(
                    textInput = textInput,
                    onTextInputChange = { textInput = it },
                    buttonColor = buttonColor,
                    onButtonColorChange = { buttonColor = it },
                    statusMessage = statusMessage,
                    onStatusMessageChange = { statusMessage = it }
                )
            }

            // Список цветов внизу - выровнен по центру
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Доступные цвета:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.Center,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth(0.8f) // 80% ширины для центрирования
                ) {
                    items(availableColors.keys.toList().sorted()) { colorName ->
                        Text(
                            text = colorName,
                            style = MaterialTheme.typography.bodySmall,
                            color = availableColors[colorName] ?: Color.Gray,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorChangerContent(
    textInput: String,
    onTextInputChange: (String) -> Unit,
    buttonColor: Color,
    onButtonColorChange: (Color) -> Unit,
    statusMessage: String,
    onStatusMessageChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = textInput,
            onValueChange = onTextInputChange,
            label = { Text("Введите цвет (например: red, blue)") },
            modifier = Modifier
                .width(300.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = statusMessage,
            color = if (statusMessage.contains("ошибка", ignoreCase = true)) Color.Red else Color.Green,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Button(
            onClick = {
                val colorName = textInput.trim().lowercase()
                if (colorName.isNotEmpty()) {
                    val color = availableColors[colorName]
                    if (color != null) {
                        onButtonColorChange(color)
                        onStatusMessageChange("Цвет '$colorName' применен")
                        Log.d("ColorChanger", "Цвет '$colorName' успешно применен")
                    } else {
                        onStatusMessageChange("Ошибка: цвет '$colorName' не найден")
                        Log.w("ColorChanger", "Цвет '$colorName' не найден в списке доступных цветов")
                    }
                } else {
                    onStatusMessageChange("Ошибка: введите название цвета")
                    Log.w("ColorChanger", "Введите название цвета")
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = if (buttonColor == Color.Black || buttonColor == Color.DarkGray) Color.White else Color.Black
            ),
            modifier = Modifier.width(200.dp)
        ) {
            Text("Применить цвет")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ColorChangerPreview() {
    PeopleTheme {
        ColorChangerApp()
    }
}