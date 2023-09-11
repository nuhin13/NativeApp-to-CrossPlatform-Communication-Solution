package com.nuhin13.parent_android_native

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nuhin13.parent_android_native.flutter_communication.FlutterUtil
import com.nuhin13.parent_android_native.flutter_communication.UserInfo
import com.nuhin13.parent_android_native.navigation.SetupNavGraph
import com.nuhin13.parent_android_native.navigation.rememberWindowSize
import com.nuhin13.parent_android_native.ui.theme.Parent_Android_NativeTheme
import androidx.navigation.compose.rememberNavController
import com.nuhin13.parent_android_native.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FlutterUtil.startEngine(this)

        setContent {
            Parent_Android_NativeTheme {
                val window = rememberWindowSize()
                val navController = rememberNavController()
                SetupNavGraph(windowSize = window, navController = navController)
            }
        }
    }
}


/*@Composable
fun Camera() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSuccess: Boolean ->
            if (isSuccess) {
                // The picture was taken successfully.
                val uri = it.data?.data
                val imageView = remember { Image(modifier = Modifier.fillMaxSize()) }
                imageView.setImageURI(uri)
            } else {
                // The picture was not taken successfully.
            }
        }
    )

    Button(
        onClick = {
            launcher.launch()
        },
        modifier = Modifier.align(Alignment.CenterHorizontally)
    ) {
        Text("Take Picture")
    }
}*/