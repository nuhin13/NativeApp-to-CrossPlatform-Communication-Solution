package com.nuhin13.parent_android_native.flutter_communication

import android.content.Context
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_ENGINE_ID
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.KEY_MAIN_CHANNEL
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

object FlutterUtil {

    fun startEngine(context: Context) {
        try {
            FlutterEngineCache.getInstance().get(KEY_ENGINE_ID)?.destroy()
            FlutterEngineCache.getInstance().remove(KEY_ENGINE_ID)
            val flutterEngine = FlutterEngine(context)

            // Start executing Dart code to pre-warm the FlutterEngine.
            flutterEngine.dartExecutor.executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())

            // Cache the FlutterEngine to be used by FlutterActivity.
            FlutterEngineCache
                .getInstance()
                .put(KEY_ENGINE_ID, flutterEngine)
            callMethodChannel(flutterEngine, StartUpMethodChannel())
        } catch (e: Exception) {
        }
    }

    fun callMethodChannel(flutterEngine: FlutterEngine?, handler: MethodChannel.MethodCallHandler): MethodChannel? {
        return try {
            val channel = KEY_MAIN_CHANNEL
            // Pass message to flutter module
            val methodChannel =
                flutterEngine?.dartExecutor?.binaryMessenger?.let { MethodChannel(it, channel) }

            methodChannel?.setMethodCallHandler(handler)
            methodChannel
        } catch (ex: Exception) {
            null
        }
    }

    fun navigateToFlutter(context: Context, toFlutter: String) {
        try {
            FlutterModuleGenerator.instance?.flutterFeature = toFlutter
            context.startActivity(
                FlutterContainerActivity
                    .withCachedEngine(KEY_ENGINE_ID)
                    .destroyEngineWithActivity(false)
                    .build(context)
            )
            //(context as Activity).overridePendingTransition(R.anim.right_in, R.anim.left_out)
        } catch (e: Exception) {
        }
    }
}