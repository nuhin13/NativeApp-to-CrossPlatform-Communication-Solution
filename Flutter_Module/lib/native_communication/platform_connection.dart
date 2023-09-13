import 'dart:io';

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/main.dart';
import 'package:flutter_module/native_communication/user_info.dart';
import 'package:flutter_module/screens/image_screen.dart';
import 'package:flutter_module/screens/phone_input_screen.dart';

import 'channel_constant.dart';

class PlatformConnection {
  static const methodChannel = MethodChannel(ChannelConstants.KEY_MAIN_CHANNEL);

  Future<void> navigateWithData(String key, dynamic data) async {
    try {
      await methodChannel.invokeMethod(key, data);
    } catch (e) {
      printLog(e.toString());
    }
  }

  Future<void> setMethodCallHandler(BuildContext context) async {
    methodChannel.setMethodCallHandler(
        (call) async => await _methodCallHandler(call, context));
  }

  Future<void> _objectCalculation(MethodCall methodCall) async {
    printLog("=== Object Calculation Starts");

    if (methodCall.arguments != null) {
      int value1 = int.parse(methodCall.arguments["value1"]);
      int value2 = int.parse(methodCall.arguments["value2"]);

      int result = value1 + value2;
      navigateWithData(ChannelConstants.KEY_FLUTTER_TO_NATIVE_OBJECT_PASS, result);
    }

    printLog("=== Object Calculation Ends");
  }

  Future<void> _goToSpecificScreen(
      MethodCall methodCall, BuildContext context) async {
    printLog("=== Go To Specific Screen Starts");

    Navigator.push(
        context,
        MaterialPageRoute(
            builder: (context) =>
                const MyHomePage(title: "This is from native")));
    printLog("=== Go To Specific Screen Ends");
  }

  Future<void> _goToSpecificScreenWithData(
      MethodCall methodCall, BuildContext context) async {
    printLog("=== Go To Specific Screen With Data Starts");

    if (methodCall.arguments != null) {
      var userInfo = UserInfo.fromJson(methodCall.arguments);

      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => PhoneInputScreen(
                  name: userInfo.name ?? " ", email: userInfo.email ?? " ")));
    }
  }

  Future<void> _goToSpecificScreenWithImage(
      MethodCall methodCall, BuildContext context) async {
    printLog("=== Go To Specific Screen With Image Starts");

    if (methodCall.arguments != null) {
      File imageFile = File(methodCall.arguments['image']);
      if (imageFile.existsSync() == false) {
        printLog("Not valid Image");
        return;
      }

      Navigator.push(
          context,
          MaterialPageRoute(
              builder: (context) => ImageShowScreen(imageFile: imageFile)));
    }
  }

  Future<dynamic> _methodCallHandler(
      MethodCall methodCall, BuildContext context) async {
    printLog("=== method call name ${methodCall.method}");
    printLog("=== method arguments ${methodCall.arguments}");

    switch (methodCall.method) {
      case ChannelConstants.KEY_NATIVE_TO_FLUTTER_OBJECT_PASS:
        _objectCalculation(methodCall);
        break;

      case ChannelConstants.KEY_NATIVE_TO_FLUTTER_SPECIFIC_ROUTE:
        _goToSpecificScreen(methodCall, context);
        break;

      case ChannelConstants.KEY_NATIVE_TO_FLUTTER_SPECIFIC_ROUTE_WITH_DATA:
        _goToSpecificScreenWithData(methodCall, context);
        break;

      case ChannelConstants.KEY_NATIVE_TO_FLUTTER_IMAGE_PASS:
        _goToSpecificScreenWithImage(methodCall, context);
        break;
    }
  }

  printLog(String message) {
    if (kDebugMode) {
      print(message);
    }
  }
}
