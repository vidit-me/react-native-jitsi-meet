# react-native-jitsi-meet

React Native Wrapper for Jitsi Meet SDK.

## Install

`yarn add react-native-jitsi-meet`.

`npx pod-install`.

Only support React Native >= 0.60.

Although most of the process is automated, you still have to follow the platform install guide below ([iOS](#ios-install) and [Android](#android-install)) to get this library to work.

## Usage

The following code is an example of use:

```
import JitsiMeet from 'react-native-jitsi-meet';

JitsiMeet.launch('room', {
      serverUrl: 'https://meet.jit.si',
      userInfo: {
        displayName: 'React Native Jitsi Meet Example',
        email: 'example@test.com',
        avatar: 'https://picsum.photos/200',
      },
});
```

You can also check the [ExampleApp](https://github.com/bortolilucas/react-native-jitsi-meet/tree/master/example).

## iOS Install

1.) This library uses Swift code, so make sure that you have created the `Objective-C bridging header file`.

If not, open your project in Xcode and create an empty Swift file.

Xcode will ask if you wish to create the bridging header file, please choose yes.

For more information check [Create Objective-C bridging header file](https://developer.apple.com/documentation/swift/imported_c_and_objective-c_apis/importing_objective-c_into_swift).

2.) Replace the following code in AppDelegate.m:

```
UIViewController *rootViewController = [UIViewController new];
rootViewController.view = rootView;
self.window.rootViewController = rootViewController;
```

with this one

```
UIViewController *rootViewController = [UIViewController new];
UINavigationController *navigationController = [[UINavigationController alloc]initWithRootViewController:rootViewController];
navigationController.navigationBarHidden = YES;
rootViewController.view = rootView;
self.window.rootViewController = navigationController;
```

This will create a navigation controller to be able to navigate between the Jitsi component and your react native screens.

3.) Add the following lines to your `Info.plist`

```
<key>NSCameraUsageDescription</key>
<string>Camera Permission</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone Permission</string>
<key>NSCalendarUsageDescription</key>
<string>Calendar Permission</string>
```

4.) Modify your platform version in Podfile and Xcode to have platform version `11.0` or above.

5.) In Xcode, under `Build settings` set `Enable Bitcode` to `No` and `Always Embed Swift Standard Libraries` to `Yes`.

6.) In Xcode, under `Signing & Capabilities` add the capability `Background Modes` and check `Voice over ip`. Otherwise, it won't work well in background.

7.) Clean your project and run `npx pod-install`.

## Android Install

1.) In `android/app/build.gradle`, add/replace the following lines:

```
project.ext.react = [
    entryFile: "index.js",
    bundleAssetName: "app.bundle",
    ...
]
```

2.) In `android/app/src/main/java/com/xxx/MainApplication.java` add/replace the following methods:

```
  import androidx.annotation.Nullable; // <--- Add this line if not already existing
  ...
    @Override
    protected String getJSMainModuleName() {
      return "index";
    }

    @Override
    protected @Nullable String getBundleAssetName() {
      return "app.bundle";
    }
```

3.) In `android/build.gradle`, add the following code

```
allprojects {
    repositories {
        mavenLocal()
        jcenter()
        maven {
            // All of React Native (JS, Obj-C sources, Android binaries) is installed from npm
            url "$rootDir/../node_modules/react-native/android"
        }
        maven {
            url "https://maven.google.com"
        }
        maven { // <---- Add this block
            url "https://github.com/jitsi/jitsi-maven-repository/raw/master/releases"
        }
        maven { url "https://jitpack.io" }
    }
}
```

4.) In the `<application>` section of `android/app/src/main/AndroidManifest.xml`, add

```xml
<activity
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|screenSize|smallestScreenSize"
    android:launchMode="singleTask"
    android:resizeableActivity="true"
    android:supportsPictureInPicture="true"
    android:windowSoftInputMode="adjustResize"
    android:name="com.reactnativejitsimeet.JitsiMeetActivityExtended">
</activity>
```

5.) And set your minSdkVersion to be at least 24.

### Side-note

If your having problems with `duplicate_classes` errors, try exclude them from the react-native-jitsi-meet project implementation with the following code (even if you're app uses autolinking with RN > 0.60):

```
implementation(project(':react-native-jitsi-meet')) {
  // Un-comment below if using hermes
  // exclude group: 'com.facebook',module:'hermes'
  // Un-comment any packages below that you have added to your project to prevent `duplicate_classes` errors
  // exclude group: 'com.facebook.react',module:'react-native-locale-detector'
  // exclude group: 'com.facebook.react',module:'react-native-vector-icons'
  // exclude group: 'com.facebook.react',module:'react-native-community-async-storage'
  // exclude group: 'com.facebook.react',module:'react-native-community_netinfo'
  // exclude group: 'com.facebook.react',module:'react-native-svg'
  // exclude group: 'com.facebook.react',module:'react-native-fetch-blob'
  // exclude group: 'com.facebook.react',module:'react-native-webview'
  // exclude group: 'com.facebook.react',module:'react-native-linear-gradient'
  // exclude group: 'com.facebook.react',module:'react-native-sound'
}
```
