package com.ayushsinghal.hikeview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.ayushsinghal.hikeview.ui.theme.HikeViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HikeViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val elevationData = listOf<Pair<Int, Int>>(
                        //height, offset
                        Pair(25, 300),
                        Pair(30, 290),
                        Pair(25, 290),
                        Pair(25, 270),
                        Pair(35, 240),
                        Pair(25, 245),
                        Pair(30, 260),
                        Pair(100, 190),
                        Pair(40, 150),
                        Pair(50, 160),
                        Pair(80, 200),
                        Pair(30, 300)
                    )

                    val heartRateData = listOf<Pair<Int, Int>>(
                        //height, offset
                        Pair(30, 280),
                        Pair(25, 290),
                        Pair(30, 270),
                        Pair(40, 240),
                        Pair(40, 210),
                        Pair(25, 220),
                        Pair(28, 240),
                        Pair(100, 150),
                        Pair(60, 110),
                        Pair(70, 170),
                        Pair(30, 230),
                        Pair(30, 250)
                    )

                    val paceData = listOf<Pair<Int, Int>>(
                        //height, offset
                        Pair(25, 50),
                        Pair(70, 70),
                        Pair(25, 70),
                        Pair(50, 70),
                        Pair(80, 70),
                        Pair(28, 45),
                        Pair(80, 60),
                        Pair(50, 10),
                        Pair(150, 70),
                        Pair(28, 70),
                        Pair(32, 73),
                        Pair(32, 75)
                    )

                    HikeScreen(
                        title = "Lonesome Ridge Trail",
                        distance = "4.5 km",
                        thumbNail = R.drawable.small_image,
                        elevationData = elevationData,
                        heartRateData = heartRateData,
                        paceData = paceData
                    )
                }
            }
        }
    }
}