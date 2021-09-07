#import "JdAdjoePlugin.h"
#if __has_include(<jd_adjoe_plugin/jd_adjoe_plugin-Swift.h>)
#import <jd_adjoe_plugin/jd_adjoe_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "jd_adjoe_plugin-Swift.h"
#endif

@implementation JdAdjoePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftJdAdjoePlugin registerWithRegistrar:registrar];
}
@end
