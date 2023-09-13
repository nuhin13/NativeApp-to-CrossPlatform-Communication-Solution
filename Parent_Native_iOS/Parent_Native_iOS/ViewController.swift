//
//  ViewController.swift
//  Parent_Native_iOS
//
//  Created by Polygon Technology on 5/9/23.
//

import UIKit
import Flutter


class ViewController: UIViewController, UIImagePickerControllerDelegate,UINavigationControllerDelegate{
    
    var flutterChannel: FlutterMethodChannel!
    var flutterEngine: FlutterEngine!
    var flutterViewController: FlutterViewController!

    @IBOutlet weak var editTextName: UITextField!
    @IBOutlet weak var editTextPhone: UITextField!
    @IBOutlet weak var editTextValue1: UITextField!
    @IBOutlet weak var editTextValue2: UITextField!
    
    @IBOutlet weak var btnNavigateFlutter: UIButton!
    @IBOutlet weak var btnCameraPick: UIButton!
    @IBOutlet weak var btnCalculateIntoFlutter: UIButton!
    @IBOutlet weak var btnNavigateFlutterWithImage:UIButton!
    
    @IBOutlet weak var imgPreview: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        initiateFlutterEngine()
        initCameraSelector()
    }
    
    func initCameraSelector() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(openCamera))
        self.view.addGestureRecognizer(gesture)
    }
    
    func initiateFlutterEngine() {
        flutterEngine = ((UIApplication.shared.delegate as? AppDelegate)?.flutterEngine)!;
        flutterViewController =  FlutterViewController(engine: flutterEngine, nibName: nil, bundle: nil);
        flutterChannel = FlutterMethodChannel(name: MethodChannelConstant.KEY_MAIN_CHANNEL, binaryMessenger: flutterViewController.binaryMessenger)
        
        setMethodChannel()
    }
    
    func setMethodChannel() {
        flutterChannel.setMethodCallHandler({
            [weak self] (call: FlutterMethodCall, result: FlutterResult) -> Void in
            
            print(call.arguments ?? "nil argument")
            print(call.method)
            
            switch call.method {
            case MethodChannelConstant.KEY_FLUTTER_TO_NATIVE_EXIT_FLUTTER:
                self?.exitFlutter()
            case MethodChannelConstant.KEY_FLUTTER_TO_NATIVE_OBJECT_PASS:
                self?.objectPass(argument: call.arguments as! String)
                
            case MethodChannelConstant.KEY_FLUTTER_TO_NATIVE_SPECIFIC_ROUTE:
                self?.navigateSpecificRoute(argument: call.arguments as! String)
                
            default:
                result(FlutterMethodNotImplemented)
                return
            }
        })
    }
    
    func exitFlutter() {
        self.dismiss(animated: true, completion: nil)
    }
    
    func objectPass(argument: Any?) {
        let args = argument as? Dictionary<String, Any>
        let name = args?["name"] as? String
        let email = args?["email"] as? String
        let phone = args?["phone"] as? String
        
        print("Arguments ", name!, " ", email!, " ", phone!)
    }
    
    func navigateSpecificRoute(argument: Any?) {
        let args = argument as? Dictionary<String, Any>
        let name = args?["name"] as? String
        let email = args?["email"] as? String
        let phone = args?["phone"] as? String
        
        print("Arguments ", name!, " ", email!, " ", phone!)
    }

    @objc func openCamera() {
        let vc = UIImagePickerController()
        vc.sourceType = .camera
        vc.allowsEditing = true
        vc.delegate = self
        present(vc, animated: true)
    }

    
    func objectPassIntoFlutterModule(methodName:String, jsonObject: NSMutableDictionary){
        print( " Object Pass Into Flutter Module ", jsonObject)
        
        flutterChannel.invokeMethod(methodName, arguments: jsonObject)
    }
    
    func navigateSpecificRouteInFlutter(methodName:String, jsonObject: NSMutableDictionary?) {
        print( " Navigate Specific Route In Flutter ", jsonObject ?? "No value")
        
        flutterChannel.invokeMethod(methodName, arguments: jsonObject ?? nil)
        self.present(flutterViewController, animated: false, completion: nil)
    }
   
    @IBAction func navigateToFlutterClick(_ sender: Any) {
        var name = editTextName.text ?? ""
        var phone = editTextPhone.text ?? ""
        
        if(name.isEmpty || phone.isEmpty){
            //showToast(message: "Please fill the info", font: <#T##UIFont#>)
            return
        }
        
        let jsonArg: NSMutableDictionary = NSMutableDictionary()

        jsonArg.setValue(name, forKey: "name")
        jsonArg.setValue(phone, forKey: "phone")
        
        print(name , " ", phone)
        
        navigateSpecificRouteInFlutter(methodName: MethodChannelConstant.KEY_NATIVE_TO_FLUTTER_SPECIFIC_ROUTE_WITH_DATA, jsonObject: jsonArg)
    }
    
    @IBAction func navigateToFlutterCalculation(_ sender: Any) {
        var value1 = editTextValue1.text ?? ""
        var value2 = editTextValue2.text ?? ""
        
        if(value1.isEmpty || value2.isEmpty){
            //showToast(message: "Please fill the info", font: <#T##UIFont#>)
            return
        }
        
        let jsonArg: NSMutableDictionary = NSMutableDictionary()

        jsonArg.setValue(value1, forKey: "value1")
        jsonArg.setValue(value2, forKey: "value2")
        
        print(value1 , " ", value2)
        
        objectPassIntoFlutterModule(methodName: MethodChannelConstant.KEY_NATIVE_TO_FLUTTER_OBJECT_PASS, jsonObject: jsonArg)
    }
    
    @IBAction func cameraOpenClick(_ sender: Any) {
        openCamera()
    }
    
    @IBAction func navigateToFlutterWithImage(_ sender: Any) {
        //TODO
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let image = info[.editedImage] as? UIImage else { return }
        
        self.imgPreview.image = image

        let imageName = UUID().uuidString
        let imagePath = getDocumentsDirectory().appendingPathComponent(imageName)

        if let jpegData = image.jpegData(compressionQuality: 0.8) {
            try? jpegData.write(to: imagePath)
        }

        print(imagePath)
        
        dismiss(animated: true)
    }

    func getDocumentsDirectory() -> URL {
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return paths[0]
    }
}

extension UIViewController {
    func showToast(message : String, font: UIFont) {
        let toastLabel = UILabel(frame: CGRect(x: self.view.frame.size.width/2 - 75, y: self.view.frame.size.height-100, width: 150, height: 35))
        toastLabel.backgroundColor = UIColor.black.withAlphaComponent(0.6)
        toastLabel.textColor = UIColor.white
        toastLabel.font = font
        toastLabel.textAlignment = .center;
        toastLabel.text = message
        toastLabel.alpha = 1.0
        toastLabel.layer.cornerRadius = 10;
        toastLabel.clipsToBounds  =  true
        self.view.addSubview(toastLabel)
        UIView.animate(withDuration: 4.0, delay: 0.1, options: .curveEaseOut, animations: {
            toastLabel.alpha = 0.0
        }, completion: {(isCompleted) in
            toastLabel.removeFromSuperview()
        })
    }
}
