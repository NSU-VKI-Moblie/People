package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
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
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ColorChangerApp()
                }
            }
        }
    }
}

@Composable
fun ColorChangerApp() {
    var inputColor by remember { mutableStateOf("") }
    val defaultButtonColor = Color(0xFF6200EE) // Фиолетовый по умолчанию
    var buttonColor by remember { mutableStateOf(defaultButtonColor) }

    val colorMap = remember {
        mapOf(
            "red" to Color.Red,
            "green" to Color.Green,
            "blue" to Color.Blue,
            "black" to Color.Black,
            "white" to Color.White,
            "gray" to Color.Gray,
            "cyan" to Color.Cyan,
            "magenta" to Color.Magenta,
            "yellow" to Color.Yellow,
            "darkgray" to Color.DarkGray,
            "lightgray" to Color.LightGray,
            "purple" to Color(0xFF9C27B0),
            "orange" to Color(0xFFFF9800),
            "brown" to Color(0xFF795548),
            "teal" to Color(0xFF009688),
            "lime" to Color(0xFFCDDC39),
            "indigo" to Color(0xFF3F51B5),
            "pink" to Color(0xFFE91E63)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Заголовок
        Text(
            text = "Изменение цвета кнопки",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Поле ввода (остается серым всегда)
        Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color.LightGray, // Серый фон всегда
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            BasicTextField(
                value = inputColor,
                onValueChange = { inputColor = it },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 18.dp),
                textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                decorationBox = { innerTextField ->
                    if (inputColor.isEmpty()) {
                        Text(
                            text = "Введите название цвета",
                            color = Color.DarkGray,
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка Применить (меняет цвет)
        Button(
            onClick = {
                val colorName = inputColor.trim().lowercase()
                if (colorName.isNotEmpty()) {
                    val color = colorMap[colorName]
                    if (color != null) {
                        buttonColor = color // Меняем цвет кнопки
                        Log.i("ColorChanger", "Цвет кнопки изменен на: $colorName")
                    } else {
                        // Сбрасываем на цвет по умолчанию при ошибке
                        buttonColor = defaultButtonColor
                        Log.e("ColorChanger", "Ошибка: Цвет '$colorName' не найден. Кнопка сброшена на цвет по умолчанию.")
                    }
                } else {
                    // Если поле пустое, тоже сбрасываем
                    buttonColor = defaultButtonColor
                    Log.w("ColorChanger", "Предупреждение: Поле ввода пустое. Кнопка сброшена на цвет по умолчанию.")
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor, // Цвет фона кнопки
                contentColor = if (isDarkColor(buttonColor)) Color.White else Color.Black // Цвет текста
            )
        ) {
            Text("Применить", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Список доступных цветов
        Text(
            text = "Доступные цвета:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(colorMap.keys.toList()) { colorName ->
                Text(
                    text = colorName,
                    color = colorMap[colorName] ?: Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

// Функция для определения темного цвета
private fun isDarkColor(color: Color): Boolean {
    val luminance = 0.299 * color.red + 0.587 * color.green + 0.114 * color.blue
    return luminance < 0.5f
}

@Preview(showBackground = true)
@Composable
fun ColorChangerPreview() {
    PeopleTheme {
        ColorChangerApp()
    }
}