package com.nuhin13.parent_android_native.flutter_communication

import io.flutter.plugin.common.MethodChannel

class FlutterModuleGenerator {
    var flutterFeature: String? = null
    var methodChannel: MethodChannel? = null
    var result: MethodChannel.Result? = null
    var isResumed = false

    fun clearModuleGenerator() {
        if (flutterModuleGenerator != null) {
            flutterModuleGenerator = null
        }
    }

    companion object {
        private var flutterModuleGenerator: FlutterModuleGenerator? = null
        val instance: FlutterModuleGenerator?
            get() {
                if (flutterModuleGenerator == null) {
                    flutterModuleGenerator = FlutterModuleGenerator()
                }
                return flutterModuleGenerator
            }
    }
}