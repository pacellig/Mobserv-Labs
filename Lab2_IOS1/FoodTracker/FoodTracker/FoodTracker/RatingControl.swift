//
//  RatingControl.swift
//  FoodTracker
//
//  Created by pacelli giuseppe, giovanni tataranni on 11/2/15.
//  Copyright © 2015 pacellig. All rights reserved.
//

import UIKit

class RatingControl: UIView {
    // MARK: properties
    var rating = 0 {
        didSet{
            setNeedsLayout()
        }
    }
    var ratingButtons = [UIButton]()
    var spacing = 5
    var stars = 5

        // MARK: initialization
    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        let filledStarImage = UIImage(named: "filledStar")
        let emptyStarImage = UIImage(named: "emptyStar")
        
        for _ in 0..<5 {
            let button = UIButton()
            
            button.setImage(emptyStarImage, forState: .Normal)
            button.setImage(filledStarImage, forState: .Selected)
            button.setImage(filledStarImage, forState: [.Highlighted,.Selected])
            
            button.adjustsImageWhenHighlighted = false
            
        //button.backgroundColor = UIColor.redColor()
        
            button.addTarget(self, action: "ratingButtonTapped", forControlEvents: .TouchDown)
        ratingButtons += [button]
        addSubview(button)
        }
    }
    
    override func layoutSubviews(){
        // set buttons weight and height
        let buttonSize = Int(frame.size.height)
    var buttonFrame = CGRect(x: 0, y: 0, width: buttonSize, height: buttonSize)
        
        //offset each button 
        for (index,button) in ratingButtons.enumerate(){
        buttonFrame.origin.x = CGFloat(index*(buttonSize+spacing))
            button.frame = buttonFrame
        }
        updateButtonSelectionStates()
    }
    
    override func intrinsicContentSize() -> CGSize {
        let buttonSize = Int(frame.size.height)
        let width = (buttonSize+spacing)*stars
        return CGSize(width: width, height: buttonSize)
    }
    
    // MARK: button Action
    func ratingButtonTapped(button: UIButton){
        rating = ratingButtons.indexOf(button)! + 1
        updateButtonSelectionStates()
    }
    
    func updateButtonSelectionStates(){
        for(index, button) in ratingButtons.enumerate() {
            button.select = index < rating
        }
    }
}
