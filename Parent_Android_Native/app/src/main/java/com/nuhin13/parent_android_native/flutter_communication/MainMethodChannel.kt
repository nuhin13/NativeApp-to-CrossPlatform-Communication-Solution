package com.nuhin13.parent_android_native.flutter_communication

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.nuhin13.parent_android_native.Util
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_FLUTTER_TO_NATIVE_EXIT_FLUTTER
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_FLUTTER_TO_NATIVE_OBJECT_PASS
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_FLUTTER_TO_NATIVE_SPECIFIC_ROUTE
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MainMethodChannel(private val context: Context) : MethodChannel.MethodCallHandler {

    /**
     * This method is called when a call is made from Flutter with the help of the Method Channel.
     */

    override fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
        Util.print(message = methodCall.arguments.toString(), tag = "Method Call")
        Util.print(message = methodCall.method, tag = "Method Call")

        val argData = methodCall.arguments.toString()

        when (methodCall.method) {
            KEY_FLUTTER_TO_NATIVE_OBJECT_PASS -> {
                if (methodCall.arguments.toString().isNotEmpty()) {
                    val info = Gson().fromJson(argData, UserInfo::class.java)
                    Util.print(message = info.toString(), tag = KEY_FLUTTER_TO_NATIVE_OBJECT_PASS)

                    //FlutterModuleGenerator.instance().result = result
                }
            }

            KEY_FLUTTER_TO_NATIVE_SPECIFIC_ROUTE -> {
                //TODO
            }

            KEY_FLUTTER_TO_NATIVE_EXIT_FLUTTER -> {
                Toast.makeText(context, "Flutter Module Exit", Toast.LENGTH_LONG).show()
            }

            else -> {
                //TODO
                /*CommonUtil.goToNextActivityByClearingHistory(
                    context,
                    HomeLandingActivity::class.java
                )*/
            }
        }
    }
}
