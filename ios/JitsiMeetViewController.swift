import UIKit
import JitsiMeetSDK

class JitsiMeetViewController: UIViewController {
  var conferenceOptions: JitsiMeetConferenceOptions?
  
  override func viewDidLoad() {
    let jitsiMeetView = JitsiMeetView()
    
    jitsiMeetView.join(conferenceOptions)
    jitsiMeetView.delegate = self
    
    view = jitsiMeetView
  }
}

extension JitsiMeetViewController: JitsiMeetViewDelegate {
  func ready(toClose data: [AnyHashable : Any]!) {
    DispatchQueue.main.async {
        let rootViewController = UIApplication.shared.delegate?.window??.rootViewController as! UINavigationController
        rootViewController.popViewController(animated: false)
    }
  }
}
