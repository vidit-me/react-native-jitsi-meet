#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(JitsiMeet, NSObject)
RCT_EXTERN_METHOD(launchJitsiMeetView:(NSDictionary)options)
RCT_EXTERN_METHOD(launch:(NSDictionary)options)
@end
