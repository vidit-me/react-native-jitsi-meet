import Foundation
import JitsiMeetSDK

struct JitsiMeetUtil {
  static func buildConferenceOptions(_ options: NSDictionary) -> JitsiMeetConferenceOptions {
    return JitsiMeetConferenceOptions.fromBuilder { (builder) in
      guard let room = options["room"] as? String else {
        fatalError("Room must no be empty")
      }
      
      builder.room = room
      
      builder.serverURL = URL(string: (options["serverUrl"] as? String) ?? "https://meet.jit.si")
      
      if let userInfo = options["userInfo"] as? NSDictionary {
        let conferenceUserInfo = JitsiMeetUserInfo()
        
        if let displayName = userInfo["displayName"] as? String {
          conferenceUserInfo.displayName = displayName
        }
        
        if let email = userInfo["email"] as? String {
          conferenceUserInfo.email = email
        }
        
        if let avatar = userInfo["avatar"] as? String {
          conferenceUserInfo.avatar = URL(string: avatar)
        }
        
        builder.userInfo = conferenceUserInfo
      }
      
      if let token = options["token"] as? String {
        builder.token = token
      }
      
      if let screenSharingEnabled = options["screenSharingEnabled"] as? Bool {
        builder.setFeatureFlag("ios.screensharing.enabled", withBoolean: screenSharingEnabled)
      }
      
      if let conferenceTimerEnabled = options["conferenceTimerEnabled"] as? Bool {
        builder.setFeatureFlag("conference-timer.enabled", withBoolean: conferenceTimerEnabled)
      }
      
      if let closeCaptionsEnabled = options["closeCaptionsEnabled"] as? Bool {
        builder.setFeatureFlag("close-captions.enabled", withBoolean: closeCaptionsEnabled)
      }
      
      if let addPeopleEnabled = options["addPeopleEnabled"] as? Bool {
        builder.setFeatureFlag("add-people.enabled", withBoolean: addPeopleEnabled)
      }
      
      if let calendarEnabled = options["calendarEnabled"] as? Bool {
        builder.setFeatureFlag("calendar.enabled", withBoolean: calendarEnabled)
      }
      
      if let inviteEnabled = options["invite.enabled"] as? Bool {
        builder.setFeatureFlag("invite.enabled", withBoolean: inviteEnabled)
      }
      
      if let meetingPasswordEnabled = options["meetingPasswordEnabled"] as? Bool {
        builder.setFeatureFlag("meeting-password.enabled", withBoolean: meetingPasswordEnabled)
      }
      
      if let recordingEnabled = options["recordingEnabled"] as? Bool {
        builder.setFeatureFlag("ios.recording.enabled", withBoolean: recordingEnabled)
        builder.setFeatureFlag("recording.enabled", withBoolean: recordingEnabled)
      }
      
      if let liveStreamingEnabled = options["liveStreamingEnabled"] as? Bool {
        builder.setFeatureFlag("live-streaming.enabled", withBoolean: liveStreamingEnabled)
      }
      
      if let raiseHandEnabled = options["raiseHandEnabled"] as? Bool {
        builder.setFeatureFlag("raise-hand.enabled", withBoolean: raiseHandEnabled)
      }
      
      if let serverUrlChangeEnabled = options["serverUrlChangeEnabled"] as? Bool {
        builder.setFeatureFlag("server-url-change.enabled", withBoolean: serverUrlChangeEnabled)
      }
      
      if let videoShareEnabled = options["videoShareEnabled"] as? Bool {
        builder.setFeatureFlag("video-share.enabled", withBoolean: videoShareEnabled)
      }
      
      if let securityOptionsEnabled = options["securityOptionsEnabled"] as? Bool {
        builder.setFeatureFlag("security-options.enabled", withBoolean: securityOptionsEnabled)
      }
      
      if let chatEnabled = options["chatEnabled"] as? Bool {
        builder.setFeatureFlag("chat.enabled", withBoolean: chatEnabled)
      }
      
      if let lobbyModeEnabled = options["lobbyModeEnabled"] as? Bool {
        builder.setFeatureFlag("lobby-mode.enabled", withBoolean: lobbyModeEnabled)
      }
      
      builder.setFeatureFlag("pip.enabled", withValue: false)
    }
  }
}
