//
//  ViewController.swift
//  SDWebImageProgressiveJPEGDemo
//
//  Created by Bogdan Poplauschi on 25/10/2017.
//  Copyright © 2017 SDWebImage. All rights reserved.
//

import UIKit
import SDWebImage

class ViewController: UIViewController {
    @IBOutlet weak var imageView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        SDImageCodersManager.shared.addCoder(ProgressiveJPEGDecoder.shared)
        // Limit progress block callback frequency
        SDWebImageDownloader.shared.config.minimumProgressInterval = 0.1
        SDImageCache.shared.clearDisk {
            // http://www.pooyak.com/p/progjpeg/jpegload.cgi?o=1
            // this one looks better but has a problem, doesn't return Content-Length
            // so a check in SDWebImageDownloaderOperation will stop the progressive handling (expectedSize)
            let url = URL(string: "https://raw.githubusercontent.com/cooperka/test-progressive-jpeg/master/test-face-progressive-optimized.jpg")
            // {10650, 13426} pixel size
            self.imageView.sd_setImage(with: url, placeholderImage: nil, options: [.progressiveLoad, .avoidDecodeImage]) { (image, error, cacheType, url) in
                if let image=image, let error=error, let url=url {
                    print(image, error, cacheType, url)
                }
            }
        }
    }
}
