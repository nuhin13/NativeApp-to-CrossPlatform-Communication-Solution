package com.nuhin13.parent_android_native.flutter_communication

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nuhin13.parent_android_native.Util
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_ENGINE_ID
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_MAIN_CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

object FlutterUtil {


    var methodChannel: MethodChannel? = null

    /**
     * Start the Flutter engine. This is a singleton method.
     * Call this method from the Application class or at the entry point of your application.
     * Start executing Dart code to pre-warm the FlutterEngine.
     * Cache the FlutterEngine to be used by FlutterActivity.
     */
    fun startEngine(context: Context) {
        try {
            FlutterEngineCache.getInstance().get(KEY_ENGINE_ID)?.destroy()
            FlutterEngineCache.getInstance().remove(KEY_ENGINE_ID)
            val flutterEngine = FlutterEngine(context)

            flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

            FlutterEngineCache.getInstance().put(KEY_ENGINE_ID, flutterEngine)
            methodChannel = callMethodChannel(flutterEngine, MainMethodChannel(context))
        } catch (e: Exception) {
            Util.print(e.toString(), "Start Engine Error")
        }
    }

    /**
     * Initiate the Method Channel. This is a singleton method.
     * This the main method channel that will be used to communicate with the Flutter module.
     */

    fun callMethodChannel(
        flutterEngine: FlutterEngine?,
        handler: MethodChannel.MethodCallHandler
    ): MethodChannel? {
        return try {
            val channel = KEY_MAIN_CHANNEL
            val methodChannel =
                flutterEngine?.dartExecutor?.binaryMessenger?.let { MethodChannel(it, channel) }

            methodChannel?.setMethodCallHandler(handler)
            methodChannel
        } catch (ex: Exception) {
            null
        }
    }

    /**
     * Navigate to the Flutter module
     */

    fun navigateToFlutter(context: Context, toFlutter: String, arguments:Any? = null) {
        try {
            FlutterModuleGenerator.instance!!.flutterFeature = toFlutter
            FlutterModuleGenerator.instance!!.channelArgument = arguments
            context.startActivity(
                FlutterContainerActivity
                    .withCachedEngine(KEY_ENGINE_ID)
                    .destroyEngineWithActivity(false)
                    .build(context)
            )
        } catch (e: Exception) {
            Util.print(e.toString(), "Navigate To Flutter Error")
        }
    }


    /**
     * Generic method to navigate to the Flutter module with data
     */

    fun <T> navigateFlutterWithOnlyData(item: T, navigationRoute: String, context: Context) {
        try {
            //val jsonString = Gson().toJson(item)
            methodChannel!!.invokeMethod(navigationRoute, item)
        } catch (e: Exception) {
            Util.print(e.toString(), "Navigate Flutter With Only Data Error")
        }

    }

    /**
     * Navigate with Specific Screen with data
     */

    fun <T> navigateFlutterScreenWithData(
        context: Context,
        arguments: T?,
        navigationRoute: String
    ) {
        try {
            if (methodChannel != null) {
                FlutterModuleGenerator.instance?.methodChannel = methodChannel



                //val jsonString = Gson().toJson(arguments)
                print("from parent$arguments")

                navigateToFlutter(context, navigationRoute, arguments)

                //methodChannel!!.invokeMethod(navigationRoute, arguments)
            }
        } catch (e: Exception) {
            Util.print(e.toString(), "Navigate Flutter Screen With Data Error")
        }
    }
}