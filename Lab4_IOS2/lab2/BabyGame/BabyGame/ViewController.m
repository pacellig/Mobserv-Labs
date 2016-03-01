//
//  ViewController.m
//  BabyGame
//
//  Created by tatarann on 11/3/15.
//  Copyright (c) 2015 eurecom. All rights reserved.
//

#import "ViewController.h"

@interface ViewController ()
@property(nonatomic, strong) AVAudioPlayer *backgroundMusic;
@end

@implementation ViewController
@synthesize rectangle, square, oval, triangle, circle, slot;
@synthesize iRectangle,iSquare, iOval, iTriangle, iCircle;

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view, typically from a nib.
    UIColor *background = [[UIColor alloc]initWithPatternImage:[UIImage imageNamed:@"GameBackground.png"]];
    self.view.backgroundColor = background;
    finalPosition[0] = iRectangle;
    finalPosition[1] = iSquare;
    finalPosition[2] = iOval;
    finalPosition[3] = iTriangle;
    finalPosition[4] = iCircle;
    buttons[0] = rectangle;
    buttons[1]=square;
    buttons[2]=oval;
    buttons[3]=triangle;
    buttons[4]=circle;
    int i;
    for(i=0; i<5; i++){
        isFinal[i]=false;
        initialPosition[i]=buttons[i].center;
        //NSLog(@"posizionefinale %d %f %f\n", i, finalPosition[i].x, finalPosition[i].y);
    }
    NSURL *musicFile = [[NSBundle mainBundle] URLForResource:@"coccodrillo"
                                               withExtension:@"mp3"];
    self.backgroundMusic = [[AVAudioPlayer alloc] initWithContentsOfURL:musicFile
                                                                  error:nil];
    self.backgroundMusic.numberOfLoops = -1;
    [self.backgroundMusic play];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(IBAction) buttonPressed:(id)sender{
    UIButton * button = (UIButton *)sender;
    
    originalPosition[button.tag] = button.center;
    [UIView animateWithDuration:0.5 delay:0 options:UIViewAnimationOptionCurveEaseOut animations:^{
        button.transform = CGAffineTransformMakeScale(1.3f, 1.3f);
    } completion:^(BOOL finished){
        button.transform = CGAffineTransformMakeScale(1.0f, 1.0f);
    }];
        //int i = button.tag;
    //NSLog(@"posizioneBottone %d %f %f\n", i, originalPosition[i].x, originalPosition[i].y);
}

-(IBAction)buttonMoved:(id)sender withEvent:(UIEvent *)event{
    UIButton * button = (UIButton *)sender;
    for (UITouch *  touch in [event allTouches]) {
        button.center = [touch locationInView:self.view];
    }
}


-(CGFloat)distanceBetweenPoint:(CGPoint)point1 andPoint:(CGPoint)point2 {
    // we can say this is private since we dont't put its prototype in the .h file
    CGFloat dx = point2.x - point1.x;
    CGFloat dy = point2.y - point1.y;
    return sqrt(dx*dx + dy*dy);
}

-(IBAction)buttonReleased:(id)sender{
    UIButton * button = (UIButton *)sender;
    if([self distanceBetweenPoint:button.center andPoint:finalPosition[button.tag].center]<100){
        button.center=finalPosition[button.tag].center;
        isFinal[button.tag]=true;
        int k=0, i;
        for(i=0; i<5; i++){
            if(isFinal[i]==true)
                k++;
        }
        if(k==5){
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Congratulations!"
                                                            message:@"You completed the game!"
                                                           delegate:self
                                                  cancelButtonTitle:@"Cancel"
                                                  otherButtonTitles:@"Restart", nil];
            [alert show];
        }
        
    }
    else{
        //;
        [UIView animateWithDuration:2.0 animations:^{
            button.frame = CGRectOffset(button.frame, originalPosition[button.tag].x-button.center.x, originalPosition[button.tag].y-button.center.y);
        }];
        button.center=originalPosition[button.tag];
    }
}

- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex {
    // the user clicked OK
    if (buttonIndex == 1) {
        int i;
        for(i=0; i<5; i++){
            isFinal[i]=false;
            buttons[i].center = initialPosition[i];
        }
    }
}

-(BOOL)canBecomeFirstResponder {
    return YES;
}

-(void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:NO];
    [self becomeFirstResponder];
}

-(void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:NO];
}

-(void)viewDidDisappear:(BOOL)animated {
    [self resignFirstResponder];
    [super viewDidDisappear:NO];
}

-(void)motionBegan:(UIEventSubtype)motion withEvent:(UIEvent *)event
{
    if (motion == UIEventSubtypeMotionShake )
    {
        // shaking has began.
    }
}


-(void)motionEnded:(UIEventSubtype)motion withEvent:(UIEvent *)event
{
    if (motion == UIEventSubtypeMotionShake )
    {
        // shaking has ended
        //NSLog(@"SHAKE ENDED");
        int i;
        for(i=0; i<5; i++){
            isFinal[i]=false;
            buttons[i].center = initialPosition[i];
        }
    }
}

@end
