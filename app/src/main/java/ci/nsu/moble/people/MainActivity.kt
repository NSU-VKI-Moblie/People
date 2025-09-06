package ci.nsu.moble.people

import androidx.activity.ComponentActivity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class MainActivity : ComponentActivity() {

    // Массив доступных цветов
    private val availableColors = mapOf(
        "красный" to Color.RED,
        "синий" to Color.BLUE,
        "зеленый" to Color.GREEN,
        "черный" to Color.BLACK,
        "белый" to Color.WHITE,
        "желтый" to Color.YELLOW,
        "серый" to Color.GRAY,
        "голубой" to Color.CYAN,
        "пурпурный" to Color.MAGENTA
    )

    private lateinit var mainLayout: LinearLayout
    private lateinit var colorInput: EditText
    private lateinit var changeColorButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Создаем основной контейнер
        mainLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(50, 50, 50, 50)
            setBackgroundColor(Color.WHITE)
        }

        // Создаем заголовок
        val titleTextView = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 40
            }
            text = "Выберите цвет для кнопки"
            textSize = 18f
            setTextColor(Color.BLACK)
        }

        // Создаем поле ввода
        colorInput = EditText(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 20
            }
            hint = "Введите название цвета (например: красный)"
            setTextColor(Color.BLACK)
            setHintTextColor(Color.GRAY)
        }

        // Создаем кнопку
        changeColorButton = Button(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            text = "Изменить цвет кнопки"
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.DKGRAY)
            setOnClickListener { changeButtonColor() }
        }

        // Добавляем все элементы в основной layout
        mainLayout.addView(titleTextView)
        mainLayout.addView(colorInput)
        mainLayout.addView(changeColorButton)

        // Устанавливаем основной layout как контент активности
        setContentView(mainLayout)
    }

    private fun changeButtonColor() {
        val colorName = colorInput.text.toString().trim().lowercase()

        if (colorName.isEmpty()) {
            showToast("Пожалуйста, введите название цвета")
            return
        }

        val color = availableColors[colorName]

        if (color != null) {
            // Меняем цвет кнопки
            changeColorButton.setBackgroundColor(color)

            // Меняем цвет текста кнопки для лучшей читаемости
            val textColor = if (isDarkColor(color)) Color.WHITE else Color.BLACK
            changeColorButton.setTextColor(textColor)

            showToast("Цвет кнопки изменен на: $colorName")
        } else {
            showToast("Такого цвета нет в списке! Доступные цвета: ${availableColors.keys.joinToString()}")
        }
    }

    private fun isDarkColor(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}