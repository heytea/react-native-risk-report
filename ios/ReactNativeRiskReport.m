#import "ReactNativeRiskReport.h"
#import <React/RCTBridge.h>

@implementation ReactNativeRiskReport

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(getDeviceToken:(NSString *)appKey
                :(RCTPromiseResolveBlock)resolve
                :(RCTPromiseRejectBlock)reject)
{
    resolve([NSNull null]);
}

@end
