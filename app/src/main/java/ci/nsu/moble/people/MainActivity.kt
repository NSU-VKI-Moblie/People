package ci.nsu.moble.people

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                ColorChangerApp()
            }
        }
    }
}

@Composable
fun ColorChangerApp() {
    val colorMap = remember {
        mapOf(
            "red" to Color.Red,
            "blue" to Color.Blue,
            "green" to Color.Green,
            "yellow" to Color.Yellow,
            "cyan" to Color.Cyan,
            "magenta" to Color.Magenta,
            "black" to Color.Black,
            "white" to Color.White,
            "gray" to Color.Gray,
        )
    }
    var textInput by remember { mutableStateOf("") }
    var buttonColor by remember { mutableStateOf(Color.Blue) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Введите цвет") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val colorName = textInput.lowercase()
                buttonColor = colorMap[colorName] ?: run {
                    Log.w("ColorChanger", "Цвет '$colorName' не найден")
                    buttonColor
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Изменить цвет", color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Доступные цвета:",
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column(Modifier.padding(4.dp)) {
            colorMap.keys.forEach { colorName ->
                Text(
                    text = colorName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}