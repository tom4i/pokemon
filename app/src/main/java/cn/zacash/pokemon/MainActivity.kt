package cn.zacash.pokemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.edit
import androidx.navigation.compose.rememberNavController
import cn.zacash.pokemon.ui.theme.PokemonTheme

class MainActivity : ComponentActivity() {

    companion object {
        const val PREFS_SETTINGS = "settings"

        // 是否为第一次启动
        const val IS_FIRST_LAUNCH = "is_first_launch"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences(PREFS_SETTINGS, MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean(IS_FIRST_LAUNCH, true)
        enableEdgeToEdge()
        setContent {
            PokemonTheme {
                AppNavHost(rememberNavController(), isFirstLaunch)
                prefs.edit {
                    putBoolean(IS_FIRST_LAUNCH, false)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokemonTheme {

    }
}