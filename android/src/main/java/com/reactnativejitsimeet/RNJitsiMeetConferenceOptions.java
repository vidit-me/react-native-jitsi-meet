package com.reactnativejitsimeet;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class RNJitsiMeetConferenceOptions implements Parcelable {
    private URL serverURL;
    private String room;
    private String token;
    private Bundle colorScheme;
    private Bundle config;
    private Bundle featureFlags;
    private RNJitsiMeetUserInfo userInfo;

    public URL getServerURL() {
        return serverURL;
    }

    public String getRoom() {
        return room;
    }

    public String getToken() {
        return token;
    }

    public Bundle getColorScheme() {
        return colorScheme;
    }

    public Bundle getFeatureFlags() {
        return featureFlags;
    }

    public RNJitsiMeetUserInfo getUserInfo() {
        return userInfo;
    }

    public static class Builder {
        private URL serverURL;
        private String room;
        private String token;

        private Bundle colorScheme;
        private Bundle config;
        private Bundle featureFlags;

        private RNJitsiMeetUserInfo userInfo;

        public Builder() {
            config = new Bundle();
            featureFlags = new Bundle();
        }

        public Builder setServerURL(URL url) {
            this.serverURL = url;

            return this;
        }

        public Builder setRoom(String room) {
            this.room = room;

            return this;
        }

        public Builder setSubject(String subject) {
            setConfigOverride("subject", subject);

            return this;
        }

        public Builder setToken(String token) {
            this.token = token;

            return this;
        }

        public Builder setColorScheme(Bundle colorScheme) {
            this.colorScheme = colorScheme;

            return this;
        }

        public Builder setAudioMuted(boolean audioMuted) {
            setConfigOverride("startWithAudioMuted", audioMuted);

            return this;
        }

        public Builder setAudioOnly(boolean audioOnly) {
            setConfigOverride("startAudioOnly", audioOnly);

            return this;
        }

        public Builder setVideoMuted(boolean videoMuted) {
            setConfigOverride("startWithVideoMuted", videoMuted);

            return this;
        }

        public Builder setWelcomePageEnabled(boolean enabled) {
            this.featureFlags.putBoolean("welcomepage.enabled", enabled);

            return this;
        }

        public Builder setFeatureFlag(String flag, boolean value) {
            this.featureFlags.putBoolean(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, String value) {
            this.featureFlags.putString(flag, value);

            return this;
        }

        public Builder setFeatureFlag(String flag, int value) {
            this.featureFlags.putInt(flag, value);

            return this;
        }

        public Builder setUserInfo(RNJitsiMeetUserInfo userInfo) {
            this.userInfo = userInfo;

            return this;
        }

        public Builder setConfigOverride(String config, String value) {
            this.config.putString(config, value);

            return this;
        }

        public Builder setConfigOverride(String config, int value) {
            this.config.putInt(config, value);

            return this;
        }

        public Builder setConfigOverride(String config, boolean value) {
            this.config.putBoolean(config, value);

            return this;
        }

        public Builder setConfigOverride(String config, Bundle bundle) {
            this.config.putBundle(config, bundle);

            return this;
        }

        public Builder setConfigOverride(String config, String[] list) {
            this.config.putStringArray(config, list);

            return this;
        }

        public RNJitsiMeetConferenceOptions build() {
            RNJitsiMeetConferenceOptions options = new RNJitsiMeetConferenceOptions();

            options.serverURL = this.serverURL;
            options.room = this.room;
            options.token = this.token;
            options.colorScheme = this.colorScheme;
            options.config = this.config;
            options.featureFlags = this.featureFlags;
            options.userInfo = this.userInfo;

            return options;
        }
    }

    private RNJitsiMeetConferenceOptions() {
    }

    private RNJitsiMeetConferenceOptions(Parcel in) {
        serverURL = (URL) in.readSerializable();
        room = in.readString();
        token = in.readString();
        colorScheme = in.readBundle();
        config = in.readBundle();
        featureFlags = in.readBundle();
        userInfo = new RNJitsiMeetUserInfo(in.readBundle());
    }

    Bundle asProps() {
        Bundle props = new Bundle();

        // Android always has the PiP flag set by default.
        if (!featureFlags.containsKey("pip.enabled")) {
            featureFlags.putBoolean("pip.enabled", true);
        }

        props.putBundle("flags", featureFlags);

        if (colorScheme != null) {
            props.putBundle("colorScheme", colorScheme);
        }

        Bundle urlProps = new Bundle();

        // The room is fully qualified
        if (room != null && room.contains("://")) {
            urlProps.putString("url", room);
        } else {
            if (serverURL != null) {
                urlProps.putString("serverURL", serverURL.toString());
            }
            if (room != null) {
                urlProps.putString("room", room);
            }
        }

        if (token != null) {
            urlProps.putString("jwt", token);
        }

        if (userInfo != null) {
            props.putBundle("userInfo", userInfo.asBundle());
        }

        urlProps.putBundle("config", config);
        props.putBundle("url", urlProps);

        return props;
    }

    public static final Creator<RNJitsiMeetConferenceOptions> CREATOR = new Creator<RNJitsiMeetConferenceOptions>() {
        @Override
        public RNJitsiMeetConferenceOptions createFromParcel(Parcel in) {
            return new RNJitsiMeetConferenceOptions(in);
        }

        @Override
        public RNJitsiMeetConferenceOptions[] newArray(int size) {
            return new RNJitsiMeetConferenceOptions[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(serverURL);
        dest.writeString(room);
        dest.writeString(token);
        dest.writeBundle(colorScheme);
        dest.writeBundle(config);
        dest.writeBundle(featureFlags);
        dest.writeBundle(userInfo != null ? userInfo.asBundle() : new Bundle());
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
