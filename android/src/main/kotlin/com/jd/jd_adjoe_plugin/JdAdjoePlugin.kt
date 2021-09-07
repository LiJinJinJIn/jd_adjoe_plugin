package com.jd.jd_adjoe_plugin

import android.app.Activity
import androidx.annotation.NonNull
import io.adjoe.sdk.*

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** JdAdjoePlugin */
class JdAdjoePlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    private lateinit var activity: Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "jd_adjoe_plugin")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "init" -> {
                val sdkHash = call.argument<String>("sdkHash") ?: ""
                val userId = call.argument<String>("userId") ?: ""
                val options = Adjoe.Options()
                        .setUserId(userId)

                Adjoe.init(activity, sdkHash, options, object : AdjoeInitialisationListener {

                    override fun onInitialisationFinished() {
                        channel.invokeMethod("init", "onInitialisationFinished")
                    }

                    override fun onInitialisationError(exception: Exception?) {
                        channel.invokeMethod("init", "onInitialisationError")
                    }
                })
            }

            "isInitialized" -> {
                result.success(Adjoe.isInitialized())
            }

            "showOfferWall" -> {
                try {
                    val adJoeOfferWallIntent = Adjoe.getOfferwallIntent(activity)
                    activity.startActivity(adJoeOfferWallIntent)
                } catch (notInitializedException: AdjoeNotInitializedException) {
                    channel.invokeMethod("showOfferWall", "exception")
                } catch (exception: AdjoeException) {
                    channel.invokeMethod("showOfferWall", "exception")
                }
            }

            "setOfferWallListener" -> {
                Adjoe.setOfferwallListener(object : AdjoeOfferwallListener {
                    override fun onOfferwallOpened(type: String?) {
                        channel.invokeMethod("setOfferWallListener", "onOfferWallOpened")
                    }

                    override fun onOfferwallClosed(type: String?) {
                        channel.invokeMethod("setOfferWallListener", "onOfferWallClosed")
                    }

                })
            }


            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onDetachedFromActivity() {
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }
}
