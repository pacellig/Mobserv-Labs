//
//  DetailViewController.m
//  FlickrMap
//
//  Created by pacellig on 24/11/15.
//  Copyright © 2015 eurecom. All rights reserved.
//

#import "DetailViewController.h"
#import "MasterViewController.h"

@interface DetailViewController<UIScrollViewDelegate, UIGestureRecognizerDelegate> ()

@end

@implementation DetailViewController

@synthesize image;
@synthesize img;
@synthesize scrollview;
@synthesize imagesArray;
@synthesize parentView;

#pragma mark - Managing the detail item

- (void)setDetailItem:(id)newDetailItem {
    if (_detailItem != newDetailItem) {
        _detailItem = newDetailItem;
            
        // Update the view
        [self configureView];
    }
}

- (void)configureView {
    // Update the user interface for the detail item.
    /*if (self.detailItem) {
        self.detailDescriptionLabel.text = [self.detailItem description];
    }*/
    
    UIActivityIndicatorView  *av = [[UIActivityIndicatorView alloc]initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
    av.frame = CGRectMake(145, 160, 25, 25);
    av.tag = 1;
    [self.view addSubview:av];
    [av startAnimating];
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSError* error = nil;
        NSData * data = [NSData dataWithContentsOfURL:imagesArray options:NSDataReadingUncached error:&error];
        
        if (error)
            return;
        dispatch_async(dispatch_get_main_queue(), ^{
            self.image.image = [UIImage imageWithData:data];
            [self.image	setContentMode:UIViewContentModeScaleAspectFit];
            
            // try to make the pinch to zoom effect
            [self.scrollview addSubview:image];
            self.scrollview.contentSize=self.image.image.size;
            self.scrollview.zoomScale=1.0;
            self.scrollview.minimumZoomScale=1.0;
            self.scrollview.maximumZoomScale=3.0;
            
            //[av removeFromSuperview];
            [av stopAnimating];
        });
    });
    
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    [self configureView];
    //self.image.image = img;
    
    // try to recognize swipes for switching images
    UISwipeGestureRecognizer *swipeLeft = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleLeftSwipe)];
    UISwipeGestureRecognizer *swipeRight = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleRightSwipe)];
    
    // Setting the swipe direction.
    [swipeLeft setDirection:UISwipeGestureRecognizerDirectionLeft];
    [swipeRight setDirection:UISwipeGestureRecognizerDirectionRight];
    // Adding the swipe gesture on image view
    [self.view addGestureRecognizer:swipeLeft];
    [self.view addGestureRecognizer:swipeRight];
    

}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(UIView *) viewForZoomingInScrollView:(UIScrollView *)scrollView
{
    return image;
}
-(void) scrollViewDidZoom:(UIScrollView*)scollview{
    if ( self.scrollview.zoomScale==1.0){
        scrollview.scrollEnabled = false;
    } else {
        scrollview.scrollEnabled = true;
    }
}
- (void)handleLeftSwipe
{
    NSLog(@"Left Swipe");
        NSMutableArray *images = parentView.images;
        if(parentView.index >0){
            imagesArray = [images objectAtIndex:parentView.index-1];
            parentView.index--;
            [self configureView];
    }
    
}

- (void)handleRightSwipe
{
    NSLog(@"Right Swipe");
    NSMutableArray *images = parentView.images;
    if(parentView.index < images.count-1){
        imagesArray = [images objectAtIndex:parentView.index+1];
        parentView.index++;
        [self configureView];
    }
}

@end
