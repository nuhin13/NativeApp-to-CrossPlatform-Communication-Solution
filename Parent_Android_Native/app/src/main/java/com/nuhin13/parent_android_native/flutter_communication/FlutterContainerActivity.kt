package com.nuhin13.parent_android_native.flutter_communication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.nuhin13.parent_android_native.flutter_communication.ChannelConstants.Companion.IMAGE_CONSTANT_CODE
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngineCache
import kotlinx.coroutines.delay

class FlutterContainerActivity : FlutterFragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val engine = FlutterEngineCache.getInstance().get(ChannelConstants.KEY_ENGINE_ID)
        val channel = FlutterUtil.callMethodChannel(engine, MainMethodChannel(this))
        if(channel != null) {
            FlutterModuleGenerator.instance?.methodChannel = channel
            FlutterModuleGenerator.instance?.flutterFeature?.let { channel.invokeMethod(it, null) }
        } else {
            lifecycleScope.launchWhenResumed {
                delay(200)
                Toast.makeText(this@FlutterContainerActivity, "Restart and try again",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        try {
//            if (!FlutterModuleGenerator.getInstance().isResumed) {
//                val channel = FlutterModuleGenerator.getInstance().methodChannel
//                channel.invokeMethod(FlutterModuleGenerator.getInstance().flutterFeature, null)
//                FlutterModuleGenerator.getInstance().isResumed = true
//            }
            val result = FlutterModuleGenerator.instance?.result
            if (result != null) {
                result.success("Succeed")
                FlutterModuleGenerator.instance?.result = null
            }
        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        try {
            when (requestCode) {
                 IMAGE_CONSTANT_CODE -> {
                    try {
                        if (!(resultCode == Activity.RESULT_OK && imageReturnedIntent != null)) {
                            FlutterModuleGenerator.instance?.result = null
                            return
                        }
                        /*val mediaFile: MediaFile? = imageReturnedIntent.getParcelableExtra(MediaConstants.MEDIA_BUNDLE)
                        val result = FlutterModuleGenerator.getInstance().result
                        val path = FileUtils(this).getPath(mediaFile?.getUri())
                        result.success(path)
                        FlutterModuleGenerator.getInstance().result = null*/
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, "Unable To Fetch Image!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception){}
    }

    companion object {
        fun withCachedEngine(engineId: String) = CustomCachedEngineIntentBuilder(engineId)
    }

    class CustomCachedEngineIntentBuilder(engineId: String) :
        FlutterFragmentActivity.CachedEngineIntentBuilder(FlutterContainerActivity::class.java, engineId)

//    override fun onDestroy() {
//        flutterEngine?.platformViewsController?.detachFromView()
//        super.onDestroy()
//    }

}