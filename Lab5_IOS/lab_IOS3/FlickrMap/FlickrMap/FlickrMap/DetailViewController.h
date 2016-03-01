//
//  DetailViewController.h
//  FlickrMap
//
//  Created by pacellig on 24/11/15.
//  Copyright © 2015 eurecom. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MasterViewController;
@interface DetailViewController<UIScrollViewDelegate, UIGestureRecognizerDelegate>: UIViewController


@property (strong, nonatomic) id detailItem;
@property (weak, nonatomic) IBOutlet UILabel *detailDescriptionLabel;
@property (nonatomic,retain) IBOutlet UIImageView *image;
@property (nonatomic,retain) UIImage *img;
@property (nonatomic,retain) IBOutlet UIScrollView *scrollview;
@property (nonatomic,retain) NSURL *imagesArray;
@property (weak,nonatomic) MasterViewController *parentView;

@end

