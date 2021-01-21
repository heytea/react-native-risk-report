#import "ReactNativeRiskReport.h"
#import <React/RCTBridge.h>
#import <deviceiOS/SecuritySession.h>
#import <deviceiOS/SecurityDevice.h>

@implementation ReactNativeRiskReport

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(getDeviceToken:(NSString *)appKey
                :(RCTPromiseResolveBlock)resolve
                :(RCTPromiseRejectBlock)reject)
{
    
    SecurityDevice *device = [[SecurityDevice alloc] init];
    [device initDevice:appKey :^(int code)  {
      if (10000 == code) {
          SecuritySession *session = [device getSession];
          int code = session.code;
          NSString *token = session.session;
          if (10000 == code) {
            // 成功
            resolve(token);
          }else{
              resolve(@"");
          }
      }else{
        resolve(@"");
      }
    }];
}


@end
