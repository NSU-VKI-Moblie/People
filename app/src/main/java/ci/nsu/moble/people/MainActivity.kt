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

import ci.nsu.moble.people.ui.theme.PeopleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PeopleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Lab1()

                }
            }
        }
    }
}

val colors = mapOf(
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

@Composable
fun Lab1(){
    var textBuffer by remember { mutableStateOf("") }
    var buttonColor by remember {mutableStateOf(Color.Blue)}

    Column (
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)){
            OutlinedTextField(
                value = textBuffer,
                onValueChange = { textBuffer = it },
                label = { Text("Введите цвет") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                val colorName = textBuffer.lowercase()
                buttonColor = colors[colorName] ?: run{
                    Log.w("ColorChanger", "Color '$colorName' not found")
                    buttonColor
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                )
            { Text("Enter") }
        }
        colors.keys.forEach { colorName ->
            Text(
                text = colorName,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp),
                textAlign = TextAlign.Center
            )

        }
    }
}
