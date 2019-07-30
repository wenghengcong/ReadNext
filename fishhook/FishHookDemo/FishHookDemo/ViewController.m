//
//  ViewController.m
//  FishHookDemo
//
//  Created by Hunt on 2019/6/24.
//  Copyright © 2019 WengHengcong. All rights reserved.
//

#import "ViewController.h"
#import "fishhook.h"

@interface ViewController ()

@end


/**
 fishhook:只能hook系统函数，而不能hook自己编写的函数
 */
@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    //定义rebinding 结构体
    struct rebinding rebind = {};
    rebind.name = "NSLog";
    rebind.replacement = hookNSLog;
    rebind.replaced = (void *)&nslogMethod;
    
    //将上面的结构体 放入 reb结构体数组中
    struct rebinding red[]  = {rebind};
    
    /*
     * arg1 : 结构体数据组
     * arg2 : 数组的长度
     */
    
    rebind_symbols(red, 1);
}

//定义一个函数指针 用于指向原来的NSLog函数
static void (*nslogMethod)(NSString *format, ...);

//替换的函数
void hookNSLog(NSString *format, ...){
    format = [format stringByAppendingString:@"被勾住了"];
    nslogMethod(format);
}

- (void)touchesBegan:(NSSet<UITouch *> *)touches withEvent:(UIEvent *)event{
    NSLog(@"原有NSLog函数");
}


@end
