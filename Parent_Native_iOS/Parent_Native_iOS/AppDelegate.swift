//
//  AppDelegate.swift
//
//  Created by nuhin13 on 12/9/23.
//

import UIKit
import Flutter
import FlutterPluginRegistrant

@UIApplicationMain
class AppDelegate: FlutterAppDelegate {
    
    var flutterEngine : FlutterEngine?;

    override func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
    self.flutterEngine = FlutterEngine(name: MethodChannelConstant.KEY_ENGINE_ID, project: nil);
    self.flutterEngine?.run(withEntrypoint: nil);
    GeneratedPluginRegistrant.register(with: self.flutterEngine!);
    return super.application(application, didFinishLaunchingWithOptions: launchOptions);
  }
}
