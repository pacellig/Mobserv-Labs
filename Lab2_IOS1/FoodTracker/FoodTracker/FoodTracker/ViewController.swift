//
//  ViewController.swift
//  FoodTracker
//
//  Created by pacelli giuseppe, tataranni giovanni on 11/1/15.
//  Copyright © 2015 pacellig. All rights reserved.
//

import UIKit

class ViewController: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    // MARK: properties
    @IBOutlet weak var mealNameLabel: UILabel!
    @IBOutlet weak var nameTextField: UITextField!
    @IBOutlet weak var photoImageView: UIImageView!
    @IBOutlet weak var ratingControl: RatingControl!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Handle the text field user input through delegate callbacks
        nameTextField.delegate = self
        
    }
    // MARK: UITextFieldDelegate
    func textFieldShouldReturn(textField: UITextField) -> Bool {
        // hide the keyboard
        textField.resignFirstResponder()
        return true
    }
    func textFieldDidEndEditing(textField: UITextField) {
        mealNameLabel.text = textField.text
    }
    // MARK: UIImagePickerControllerDelegate
    func imagePickerControllerDidCancel(picker: UIImagePickerController) {
        // dismiss the picker if the user canceled
        dismissViewControllerAnimated(true, completion: nil)
    }
    func imagePickerController(picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : AnyObject]) {
        // the info dictionary contains multiple representations of the image, this uses the original
    let selectedImage = info[UIImagePickerControllerOriginalImage] as! UIImage
    // set photoimageview to display selected image
        photoImageView.image = selectedImage
        
        //dismiss the picker
        dismissViewControllerAnimated(true, completion: nil)
        
    }
    
    // MARK: actions
    @IBAction func selectImageFromPhotoLibrary(sender: UITapGestureRecognizer) {
        // Hide the keyboard
        nameTextField.resignFirstResponder()
        
        // UIimagepickcontroller is a view controler that lets user pick edia from their photo library
        let imagePickerController = UIImagePickerController()
        
        // allow photos to be picked and not taken
        imagePickerController.sourceType = .PhotoLibrary
        
        // make sure viewcontroller is notified when the user picks the image
        imagePickerController.delegate = self
        
        presentViewController(imagePickerController, animated: true, completion: nil)
    
    }

    
}
