package ci.nsu.moble.people

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val availableColors = mapOf(
    "Red" to Color.Red,
    "blue" to Color.Blue,
    "green" to Color.Green,
    "желтый" to Color.Yellow,
    "черный" to Color.Black,
    "белый" to Color.White,
    "фиолетовый" to Color.Magenta,
    "оранжевый" to Color(0xFFFFA500),
    "розовый" to Color(0xFFFFC0CB),
    "серый" to Color.Gray
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorChangerScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorChangerScreen() {
    var inputColor by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Blue) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Поле ввода цвета
        TextField(
            value = inputColor,
            onValueChange = {
                inputColor = it
                errorMessage = "" // Очищаем ошибку при изменении текста
            },
            label = { Text("Введите цвет (например: красный, синий)") },
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp),
            textStyle = TextStyle(fontSize = 16.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для изменения цвета
        Button(
            onClick = {
                val colorName = inputColor.trim()
                if (colorName.isNotEmpty()) {
                    val newColor = availableColors[colorName]
                    if (newColor != null) {
                        buttonColor = newColor
                        errorMessage = ""
                    } else {
                        errorMessage = "Цвет '$colorName' не найден в списке доступных цветов"
                    }

                } else {
                    errorMessage = "Пожалуйста, введите название цвета"
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = if (buttonColor == Color.Black || buttonColor == Color.Blue) Color.White else Color.Black
            ),
            modifier = Modifier
                .padding(8.dp)
        ) {
            Text("Изменить цвет")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Сообщение об ошибке
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Заголовок для списка доступных цветов
        Text(
            text = "Доступные цвета:",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )

        // Список доступных цветов в виде колонки
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            availableColors.keys.forEach { colorName ->
                Text(
                    text = "• $colorName",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}
