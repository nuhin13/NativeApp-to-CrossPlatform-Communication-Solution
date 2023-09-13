import 'package:flutter/material.dart';
import 'package:flutter_module/native_communication/user_info.dart';

import '../native_communication/channel_constant.dart';
import '../native_communication/platform_connection.dart';

class PhoneInputScreen extends StatefulWidget {
  const PhoneInputScreen({super.key, required this.name, required this.email});

  final String name;
  final String email;

  @override
  State<PhoneInputScreen> createState() => _PhoneInputScreenState();
}

class _PhoneInputScreenState extends State<PhoneInputScreen> {
  final myController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Input Phone Number"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Your Name: ${widget.name}',
            ),
            Container(
              height: 10,
            ),
            Text(
              'Your Name: ${widget.email}',
            ),
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: TextField(
                controller: myController,
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  labelText: 'Phone Number',
                ),
              ),
            ),
            ElevatedButton(
                onPressed: () {
                  navigateToNative();
                },
                child: const Text("Submit & Go To Native"))
          ],
        ),
      ),
    );
  }

  void navigateToNative() {
    if (myController.text.toString().isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(
          content: Text("Phone Number is Empty"),
        ),
      );
      return;
    }

    var user = UserInfo(
        name: widget.name,
        email: widget.email,
        phone: myController.text.toString());

    PlatformConnection().navigateWithData(
        ChannelConstants.KEY_FLUTTER_TO_NATIVE_SPECIFIC_ROUTE, user.toJson());
  }
}
