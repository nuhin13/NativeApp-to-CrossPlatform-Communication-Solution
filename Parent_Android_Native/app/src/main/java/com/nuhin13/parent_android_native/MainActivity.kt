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
import com.nuhin13.parent_android_native.flutter_communication.FlutterUtil
import com.nuhin13.parent_android_native.flutter_communication.UserInfo
import com.nuhin13.parent_android_native.ui.theme.Parent_Android_NativeTheme


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FlutterUtil.startEngine(this)

        setContent {
            Parent_Android_NativeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Greeting("Android")
                        CardViewWithEditTexts(onSubmit = {
                            Log.e("T", it.toString())
                        })
                        CardViewWithImageView()
                        CardViewWithInputNumber(onSubmit = {
                            Log.e("T", it.toString())
                        })
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CardViewWithEditTexts(
    onSubmit: (UserInfo) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier.padding(10.dp).fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = { Text("Your Name") }
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                },
                label = { Text("Your Phone Number") }
            )
            Button(
                onClick = {
                    val user = UserInfo(name, email)
                    onSubmit(user)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Navigate to Flutter",
                )
            }
        }
    }
}


@Composable
fun CardViewWithImageView() {
    //val img = imageResource(id = R.drawable.ic_launcher_background)
    val paddingModifier = Modifier.padding(10.dp).fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(text = "Open Camera",)
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(text = "Navigate Flutter with Image",)
            }
        }
    }
}


@ExperimentalMaterial3Api
@Composable
fun CardViewWithInputNumber(
    onSubmit: (UserInfo) -> Unit
) {
    var value1 by rememberSaveable { mutableStateOf("") }
    var value2 by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier.padding(10.dp).fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Calculate two numbers",
            )
            OutlinedTextField(
                value = value1,
                onValueChange = {
                    value1 = it
                },
                label = { Text("Value 1") }
            )
            OutlinedTextField(
                value = value2,
                onValueChange = {
                    value2 = it
                },
                label = { Text("Value 2") }
            )
            Button(
                onClick = {
                    val user = UserInfo(value1, value2)
                    onSubmit(user)
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
            ) {
                Text(
                    text = "Calculate into Flutter",
                )
            }

            Text(
                text = "This is from Flutter",
            )
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Parent_Android_NativeTheme {
        /*CardViewWithEditTexts(onSubmit = {
            Log.e("T", it.toString())
        })*/
    }
}