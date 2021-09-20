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
  launch: (options: JitsiMeetConferenceOptions) => void;
}
