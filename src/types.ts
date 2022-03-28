import type { ComponentType } from 'react';
import type { StyleProp, ViewStyle } from 'react-native';

export interface JitsiMeetUserInfo {
  displayName?: string;
  email?: string;
  avatar?: string;
}

export interface JitsiMeetConferenceOptions {
  room: string;
  serverUrl?: string;
  userInfo?: JitsiMeetUserInfo;
  token?: string;
  featureFlags?: { [key: string]: boolean };
}

interface JitsiMeetEvent {
  nativeEvent: {
    error?: string;
    url?: string;
  };
}

export interface JitsiMeetViewProps {
  options: JitsiMeetConferenceOptions;
  style?: StyleProp<ViewStyle>;
  onConferenceTerminated?: (e: JitsiMeetEvent) => void;
  onConferenceJoined?: (e: JitsiMeetEvent) => void;
  onConferenceWillJoin?: (e: JitsiMeetEvent) => void;
}

export interface JitsiMeetType {
  launchJitsiMeetView: (options: JitsiMeetConferenceOptions) => Promise<void>;
  hangUp: () => void;
}

export type JitsiMeetViewType = ComponentType<JitsiMeetViewProps>;
