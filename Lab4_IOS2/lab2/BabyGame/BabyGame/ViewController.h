//
//  ViewController.h
//  BabyGame
//
//  Created by tatarann on 11/3/15.
//  Copyright (c) 2015 eurecom. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <AudioToolbox/AudioToolbox.h>
#import <AVFoundation/AVFoundation.h>

@interface ViewController : UIViewController{
    // declaration of the IBOutlets
    IBOutlet UIImageView * slot;
    IBOutlet UIImageView * iRectangle;
    IBOutlet UIImageView * iSquare;
    IBOutlet UIImageView * iOval;
    IBOutlet UIImageView * iTriangle;
    IBOutlet UIImageView * iCircle;
    
    //    IBOutlet UIButton * button;
    IBOutlet UIButton* rectangle;
    IBOutlet UIButton* square;
    IBOutlet UIButton* oval;
    IBOutlet UIButton* triangle;
    IBOutlet UIButton* circle;
    
    CGPoint originalPosition[5];
    CGPoint initialPosition[5];
    UIImageView* finalPosition[5];
    bool isFinal[5];
    UIButton* buttons[5];
    
    
}

//properties for the members
@property(nonatomic, retain)UIImageView * slot;
@property(nonatomic, retain)UIImageView * iRectangle;
@property(nonatomic, retain)UIImageView * iSquare;
@property(nonatomic, retain)UIImageView * iOval;
@property(nonatomic, retain)UIImageView * iTriangle;
@property(nonatomic, retain)UIImageView * iCircle;
//@property(nonatomic, retain)UIButton * button;
@property(nonatomic, retain)UIButton * rectangle;
@property(nonatomic, retain)UIButton * square;
@property(nonatomic, retain)UIButton * oval;
@property(nonatomic, retain)UIButton * triangle;
@property(nonatomic, retain)UIButton * circle;


-(IBAction)buttonPressed:(id)sender;
-(IBAction)buttonMoved: (id)sender withEvent:(UIEvent *)event;
-(IBAction)buttonReleased:(id)sender;



@end

