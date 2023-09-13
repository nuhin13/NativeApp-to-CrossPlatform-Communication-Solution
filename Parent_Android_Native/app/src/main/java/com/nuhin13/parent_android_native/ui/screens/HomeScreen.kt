package com.nuhin13.parent_android_native.ui.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants
import com.nuhin13.parent_android_native.flutter_communication.FlutterUtil
import com.nuhin13.parent_android_native.flutter_communication.UserInfo
import com.nuhin13.parent_android_native.navigation.Screen
import com.nuhin13.parent_android_native.ui.theme.Parent_Android_NativeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CardViewWithEditTexts(navController = navController)
            CardViewWithImageView(navController = navController)
            CardViewWithInputNumber(navController = navController)
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun CardViewWithEditTexts(navController: NavController) {
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
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
                    if (!valueValidation(name, email, navController.context)) {
                        val user = UserInfo(name, email)

                        val arguments: MutableMap<String, String?> = HashMap()
                        arguments["name"] = name
                        arguments["email"] = email

                        FlutterUtil.navigateFlutterScreenWithData(
                            navController.context,
                            arguments = arguments,
                            ChannelConstants.KEY_NATIVE_TO_FLUTTER_SPECIFIC_ROUTE_WITH_DATA
                        )
                    }
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

private fun valueValidation(input1: String?, input2: String?, context: Context): Boolean {
    if (!input1.isNullOrEmpty() || !input2.isNullOrEmpty()) {
        Toast.makeText(context, "Input is not null or empty", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}

@Composable
fun CardViewWithImageView(navController: NavController) {
    val paddingModifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()

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
                onClick = {
                    FlutterUtil.navigateToFlutter(
                        context = navController.context,
                        ChannelConstants.KEY_NATIVE_TO_FLUTTER_SPECIFIC_ROUTE
                    )
                },
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
fun CardViewWithInputNumber(navController: NavController){
    var value1 by rememberSaveable { mutableStateOf("") }
    var value2 by rememberSaveable { mutableStateOf("") }
    val paddingModifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()

    Card(
        modifier = paddingModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
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
                    if (!valueValidation(value1, value2, navController.context)) {
                        val value = UserInfo(value1, value2)

                        val arguments: MutableMap<String, String?> = HashMap()
                        arguments["value1"] = value1
                        arguments["value2"] = value2

                        val valueJson = Gson().toJson(value)

                        FlutterUtil.navigateFlutterWithOnlyData(
                            item = arguments,
                            ChannelConstants.KEY_NATIVE_TO_FLUTTER_OBJECT_PASS,
                            navController.context
                        )
                    }

                    //navController.navigate(Screen.AfterFlutter.route)
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
