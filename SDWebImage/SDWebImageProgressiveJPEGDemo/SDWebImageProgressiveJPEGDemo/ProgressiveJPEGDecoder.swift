//
//  ProgressiveJPEGDecoder.swift
//  SDWebImageProgressiveJPEGDemo
//
//  Created by Bogdan Poplauschi on 25/10/2017.
//  Copyright © 2017 SDWebImage. All rights reserved.
//

import UIKit
import SDWebImage
import Concorde

class ProgressiveJPEGDecoder: NSObject, SDProgressiveImageCoder {
    static let shared = ProgressiveJPEGDecoder()
    
    var data: Data?
    var finished: Bool = false
    
    func canIncrementalDecode(from data: Data?) -> Bool {
        return true
    }
    
    required init(incrementalWithOptions options: [SDImageCoderOption : Any]? = nil) {
        super.init()
    }
    
    func updateIncrementalData(_ data: Data?, finished: Bool) {
        if self.finished {
            return
        }
        self.data = data
        self.finished = finished
    }
    
    func incrementalDecodedImage(options: [SDImageCoderOption : Any]? = nil) -> UIImage? {
        if let data = data {
            if let bufferedImageDecoder = CCBufferedImageDecoder(data: data) {
                bufferedImageDecoder.decompress()
                return bufferedImageDecoder.toImage()
            }
        }
        return nil
    }
    
    func canDecode(from data: Data?) -> Bool {
        return true
    }
    
    func decodedImage(with data: Data?, options: [SDImageCoderOption : Any]? = nil) -> UIImage? {
        if let data = data {
            if let imageDecoder = CCBufferedImageDecoder(data: data) {
                imageDecoder.decompress()
                return imageDecoder.toImage()
            }
        }
        return nil
    }
    
    func canEncode(to format: SDImageFormat) -> Bool {
        return false
    }
    
    func encodedData(with image: UIImage?, format: SDImageFormat, options: [SDImageCoderOption : Any]? = nil) -> Data? {
        return nil
    }
}
