package com.nuhin13.parent_android_native.flutter_communication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.lifecycleScope
import com.nuhin13.parent_android_native.Util
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.IMAGE_CONSTANT_CODE
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import kotlinx.coroutines.delay

class FlutterContainerActivity : FlutterFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val engine = FlutterEngineCache.getInstance().get(ChannelConstants.KEY_ENGINE_ID)
        val channel = FlutterUtil.callMethodChannel(engine, MainMethodChannel(this))
        if (channel != null) {
            FlutterModuleGenerator.instance?.methodChannel = channel
            val arg = FlutterModuleGenerator.instance?.channelArgument

            FlutterModuleGenerator.instance?.flutterFeature?.let {
                channel.invokeMethod(it, arg)
            }
        } else {
            lifecycleScope.launchWhenResumed {
                delay(200)
                Toast.makeText(
                    this@FlutterContainerActivity, "Restart and try again",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            val result = FlutterModuleGenerator.instance?.result
            if (result != null) {
                result.success("Succeed")
                FlutterModuleGenerator.instance?.result = null
            }
        } catch (e: Exception) {
            Util.print(message = e.toString(), tag = "FlutterContainerActivity Error")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            when (requestCode) {
                IMAGE_CONSTANT_CODE -> {
                    try {
                        if (!(resultCode == Activity.RESULT_OK && data != null)) {
                            FlutterModuleGenerator.instance?.result = null
                            return
                        }

                        val imageBitmap = data.extras?.get("data") as Bitmap

                        val uri = Util.getImageUri(this, imageBitmap, "back")
                        val path = Util.getRealPathFromURI(uri!!, this)

                        val result = FlutterModuleGenerator.instance?.result
                        result?.success(path)
                        FlutterModuleGenerator.instance?.result = null

                    } catch (e: Exception) {
                        Toast.makeText(
                            applicationContext,
                            "Unable To Fetch Image!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } catch (e: Exception) {
            Util.print(message = e.toString(), tag = "onActivityResult Image Error")
        }
    }

    companion object {
        fun withCachedEngine(engineId: String) = CustomCachedEngineIntentBuilder(engineId)
    }

    class CustomCachedEngineIntentBuilder(engineId: String) :
        CachedEngineIntentBuilder(FlutterContainerActivity::class.java, engineId)

    override fun onDestroy() {
        flutterEngine?.platformViewsController?.detachFromView()
        super.onDestroy()
    }
}