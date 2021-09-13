interface JitsiMeetUserInfo {
  displayName?: string;
  email?: string;
  avatar?: string;
}

interface JitsiMeetConferenceOptions {
  serverUrl?: string;
  userInfo?: JitsiMeetUserInfo;
  token?: string;
  audioMuted?: boolean;
  videoMuted?: boolean;
  audioOnly?: boolean;
  screenSharingEnabled?: boolean;
  conferenceTimerEnabled?: boolean;
  closeCaptionsEnabled?: boolean;
  addPeopleEnabled?: boolean;
  calendarEnabled?: boolean;
  inviteEnabled?: boolean;
  meetingPasswordEnabled?: boolean;
  recordingEnabled?: boolean;
  liveStreamingEnabled?: boolean;
  raiseHandEnabled?: boolean;
  serverUrlChangeEnabled?: boolean;
  videoShareEnabled?: boolean;
  securityOptionsEnabled?: boolean;
  chatEnabled?: boolean;
  lobbyModeEnabled?: boolean;
  pipEnabled?: boolean;
}

export interface JitsiMeetInterface {
  launchJitsiMeetView(room: string, options: JitsiMeetConferenceOptions): void;
}
