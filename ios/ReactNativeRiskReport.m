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
    [device initDevice:SECURITY_APPKEY :^(int code)  {
      if (10000 == code) {
          SecurityDevice *device = [[SecurityDevice alloc]init];
          SecuritySession *session = [device getSession];
          int code = session.code;
          NSString *token = session.session;
          if (10000 == code) {
            // 成功
            NSLog(@"device token =====  %@",token);
            resolve(token);
          }else{
              reject()
          }
      }else{
          reject();
      }
    }];
}


@end
