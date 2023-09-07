package com.nuhin13.parent_android_native.flutter_communication

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import java.util.ArrayList

class MainMethodChannel(val context: Context): MethodChannel.MethodCallHandler {

    override fun onMethodCall(methodCall: MethodCall, result: MethodChannel.Result) {
        when (methodCall.method) {
/*            Constants.KEY_METHOD_PAYMENT_LINK -> {
                if (methodCall.arguments.toString().isNotEmpty()) {
                    val data = methodCall.arguments.toString()
                    val info = Gson().fromJson(data, OrderInfo::class.java)
                    PosModuleIntImp().goToDCFromPos(
                        context, info.orderAmount
                            ?: "0", info.order, isNewPos = true
                    )

                    FlutterModuleGenerator.getInstance().result = result
                }
            }*/
           /* Constants.KEY_METHOD_GET_IMAGE -> {
                MediaUtils.getMediaData(context)
                FlutterModuleGenerator.getInstance().result = result
            }
            */
           /* Constants.KEY_METHOD_GET_USER_ID -> {
                try {
                    result.success((AppPrefRepository(context).partnerId).toString())
                } catch (e: Exception) {
                }
            }*/
            /*Constants.KEY_METHOD_USR_AGENT -> {
                result.success(System.getProperty("http.agent") ?: "")
            }*/
           /* Constants.KEY_GO_TO_SUBSCRIPTION -> {
                if (methodCall.arguments.toString().isNotEmpty()) {
                    goToSubscription(context, methodCall.arguments.toString())
                    FlutterModuleGenerator.getInstance().result = result
                }
            }*/

/*            Constants.KEY_METHOD_FROM_EKYC -> {
                if (methodCall.arguments.toString().isNotEmpty()) {
                    val status = methodCall.arguments.toString()
                    if (status == "success") {
                        //AppPrefRepository(context).partnerData = null
                        val nidModuleGenerator = NidModuleGenerator.newInstance()
                        nidModuleGenerator.clearNidData(context)
                        CommonUtil.goToNextActivityByClearingHistory(
                            context,
                            LoaderActivity::class.java
                        )
                    } else CommonUtil.goToNextActivityByClearingHistory(
                        context,
                        HomeLandingActivity::class.java
                    )
                    result.success(null)
                }
            }*/

            /*else -> CommonUtil.goToNextActivityByClearingHistory(
                context,
                HomeLandingActivity::class.java
            )*/
        }
    }


    /*private fun setVideoData(url: String, thumb: String): String {
        val flutterDataModel = VideoData()
        flutterDataModel.video_url = url
        flutterDataModel.video_thumb = thumb
        return Gson().toJson(flutterDataModel)
    }*/
}

class StartUpMethodChannel : MethodChannel.MethodCallHandler {
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            /*Constants.KEY_METHOD_SHOW_NATIVE -> {
                result.success(null)
            }
            Constants.KEY_METHOD_GET_VERSION -> {
                result.success(AppConfig.VERSION_CODE)
            }
            Constants.KEY_METHOD_GET_FLAVOR -> {
                result.success(AppConfig.FLAVOR)
            }*/
        }
    }

}