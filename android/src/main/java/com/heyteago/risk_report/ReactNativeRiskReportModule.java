package com.heyteago.risk_report;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import net.security.device.api.SecurityCode;
import net.security.device.api.SecurityDevice;
import net.security.device.api.SecurityInitListener;
import net.security.device.api.SecuritySession;

public class ReactNativeRiskReportModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String deviceToken;

    public ReactNativeRiskReportModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeRiskReport";
    }

    @ReactMethod
    private void getDeviceToken(String appKey, final Promise promise) {
        if (deviceToken == null) {
            final boolean[] isResolved = {false};
            // 设置10s超时
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    synchronized (ReactNativeRiskReportModule.class) {
                        if (deviceToken == null) {
                            isResolved[0] = true;
                            promise.resolve(null);
                        }
                    }
                }
            }, 10000);
            SecurityDevice.getInstance().init(this.reactContext, appKey, new SecurityInitListener() {
                @Override
                public void onInitFinish(int i) {
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (ReactNativeRiskReportModule.class) {
                                // 获取客户端Session，getSession接口一般在init接口调用之后3秒后调用。
                                SecuritySession securitySession = SecurityDevice.getInstance().getSession();
                                if (SecurityCode.SC_SUCCESS != securitySession.code) {
                                    Log.e("ReactNativeRiskReport", "getSession获取的结果是无效的.");
                                    if (!isResolved[0]) {
                                        promise.resolve(null);
                                    }
                                } else {
                                    deviceToken = securitySession.session;
                                    if (!isResolved[0]) {
                                        promise.resolve(deviceToken);
                                    }
                                    Log.i("ReactNativeRiskReport", "getSession成功，session: " + securitySession.session);
                                }
                            }
                        }
                    }, 3000);
                }
            });
        } else {
            promise.resolve(deviceToken);
        }

    }
}
