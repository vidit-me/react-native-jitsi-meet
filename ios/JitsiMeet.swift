import UIKit
import JitsiMeetSDK

@objc(JitsiMeet)
class JitsiMeet: NSObject {
    @objc func launchJitsiMeetView(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    DispatchQueue.main.async {
      let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
      let vc = JitsiMeetViewController()
      
      vc.resolver = resolve
      vc.modalPresentationStyle = .fullScreen
      vc.conferenceOptions = JitsiMeetUtil.buildConferenceOptions(options)
                
      rootViewController.pushViewController(vc, animated: false)
    }
  }

  @objc func launch(_ options: NSDictionary, resolver resolve: @escaping RCTPromiseResolveBlock, rejecter reject: RCTPromiseRejectBlock) {
    launchJitsiMeetView(options, resolver: resolve, rejecter: reject)
  }
  
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
