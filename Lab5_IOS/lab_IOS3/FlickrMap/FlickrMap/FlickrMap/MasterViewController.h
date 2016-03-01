//
//  MasterViewController.h
//  FlickrMap
//
//  Created by pacellig on 24/11/15.
//  Copyright © 2015 eurecom. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MapKit/MapKit.h>
#import "KMLParser.h"

@class DetailViewController;

@interface MasterViewController : UIViewController

@property (strong, nonatomic) DetailViewController *detailViewController;
@property (nonatomic,retain) IBOutlet MKMapView *map;
@property (nonatomic,retain) KMLParser *kml;
@property (nonatomic,retain) DetailViewController *detail;
@property (nonatomic,retain) NSMutableArray *images;
@property (nonatomic,nonatomic) int index;
@end

