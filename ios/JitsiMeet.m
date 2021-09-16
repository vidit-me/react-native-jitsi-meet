#import "React/RCTBridgeModule.h"

@interface RCT_EXTERN_MODULE(JitsiMeet, NSObject)
RCT_EXTERN_METHOD(
  launch:(NSString)room 
  withOptions:(NSDictionary)options
)
@end
