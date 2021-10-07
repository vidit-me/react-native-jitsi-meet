# React Native JitsiMeet

React Native Wrapper for Jitsi Meet SDK.

## Install

```sh
yarn add @bortolilucas/react-native-jitsimeet

or

npm i --save @bortolilucas/react-native-jitsimeet
```

Only supports React Native >= 0.60.

## Usage

The package can be invoked in two modes

1. As a new Activity/UIViewController on top of RN Application
2. As a view inside the RN Application

```js
import JitsiMeet, { JitsiMeetView } from '@bortolilucas/react-native-jitsimeet';
import React, { useState } from 'react';
import { StyleSheet } from 'react-native';

const conferenceOptions = {
  room: 'ReactNativeJitsiRoom',
  userInfo: {
    displayName: 'React Native Jitsi Meet Example',
    email: 'example@test.com',
    avatar: 'https://picsum.photos/200',
  },
  pipEnabled: false,
};

function App() {
  const [showJitsiView, setShowJitsiView] = useState(false);

  const startJitsiAsNativeController = () => {
    /* 
      Mode 1 - Starts a new Jitsi Activity/UIViewController 
      on top of RN Application (outside of JS). 
      It doesn't require rendering <JitsiMeetView />.  
    */
    JitsiMeet.launch(conferenceOptions);
  };

  const startJitsiView = () => setShowJitsiView(true);

  const onConferenceTerminated = () => setShowJitsiView(false);

  return (
    showJitsiView && (
      /* Mode 2 - Starts Jitsi as a RN View */
      <JitsiMeetView
        style={styles.jitsiMeetView}
        options={conferenceOptions}
        onConferenceTerminated={onConferenceTerminated}
      />
    )
  );
}

const styles = StyleSheet.create({
  jitsiMeetView: {
    flex: 1,
  },
});

export default App;
```

See [Options](#options) for further information.

## iOS install

1.) This library uses Swift code, so make sure that you have created the `Objective-C bridging header file`.

If not, open your project in Xcode and create an empty Swift file.

Xcode will ask if you wish to create the bridging header file, please choose yes.

For more information check [Create Objective-C bridging header file](https://developer.apple.com/documentation/swift/imported_c_and_objective-c_apis/importing_objective-c_into_swift).

2.) Replace the following code in AppDelegate.m (ONLY required for invoking as a new UIViewController):

```objective-c
UIViewController *rootViewController = [UIViewController new];
rootViewController.view = rootView;
self.window.rootViewController = rootViewController;
```

with this one

```objective-c
UIViewController *rootViewController = [UIViewController new];
UINavigationController *navigationController = [[UINavigationController alloc]initWithRootViewController:rootViewController];
navigationController.navigationBarHidden = YES;
rootViewController.view = rootView;
self.window.rootViewController = navigationController;
```

This will create a navigation controller to be able to navigate between the Jitsi component and your react native screens.

3.) Add the following lines to your `Info.plist`

```xml
<key>NSCameraUsageDescription</key>
<string>Camera Permission</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone Permission</string>
<key>NSCalendarUsageDescription</key>
<string>Calendar Permission</string>
```

4.) Modify your platform version in Podfile and Xcode to have platform version `11.0` or above.

5.) In Xcode, under `Build settings` set `Enable Bitcode` to `No` and `Always Embed Swift Standard Libraries` to `Yes`.

6.) In Xcode, under `Signing & Capabilities` add the capability `Background Modes` and check `VoIP`. Otherwise, it won't work well in background.

7.) Clean your project and run `npx pod-install`.

## Android install

### Important

There seems to be an issue with `RN >= 0.64` and Jitsi SDK, as buttons doesn't respond and other bugs, so until the issue is sorted out it is advised to use the `RN 0.63`

1.) In `android/app/build.gradle`, add/replace the following lines:

```groovy
project.ext.react = [
    entryFile: "index.js",
    bundleAssetName: "app.bundle",
    ...
]
```

2.) In `android/app/src/main/java/com/xxx/MainApplication.java` add/replace the following methods:

```java
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

```groovy
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

4.) In the `<application>` section of `android/app/src/main/AndroidManifest.xml`, add (ONLY required for invoking as a new Activity)

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

```groovy
buildscript {
    ext {
        buildToolsVersion = "29.0.3"
        minSdkVersion = 24 // <-- this line
        compileSdkVersion = 29
        targetSdkVersion = 29
        ndkVersion = "20.1.5948944"
    }
    ...
}
```

6.) Remove allow back up from Androidmanifest.xml

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
  package="com.sdktest">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
      android:name=".MainApplication"
      android:label="@string/app_name"
      android:icon="@mipmap/ic_launcher"
      android:roundIcon="@mipmap/ic_launcher_round"
      android:allowBackup="false" <-- this line
      android:theme="@style/AppTheme">
      <activity
        android:name=".MainActivity"
        android:label="@string/app_name"
        android:configChanges="keyboard|keyboardHidden|orientation|screenSize|uiMode"
        android:launchMode="singleTask"
        android:windowSoftInputMode="adjustResize">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
      </activity>
    </application>
</manifest>
```

## Options

| key                    | Data type | Default                             | Description                                                                                            |
| ---------------------- | --------- | ----------------------------------- | ------------------------------------------------------------------------------------------------------ |
| room                   | string    | required                            | Room name for Jitsi Meet                                                                               |
| serverUrl              | string    | https://meet.jit.si                 | Valid server URL                                                                                       |
| token                  | string    | ""                                  | JWT token                                                                                              |
| userInfo               | object    | {}                                  | Object that contains information about the participant starting the meeting. See [UserInfo](#userinfo) |
| screenSharingEnabled   | boolean   | true (android) false (iOS)          | Flag indicating if screen sharing should be enabled                                                    |
| conferenceTimerEnabled | boolean   | true                                | Flag indicating if conference timer should be enabled                                                  |
| addPeopleEnabled       | boolean   | true                                | Flag indicating if add-people functionality should be enabled                                          |
| calendarEnabled        | boolean   | true (android) auto-detected (iOS)  | Flag indicating if calendar integration should be enabled                                              |
| inviteEnabled          | boolean   | true                                | Flag indicating if invite functionality should be enabled                                              |
| meetingPasswordEnabled | boolean   | true                                | Flag indicating if the meeting password button should be enabled                                       |
| recordingEnabled       | boolean   | auto-detected (android) false (iOS) | Flag indicating if recording should be enabled                                                         |
| liveStreamingEnabled   | boolean   | auto-detected                       | Flag indicating if live-streaming should be enabled                                                    |
| raiseHandEnabled       | boolean   | true                                | Flag indicating if raise hand feature should be enabled                                                |
| serverUrlChangeEnabled | boolean   | true                                | Flag indicating if server URL change is enabled                                                        |
| videoShareEnabled      | boolean   | true                                | Flag indicating if the video share button should be enabled                                            |
| securityOptionsEnabled | boolean   | true                                | Flag indicating if the security options button should be enabled                                       |
| chatEnabled            | boolean   | true                                | Flag indicating if chat should be enabled                                                              |
| lobbyModeEnabled       | boolean   | true                                | Flag indicating if lobby mode button should be enabled                                                 |
| pipEnabled             | boolean   | true (android)                      | Flag indicating if Picture-in-Picture should be enabled (only Android)                                 |

## UserInfo

| key         | Data type | Default | Description              |
| ----------- | --------- | ------- | ------------------------ |
| displayName | string    | ""      | Participant's name       |
| email       | string    | ""      | Participant's e-mail     |
| avatar      | string    | ""      | Participant's avatar URL |

## Screen Sharing

It is already enabled by default on Android.

On iOS it requires a few extra steps. Set the flag `screenSharingEnabled` to true and follow this tutorial [Screen Sharing iOS](https://jitsi.github.io/handbook/docs/dev-guide/dev-guide-ios-sdk#screen-sharing-integration) to get it working.

### Side-note

If your having problems with `duplicate_classes` errors, try exclude them from the react-native-jitsimeet project implementation with the following code:

```groovy
implementation(project(':react-native-jitsimeet')) {
  // Un-comment below if using hermes
  exclude group: 'com.facebook',module:'hermes'
  // Un-comment any packages below that you have added to your project to prevent `duplicate_classes` errors
  exclude group: 'com.facebook.react',module:'react-native-locale-detector'
  exclude group: 'com.facebook.react',module:'react-native-vector-icons'
  // exclude group: 'com.facebook.react',module:'react-native-community-async-storage'
  // exclude group: 'com.facebook.react',module:'react-native-community_netinfo'
  // exclude group: 'com.facebook.react',module:'react-native-svg'
  // exclude group: 'com.facebook.react',module:'react-native-fetch-blob'
  // exclude group: 'com.facebook.react',module:'react-native-webview'
  // exclude group: 'com.facebook.react',module:'react-native-linear-gradient'
  // exclude group: 'com.facebook.react',module:'react-native-sound'
}
```
