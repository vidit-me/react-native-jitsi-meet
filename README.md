Set bitcode to NO

Set platform ios to 11.0

UIViewController *rootViewController = [UIViewController new];
UINavigationController *navigationController = [[UINavigationController alloc]initWithRootViewController:rootViewController];
navigationController.navigationBarHidden = YES;
rootViewController.view = rootView;
self.window.rootViewController = navigationController;

<key>NSCameraUsageDescription</key>
<string>Camera Permission</string>
<key>NSMicrophoneUsageDescription</key>
<string>Microphone Permission</string>
<key>NSCalendarUsageDescription</key>
<string>Microphone Permission</string>

Share screen

<key>UIBackgroundModes</key>
<array>
</array>
contains <string>voip</string>

project.ext.react = [
entryFile: "index.js",
bundleAssetName: "app.bundle",
]

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

    implementation(project(':react-native-jitsi-meet')) {
      exclude group: 'com.facebook.react',module:'react-native-locale-detector'
      exclude group: 'com.facebook.react',module:'react-native-vector-icons'
      // Un-comment below if using hermes
      //exclude group: 'com.facebook',module:'hermes'
      // Un-comment any packages below that you have added to your project to prevent `duplicate_classes` errors
      //exclude group: 'com.facebook.react',module:'react-native-community-async-storage'
      //exclude group: 'com.facebook.react',module:'react-native-community_netinfo'
      //exclude group: 'com.facebook.react',module:'react-native-svg'
      //exclude group: 'com.facebook.react',module:'react-native-fetch-blob'
      //exclude group: 'com.facebook.react',module:'react-native-webview'
      //exclude group: 'com.facebook.react',module:'react-native-linear-gradient'
      //exclude group: 'com.facebook.react',module:'react-native-sound'
    }

android:allowBackup="false"

<activity
          android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|screenSize|smallestScreenSize"
          android:launchMode="singleTask"
          android:resizeableActivity="true"
          android:supportsPictureInPicture="true"
          android:windowSoftInputMode="adjustResize"
          android:name="com.reactnativejitsimeet.JitsiMeetActivityExtended">
</activity>

maven url

Always Embed Swift
