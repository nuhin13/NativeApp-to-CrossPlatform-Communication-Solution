import 'dart:io';

import 'package:flutter/material.dart';

class ImageShowScreen extends StatefulWidget {
  const ImageShowScreen({super.key, required this.imageFile});

  final File imageFile;

  @override
  State<ImageShowScreen> createState() => _ImageShowScreenState();
}

class _ImageShowScreenState extends State<ImageShowScreen> {
  final myController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Image From Native"),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.file(
              widget.imageFile,
              fit: BoxFit.cover,
              height: 400,
              width: 400,
            )
          ],
        ),
      ),
    );
  }
}
