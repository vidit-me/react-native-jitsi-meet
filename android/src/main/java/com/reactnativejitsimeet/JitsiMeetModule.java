package com.reactnativejitsimeet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.module.annotations.ReactModule;

import org.jitsi.meet.sdk.BroadcastEvent;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;

import java.net.MalformedURLException;
import java.net.URL;

@ReactModule(name = JitsiMeetModule.NAME)
public class JitsiMeetModule extends ReactContextBaseJavaModule {
  public static final String NAME = "JitsiMeet";

  private BroadcastReceiver onConferenceTerminatedReceiver;

  public JitsiMeetModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }

  @ReactMethod
  public void launchJitsiMeetView(ReadableMap options, Callback onConferenceTerminated) {
    JitsiMeetConferenceOptions.Builder builder = new JitsiMeetConferenceOptions.Builder();

    if (options.hasKey("room")) {
      builder.setRoom(options.getString("room"));
    } else {
      throw new RuntimeException("Room must not be empty");
    }

    try {
      builder.setServerURL(
        new URL(options.hasKey("serverUrl") ? options.getString("serverUrl") : "https://meet.jit.si"));
    } catch (MalformedURLException e) {
      throw new RuntimeException("Server url invalid");
    }

    if (options.hasKey("userInfo")) {
      ReadableMap userInfoMap = options.getMap("userInfo");

      if (userInfoMap != null) {
        JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();

        if (userInfoMap.hasKey("displayName")) {
          userInfo.setDisplayName(userInfoMap.getString("displayName"));
        }

        if (userInfoMap.hasKey("email")) {
          userInfo.setEmail(userInfoMap.getString("email"));
        }

        if (userInfoMap.hasKey("avatar")) {
          try {
            userInfo.setAvatar(new URL(userInfoMap.getString("avatar")));
          } catch (MalformedURLException e) {
            throw new RuntimeException("Avatar url invalid");
          }
        }

        builder.setUserInfo(userInfo);
      }
    }

    if (options.hasKey("token")) {
      builder.setToken(options.getString("token"));
    }

    if (options.hasKey("conferenceTimerEnabled")) {
      builder.setFeatureFlag("conference-timer.enabled", options.getBoolean("conferenceTimerEnabled"));
    }

    if (options.hasKey("addPeopleEnabled")) {
      builder.setFeatureFlag("add-people.enabled", options.getBoolean("addPeopleEnabled"));
    }

    if (options.hasKey("calendarEnabled")) {
      builder.setFeatureFlag("calendar.enabled", options.getBoolean("calendarEnabled"));
    }

    if (options.hasKey("closeCaptionsEnabled")) {
      builder.setFeatureFlag("close-captions.enabled", options.getBoolean("closeCaptionsEnabled"));
    }

    if (options.hasKey("inviteEnabled")) {
      builder.setFeatureFlag("invite.enabled", options.getBoolean("inviteEnabled"));
    }

    if (options.hasKey("meetingPasswordEnabled")) {
      builder.setFeatureFlag("meeting-password.enabled", options.getBoolean("meetingPasswordEnabled"));
    }

    if (options.hasKey("liveStreamingEnabled")) {
      builder.setFeatureFlag("live-streaming.enabled", options.getBoolean("liveStreamingEnabled"));
    }

    if (options.hasKey("raiseHandEnabled")) {
      builder.setFeatureFlag("raise-hand.enabled", options.getBoolean("raiseHandEnabled"));
    }

    if (options.hasKey("recordingEnabled")) {
      builder.setFeatureFlag("recording.enabled", options.getBoolean("recordingEnabled"));
    }

    if (options.hasKey("serverUrlChangeEnabled")) {
      builder.setFeatureFlag("server-url-change.enabled", options.getBoolean("serverUrlChangeEnabled"));
    }

    if (options.hasKey("videoShareEnabled")) {
      builder.setFeatureFlag("video-share.enabled", options.getBoolean("videoShareEnabled"));
    }

    if (options.hasKey("securityOptionsEnabled")) {
      builder.setFeatureFlag("security-options.enabled", options.getBoolean("securityOptionsEnabled"));
    }

    if (options.hasKey("lobbyModeEnabled")) {
      builder.setFeatureFlag("lobby-mode.enabled", options.getBoolean("lobbyModeEnabled"));
    }

    if (options.hasKey("chatEnabled")) {
      builder.setFeatureFlag("chat.enabled", options.getBoolean("chatEnabled"));
    }

    if (options.hasKey("screenSharingEnabled")) {
      builder.setFeatureFlag("android.screensharing.enabled", options.getBoolean("screenSharingEnabled"));
    }

    if (options.hasKey("speakerstatsEnabled")) {
      builder.setFeatureFlag("speakerstats.enabled", options.getBoolean("speakerstatsEnabled"));
    }

    if (options.hasKey("reactionsEnabled")) {
      builder.setFeatureFlag("reactions.enabled", options.getBoolean("reactions.enabled"));
    }

    builder.setFeatureFlag("pip.enabled", !options.hasKey("pipEnabled") || options.getBoolean("pipEnabled"));

    JitsiMeetActivityExtended.launchExtended(getReactApplicationContext(), builder.build());

    this.registerForBroadcastMessages(onConferenceTerminated);
  }

  @ReactMethod
  public void launchJitsiMeetView(ReadableMap options) {
    Callback noop = new Callback() {
      @Override
      public void invoke(Object... args) {

      }
    };

    launchJitsiMeetView(options, noop);
  }

  @ReactMethod
  public void launch(ReadableMap options, Callback onConferenceTerminated) {
    launchJitsiMeetView(options, onConferenceTerminated);
  }

  @ReactMethod
  public void launch(ReadableMap options) {
    launchJitsiMeetView(options);
  }

  private void registerForBroadcastMessages(Callback onConferenceTerminated) {
    onConferenceTerminatedReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        BroadcastEvent event = new BroadcastEvent(intent);

        onConferenceTerminated.invoke(event.getType().getAction());

        LocalBroadcastManager.getInstance(getReactApplicationContext()).unregisterReceiver(onConferenceTerminatedReceiver);
      }
    };

    IntentFilter intentFilter = new IntentFilter(BroadcastEvent.Type.CONFERENCE_TERMINATED.getAction());

    LocalBroadcastManager.getInstance(getReactApplicationContext()).registerReceiver(this.onConferenceTerminatedReceiver, intentFilter);
  }
}
