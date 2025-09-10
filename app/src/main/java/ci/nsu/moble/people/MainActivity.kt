package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ci.nsu.moble.people.ui.theme.PeopleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorButtonScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Map с доступными цветами
val colorMap = mapOf(
    "red" to Color.Red,
    "blue" to Color.Blue,
    "green" to Color.Green,
    "yellow" to Color.Yellow,
    "black" to Color.Black,
    "white" to Color.White,
    "gray" to Color.Gray,
    "cyan" to Color.Cyan,
    "magenta" to Color.Magenta,
    "lightgray" to Color.LightGray,
    "darkgray" to Color.DarkGray
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorButtonScreen(modifier: Modifier = Modifier) {
    var inputText by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Blue) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Текстовое поле для ввода цвета
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            label = { Text("Введите цвет (на английском)") },
            placeholder = { Text("Например: red, blue, green") },
            modifier = Modifier
                .padding(16.dp)
                .padding(top = 60.dp) // Добавляем отступ сверху
        )

        // Круглая кнопка
        Button(
            onClick = {
                val colorName = inputText.trim().lowercase()
                if (colorName.isNotEmpty()) {
                    val newColor = colorMap[colorName]
                    if (newColor != null) {
                        buttonColor = newColor
                        Log.d("ColorButton", "Цвет кнопки изменен на: $colorName")
                    } else {
                        Log.e("ColorButton", "Ошибка: цвет '$colorName' не найден в списке доступных цветов")
                    }
                }
            },
            modifier = Modifier
                .size(80.dp)
                .padding(16.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = Color.White
            )
        ) {
            Text("OK")
        }

        // Список доступных цветов
        Text(
            text = "Доступные цвета:",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        // Отображаем список цветов через запятую
        Text(
            text = colorMap.keys.joinToString(", "),
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorButtonPreview() {
    PeopleTheme {
        ColorButtonScreen()
    }
}