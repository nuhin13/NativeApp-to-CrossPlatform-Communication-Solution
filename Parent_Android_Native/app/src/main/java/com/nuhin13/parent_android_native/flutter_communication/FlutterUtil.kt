package com.nuhin13.parent_android_native.flutter_communication

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_ENGINE_ID
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_MAIN_CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

object FlutterUtil {

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

            FlutterEngineCache
                .getInstance()
                .put(KEY_ENGINE_ID, flutterEngine)
            callMethodChannel(flutterEngine, StartUpMethodChannel())
        } catch (e: Exception) {
            Log.e("Start Engine Error", e.toString())
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

    fun navigateToFlutter(context: Context, toFlutter: String) {
        try {
            FlutterModuleGenerator.instance!!.flutterFeature = toFlutter
            context.startActivity(
                FlutterContainerActivity
                    .withCachedEngine(KEY_ENGINE_ID)
                    .destroyEngineWithActivity(false)
                    .build(context)
            )
        } catch (e: Exception) {
            Log.e("Navigate To Flutter Error", e.toString())
        }
    }


    /**
     * Generic method to navigate to the Flutter module with data
     */

    fun <T> navigateFlutterWithOnlyData(item: T, navigationRoute: String) {
        try {
            val jsonString = Gson().toJson(item)
            val channel = FlutterModuleGenerator.instance!!.methodChannel
            channel!!.invokeMethod(navigationRoute, jsonString)
        } catch (e: Exception) {
            Log.e("Navigate Flutter With Only Data Error", e.toString())
        }

    }

    /**
     * Navigate with Specific Screen with data
     */

    fun navigateFlutterScreenWithData(
        context: Context, arguments: MutableMap<String, String?>,
        navigationRoute: String
    ) {
        try {
            val engine = FlutterEngineCache.getInstance().get(KEY_ENGINE_ID)
            val channel = callMethodChannel(engine, MainMethodChannel(context))

            if (channel != null) {
                FlutterModuleGenerator.instance?.methodChannel = channel

                print("from parent$arguments")

                channel.invokeMethod(navigationRoute, arguments)
            }
        } catch (e: Exception) {
            Log.e("Navigate Flutter Screen With Data Error", e.toString())
        }
    }
}