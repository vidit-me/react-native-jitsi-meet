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
  subject?: string;
  audioOnly?: boolean;
  audioMuted?: boolean;
  videoMuted?: boolean;
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

export type ParticipantId = string;

export interface BroadcastActions {
  SET_AUDIO_MUTED?: {
    muted: boolean;
  };
  SET_VIDEO_MUTED?: {
    muted: boolean;
  };
  HANG_UP?: null;
  SEND_ENDPOINT_TEXT_MESSAGE?: {
    to?: ParticipantId;
    message: string;
  };
  TOGGLE_SCREEN_SHARE?: {
    enabled: boolean;
  };
  RETRIEVE_PARTICIPANTS_INFO?: {
    requestId: string;
  };
  OPEN_CHAT?: {
    to?: ParticipantId;
  };
  CLOSE_CHAT?: null;
  SEND_CHAT_MESSAGE?: {
    to?: ParticipantId;
    message: string;
  };
}
export interface JitsiMeetType {
  launchJitsiMeetView: (options: JitsiMeetConferenceOptions) => Promise<void>;
  sendActions: (actions: BroadcastActions) => void;
  // Deprecated
  hangUp: () => void;
}

export type JitsiMeetViewType = ComponentType<JitsiMeetViewProps>;
