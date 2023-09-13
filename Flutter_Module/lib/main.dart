import 'package:flutter/material.dart';
import 'package:flutter_module/native_communication/channel_constant.dart';
import 'package:flutter_module/native_communication/platform_connection.dart';
import 'package:flutter_module/screens/phone_input_screen.dart';

void main() => runApp(const MyApp());

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  late PlatformConnection _platformChannel;

  @override
  void initState() {
    WidgetsBinding.instance.addPostFrameCallback((_) {
      _platformChannel = PlatformConnection();
      _platformChannel.setMethodCallHandler(context);
    });

    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'This is Flutter Module'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      _counter++;
    });
  }

  @override
  Widget build(BuildContext context) {
    print("Build Called");

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const PhoneInputScreen(
                            name: "D", email: "dd@g.com")));
              },
              child: const Text(
                "Go to Next",
              ),
            ),
            Container(height: 24),
            ElevatedButton(
              onPressed: () {
                PlatformConnection().navigateWithData(
                    ChannelConstants.KEY_FLUTTER_TO_NATIVE_EXIT_FLUTTER, null);
              },
              child: const Text(
                "Exit Flutter Module",
              ),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
