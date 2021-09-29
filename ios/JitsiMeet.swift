import UIKit
import JitsiMeetSDK

@objc(JitsiMeet)
class JitsiMeet: NSObject {
  @objc func launch(_ options: NSDictionary) {
    DispatchQueue.main.async {
      let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
      let vc = JitsiMeetViewController()
      
      vc.modalPresentationStyle = .fullScreen
      vc.conferenceOptions = JitsiMeetUtil.buildConferenceOptions(options)
                
      rootViewController.pushViewController(vc, animated: false)
    }
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
