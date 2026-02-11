package cn.zacash.pokemon.ext

import androidx.compose.ui.graphics.Color

object ColorMapper {
    private val colorMap = mapOf(
        "red" to Color.Red,
        "green" to Color.Green,
        "blue" to Color.Blue,
        "yellow" to Color.Yellow,
        "cyan" to Color.Cyan,
        "magenta" to Color.Magenta,
        "black" to Color.Black,
        "white" to Color.White,
        "gray" to Color.Gray,
        "lightGray" to Color.LightGray,
        "darkGray" to Color.DarkGray,
        "orange" to Color(0xFFFFA500),
        "purple" to Color(0xFF800080),
        "pink" to Color(0xFFFFC0CB)
    )

    fun parseColor(colorName: String?, defaultColor: Color = Color.Transparent): Color {
        return colorMap[colorName?.lowercase()] ?: defaultColor
    }
}