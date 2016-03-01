//
//  MasterViewController.m
//  FlickrMap
//
//  Created by pacellig on 24/11/15.
//  Copyright © 2015 eurecom. All rights reserved.
//

#import "MasterViewController.h"
#import "DetailViewController.h"

@interface MasterViewController ()

@property NSMutableArray *objects;
@end

@implementation MasterViewController

@synthesize map;
@synthesize kml;
@synthesize detail;
@synthesize images;
@synthesize index;

- (void)viewDidLoad {
    [super viewDidLoad];
    self.images = [NSMutableArray array];
    // Do any additional setup after loading the view, typically from a nib.
    // self.navigationItem.leftBarButtonItem = self.editButtonItem;

    NSURL *url = [NSURL URLWithString: @"https://www.flickr.com/services/feeds/geo/it?format=kml&page=1"];
    self.kml = [KMLParser parseKMLAtURL:url];
    NSArray *annotations = [kml points];
    
    [map addAnnotations:annotations];
    map.visibleMapRect = [kml pointsRect];
}

- (void)viewWillAppear:(BOOL)animated {
    //self.clearsSelectionOnViewWillAppear = self.splitViewController.isCollapsed;
    [super viewWillAppear:animated];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)insertNewObject:(id)sender {
    if (!self.objects) {
        self.objects = [[NSMutableArray alloc] init];
    }
    [self.objects insertObject:[NSDate date] atIndex:0];
    NSIndexPath *indexPath = [NSIndexPath indexPathForRow:0 inSection:0];
    //[self.tableView insertRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationAutomatic];
}
#pragma mark MKMapViewDelegate
- (MKAnnotationView*)mapView:(MKMapView*)mapView viewForAnnotation:(id<MKAnnotation>)annotation{
    MKAnnotationView *pin = [kml viewForAnnotation:annotation];
    UIButton *rightButton = [UIButton buttonWithType:UIButtonTypeDetailDisclosure];
    [rightButton addTarget:self action:@selector(showDetails:) forControlEvents:UIControlEventTouchUpInside];
    rightButton.tag = [images count];
    NSURL *url = [kml imageURLForAnnotation:annotation];
    [self.images addObject:url];
    pin.rightCalloutAccessoryView = rightButton;
    return pin;
    //NSLog(@"View for annotations");
    //return [kml viewForAnnotation:annotation];
}

- (void)showDetails:(id)sender{
    UIButton *button=(UIButton *)sender;
    NSURL *url = (NSURL*)[images objectAtIndex:button.tag];
    index = (int)button.tag;
    UIStoryboard *storyboard = self.storyboard;
    
    detail = [storyboard instantiateViewControllerWithIdentifier:@"DetailViewController"];
    detail.imagesArray = url;
    detail.parentView = self;
    [self.navigationController pushViewController:detail animated:YES];
    
    
        
}
#pragma mark - Segues
/*
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"showDetail"]) {
      //  NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
     //   NSDate *object = self.objects[indexPath.row];
        DetailViewController *controller = (DetailViewController *)[[segue destinationViewController] topViewController];
        [controller setDetailItem:object];
        controller.navigationItem.leftBarButtonItem = self.splitViewController.displayModeButtonItem;
        controller.navigationItem.leftItemsSupplementBackButton = YES;
    }
}*/

#pragma mark - Table View
/*
- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.objects.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];

    NSDate *object = self.objects[indexPath.row];
    cell.textLabel.text = [object description];
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [self.objects removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}
*/
@end
