# @heytea/react-native-risk-report

## Getting started

`$ npm install @heytea/react-native-risk-report --save`

### Mostly automatic installation

`$ react-native link @heytea/react-native-risk-report`

## Usage
```javascript
import ReactNativeRiskReport from '@heytea/react-native-risk-report';

// 获取 deviceToken
ReactNativeRiskReport.getDeviceToken(appKey: string): Promise<string | null>;
```
