//
//  DoraemonSubThreadUICheckPlugin.h
//  DoraemonKit-DoraemonKit
//
//  Created by yixiang on 2018/9/12.
//

#import <Foundation/Foundation.h>
#import "DoraemonPluginProtocol.h"


/// 通过 hook setNeedLayout 等方法，检测调用上述方法是否在 main thread
@interface DoraemonSubThreadUICheckPlugin : NSObject<DoraemonPluginProtocol>

@end
