package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ci.nsu.moble.people.ui.theme.PeopleTheme
import java.util.HashMap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ColorChangerScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "ColorChanger"

        // HashMap с доступными цветами, заполненная через put()
        val colorsArray: HashMap<String, Color> = HashMap<String, Color>().apply {
            // Основные цвета
            put("red", Color.Red)
            put("blue", Color.Blue)
            put("green", Color.Green)
            put("yellow", Color.Yellow)
            put("black", Color.Black)
            put("white", Color.White)

            // Дополнительные цвета
            put("cyan", Color.Cyan)
            put("magenta", Color.Magenta)
            put("gray", Color.Gray)
            put("grey", Color.Gray) // альтернативное написание
            put("darkgray", Color.DarkGray)
            put("darkgrey", Color.DarkGray) // альтернативное написание
            put("lightgray", Color.LightGray)
            put("lightgrey", Color.LightGray) // альтернативное написание

            // Цвета по названиям
            put("orange", Color(0xFFFFA500))
            put("purple", Color(0xFF800080))
            put("pink", Color(0xFFFFC0CB))
            put("brown", Color(0xFFA52A2A))
            put("silver", Color(0xFFC0C0C0))
            put("gold", Color(0xFFFFD700))
            put("maroon", Color(0xFF800000))
            put("navy", Color(0xFF000080))
            put("teal", Color(0xFF008080))
            put("olive", Color(0xFF808000))
            put("lime", Color(0xFF00FF00))
            put("aqua", Color(0xFF00FFFF))
            put("fuchsia", Color(0xFFFF00FF))
        }
    }
}

@Composable
fun ColorChangerScreen(modifier: Modifier = Modifier) {
    var colorText by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Gray) }
    var currentColorName by remember { mutableStateOf("не задан") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Текстовое поле для ввода цвета
        BasicTextField(
            value = colorText,
            onValueChange = { newText ->
                colorText = newText
                errorMessage = null // Сбрасываем ошибку при новом вводе
            },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(8.dp),
            decorationBox = { innerTextField ->
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(8.dp)
                ) {
                    if (colorText.isEmpty()) {
                        Text(
                            text = "Введи цвет",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    innerTextField()
                }
            }
        )

        // Кнопка для изменения цвета
        Button(
            onClick = {
                val result = parseColorFromText(colorText)
                buttonColor = result.color
                currentColorName = if (colorText.isNotEmpty()) colorText else "не задан"

                if (result.success) {
                    errorMessage = null
                    Log.d(MainActivity.TAG, "Цвет успешно установлен: $colorText -> ${result.color}")
                } else {
                    errorMessage = result.errorMessage
                    Log.w(MainActivity.TAG, "Ошибка установки цвета: $colorText - ${result.errorMessage}")
                }
            },
            modifier = Modifier.padding(8.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = if (isDarkColor(buttonColor)) Color.White else Color.Black
            )
        ) {
            Text("Нажми чтоб поменять цвет")
        }

        // Отображение текущего цвета
        Text(
            text = "Текущий цвет: $currentColorName",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        // Отображение ошибки
        errorMessage?.let { message ->
            Text(
                text = "Ошибка: $message",
                modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }

        // Заголовок для списка цветов
        Text(
            text = "Доступные цвета:",
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )

        // Отображение цветов в несколько колонок
        ColorColumns()
    }
}

@Composable
fun ColorColumns() {
    val colorsList = MainActivity.colorsArray.entries.sortedBy { it.key }
    val columns = 3 // Количество колонок
    val rows = (colorsList.size + columns - 1) / columns // Вычисляем количество строк

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        for (row in 0 until rows) {
            Row(
                modifier = Modifier
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    if (index < colorsList.size) {
                        val (colorName, colorValue) = colorsList[index]
                        ColorItem(colorName = colorName, colorValue = colorValue)
                    } else {
                        // Пустое место для выравнивания
                        Surface(
                            modifier = Modifier
                                .width(100.dp)
                                .padding(2.dp),
                            color = Color.Transparent
                        ) {}
                    }
                }
            }
        }
    }
}

@Composable
fun ColorItem(colorName: String, colorValue: Color) {
    Surface(
        modifier = Modifier
            .width(100.dp)
            .padding(2.dp),
        color = colorValue,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = colorName,
            modifier = Modifier.padding(4.dp),
            color = if (isDarkColor(colorValue)) Color.White else Color.Black,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

// Класс для возврата результата парсинга цвета
data class ColorParseResult(
    val color: Color,
    val success: Boolean,
    val errorMessage: String? = null
)

// Функция для парсинга цвета из текста с детальным логированием
fun parseColorFromText(colorText: String): ColorParseResult {
    if (colorText.isEmpty()) {
        return ColorParseResult(Color.Gray, true, null)
    }

    val cleanText = colorText.trim().lowercase()

    return try {
        val color = when {
            MainActivity.colorsArray.containsKey(cleanText) -> {
                MainActivity.colorsArray[cleanText] ?: Color.Gray
            }
            cleanText.startsWith("#") -> {
                Color(android.graphics.Color.parseColor(cleanText))
            }
            cleanText.matches(Regex("[0-9A-Fa-f]{6}")) -> {
                Color(android.graphics.Color.parseColor("#$cleanText"))
            }
            cleanText.matches(Regex("[0-9A-Fa-f]{8}")) -> {
                Color(android.graphics.Color.parseColor("#$cleanText"))
            }
            else -> {
                throw IllegalArgumentException("Неизвестный формат цвета: '$cleanText'")
            }
        }
        ColorParseResult(color, true, null)

    } catch (e: IllegalArgumentException) {
        ColorParseResult(
            color = Color.Gray,
            success = false,
            errorMessage = "Неизвестный цвет: '$colorText'. Выберите из списка ниже"
        )
    }
}

// Функция для определения темного цвета
fun isDarkColor(color: Color): Boolean {
    val darkness = 1 - (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return darkness >= 0.5f
}

@Preview(showBackground = true)
@Composable
fun ColorChangerPreview() {
    PeopleTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ColorChangerScreen()
        }
    }
}