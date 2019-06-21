/*
 * This file is part of the SDWebImage package.
 * (c) Olivier Poitrey <rs@dailymotion.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

#import <Foundation/Foundation.h>
#import "SDWebImageCompat.h"

typedef NSData * _Nullable(^SDWebImageCacheSerializerBlock)(UIImage * _Nonnull image, NSData * _Nullable data, NSURL * _Nullable imageURL);

// This is the protocol for cache serializer. We can use a block to specify the cache serializer. But Using protocol can make this extensible, and allow Swift user to use it easily instead of using `@convention(block)` to store a block into context options.
// 转换需要缓存的图片格式, 通常用于需要缓存的图片格式与下载的图片格式不相符的时候
// 如：下载的时候为了节约流量、减少下载时间使用了WebP格式，但是如果缓存也用WebP，每次从缓存中取图片都需要经过一次解压缩，
// 这样是比较影响性能的，就可以使用id<SDWebImageCacheSerializer>，实现其中的协议方法：
@protocol SDWebImageCacheSerializer <NSObject>

- (nullable NSData *)cacheDataWithImage:(nonnull UIImage *)image originalData:(nullable NSData *)data imageURL:(nullable NSURL *)imageURL;

@end

@interface SDWebImageCacheSerializer : NSObject <SDWebImageCacheSerializer>

- (nonnull instancetype)initWithBlock:(nonnull SDWebImageCacheSerializerBlock)block;
+ (nonnull instancetype)cacheSerializerWithBlock:(nonnull SDWebImageCacheSerializerBlock)block;

@end
