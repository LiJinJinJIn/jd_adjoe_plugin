import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class JdAdjoePlugin {
  static const MethodChannel _channel = const MethodChannel('jd_adjoe_plugin');

  ///初始化api
  ///其中sdkHash 设置app唯一哈希值， 为必须选项且必须适配
  static Future<void> init(
      {@required String sdkHash, @required String userId, onInitialisationFinished: Function, onInitialisationError: Function}) async {
    _channel.setMethodCallHandler((MethodCall methodCall) {
      switch (methodCall.method) {
        case 'init':
          if (methodCall.arguments == "onInitialisationFinished") {
            if (onInitialisationFinished != null) onInitialisationFinished();
          } else {
            if (onInitialisationError != null) onInitialisationError();
          }
          break;
        default:
      }
      return;
    });

    _channel.invokeMethod('init', <String, dynamic>{'sdkHash': sdkHash, 'userId': userId});
  }

  ///设置 OfferWall 监听
  static Future<void> setOfferWallListener({onOfferWallOpened: Function, onOfferWallClosed: Function}) async {
    _channel.setMethodCallHandler((MethodCall methodCall) {
      switch (methodCall.method) {
        case 'setOfferWallListener':
          if (methodCall.arguments == "onOfferWallOpened") {
            if (onOfferWallOpened != null) onOfferWallOpened();
          } else if (methodCall.arguments == "onOfferWallClosed") {
            if (onOfferWallClosed != null) onOfferWallClosed();
          } else {}
          break;
        default:
      }
      return;
    });

    _channel.invokeMethod('setOfferWallListener');
  }

  ///展示OfferWall
  static Future<void> showOfferWall({exceptionListener: Function}) async {
    _channel.setMethodCallHandler((MethodCall methodCall) {
      switch (methodCall.method) {
        case 'showOfferWall':
          if (methodCall.arguments == "exception") {
            if (exceptionListener != null) exceptionListener();
          } else {}
          break;
        default:
      }
      return;
    });

    _channel.invokeMethod('showOfferWall');
  }

  ///检测是否初始化成功
  static Future<bool> isInitialized() async {
    return await _channel.invokeMethod('isInitialized');
  }
}
