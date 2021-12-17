package com.reactnativejitsimeet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.jitsi.meet.sdk.BroadcastEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class JitsiMeetViewManager extends SimpleViewManager<RNJitsiMeetView> {
  public static final String REACT_CLASS = "JitsiMeetView";

  private final ReactApplicationContext reactApplicationContext;
  private RNJitsiMeetView jitsiMeetView;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
      onBroadcastReceived(intent);
    }
  };

  public JitsiMeetViewManager(ReactApplicationContext reactApplicationContext) {
    this.reactApplicationContext = reactApplicationContext;
  }

  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @NonNull
  @Override
  protected RNJitsiMeetView createViewInstance(@NonNull ThemedReactContext reactContext) {
    jitsiMeetView = new RNJitsiMeetView(reactContext.getCurrentActivity());

    registerForBroadcastMessages();

    return jitsiMeetView;
  }

  @Override
  public void onDropViewInstance(@NonNull RNJitsiMeetView view) {
    LocalBroadcastManager.getInstance(jitsiMeetView.getContext()).unregisterReceiver(broadcastReceiver);

    jitsiMeetView.leave();
    jitsiMeetView.dispose();
  }

  @ReactProp(name = "options")
  public void setOptions(RNJitsiMeetView view, ReadableMap options) {
    RNJitsiMeetConferenceOptions.Builder builder = new RNJitsiMeetConferenceOptions.Builder();

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
        RNJitsiMeetUserInfo userInfo = new RNJitsiMeetUserInfo();

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

    builder.setFeatureFlag("pip.enabled", !options.hasKey("pipEnabled") || options.getBoolean("pipEnabled"));

    RNJitsiMeetConferenceOptions jitsiMeetConferenceOptions = builder.build();

    jitsiMeetView.join(jitsiMeetConferenceOptions);
  }

  private void registerForBroadcastMessages() {
    IntentFilter intentFilter = new IntentFilter();

    for (BroadcastEvent.Type type : BroadcastEvent.Type.values()) {
      intentFilter.addAction(type.getAction());
    }

    LocalBroadcastManager.getInstance(jitsiMeetView.getContext()).registerReceiver(broadcastReceiver, intentFilter);
  }

  private void onBroadcastReceived(Intent intent) {
    if (intent != null) {

      BroadcastEvent event = new BroadcastEvent(intent);
      WritableMap eventMap = Arguments.createMap();

      switch (event.getType()) {
        case CONFERENCE_JOINED:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetView.getId(),
            "onConferenceJoined",
            eventMap);
          break;
        case CONFERENCE_TERMINATED:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetView.getId(),
            "onConferenceTerminated",
            eventMap);
          break;
        case CONFERENCE_WILL_JOIN:
          eventMap.putString("url", (String) event.getData().get("url"));
          eventMap.putString("error", (String) event.getData().get("error"));

          reactApplicationContext.getJSModule(RCTEventEmitter.class).receiveEvent(
            jitsiMeetView.getId(),
            "onConferenceWillJoin",
            eventMap);
          break;
      }
    }
  }

  @Nullable
  @Override
  public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
    MapBuilder.Builder<String, Object> builder = MapBuilder.builder();
    builder.put("onConferenceJoined", MapBuilder.of("registrationName", "onConferenceJoined"));
    builder.put("onConferenceTerminated", MapBuilder.of("registrationName", "onConferenceTerminated"));
    builder.put("onConferenceWillJoin", MapBuilder.of("registrationName", "onConferenceWillJoin"));
    return builder.build();
  }
}
