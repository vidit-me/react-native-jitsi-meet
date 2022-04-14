import Foundation
import JitsiMeetSDK

struct JitsiMeetUtil {
  static func buildConferenceOptions(_ options: NSDictionary) -> JitsiMeetConferenceOptions {
    return JitsiMeetConferenceOptions.fromBuilder { (builder) in
      guard let room = options["room"] as? String else {
        fatalError("Room must not be empty")
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

      // Set built-in config overrides
      if let subject = options["subject"] as? String {
        builder.subject = subject
      }

      if let audioOnly = options["audioOnly"] as? Bool {
        builder.audioOnly = audioOnly
      }

      if let audioMuted = options["audioMuted"] as? Bool {
        builder.audioMuted = audioMuted
      }

      if let videoMuted = options["videoMuted"] as? Bool {
        builder.videoMuted = videoMuted
      }
        
      // Set the feature flags
      let featureFlags = options.value(forKey: "featureFlags") as! NSDictionary
      for (flag, value) in featureFlags {
        builder.setFeatureFlag(flag as! String, withValue: value)
      }
    }
  }
}
