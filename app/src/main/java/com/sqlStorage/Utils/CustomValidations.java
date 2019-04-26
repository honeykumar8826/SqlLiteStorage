package com.sqlStorage.Utils;

import android.util.Patterns;

import java.util.regex.Pattern;

public class CustomValidations {

    /*to check string is empty or not equal to null*/
    public Boolean nameValidation(String userInput) {
        return !userInput.isEmpty();
    }

    /*to check string have an number or not*/
    public Boolean stringContainNumber(String userInput) {
        return !Pattern.compile("[0-9]").matcher(userInput).find();
    }
    /*to check email is valid or not*/
    public boolean isValidEmail(String userInput) {
        // Toast.makeText(this, "right ", Toast.LENGTH_SHORT).show();
// Toast.makeText(this, "wrong", Toast.LENGTH_SHORT).show();
        return Patterns.EMAIL_ADDRESS.matcher(userInput).matches();
    }
    /*to check password is valid or not*/
    public boolean isPasswordValid(String userInput) {
        return !userInput.isEmpty();
    }
    /*to check phone number is valid or not*/
    public boolean isPhoneNumberValid(String userInput) {
        return Patterns.PHONE.matcher(userInput).matches();
    }
}
