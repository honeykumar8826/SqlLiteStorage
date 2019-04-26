package com.sqlStorage.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqlStorage.R;
import com.sqlStorage.Utils.CustomValidations;
import com.sqlStorage.database.DataContractor;

public class SignUpActivity extends AppCompatActivity {
    private final CustomValidations customValidations = new CustomValidations();
    private Button insertRecord;
    private EditText editTextFName, editTextLName, editTextPassword, editTextConfirmPassword, editTextEmail, editTextPhoneNumber;
    private String password;
    private final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        /*initialization of id*/
        intItId();
        /*listener for inserting the data*/
        insertRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromUser();
            }
        });
    }
    private void getValueFromUser() {
        String fName = editTextFName.getText().toString();
        editTextFName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i(TAG, "onFocusChange: "+v);
                if (hasFocus) {
                    Log.i(TAG, "onFocusChange: ");
                    Toast.makeText(SignUpActivity.this, "hello1", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i(TAG, "onFocusChange: else");
                    Toast.makeText(SignUpActivity.this, "hello", Toast.LENGTH_SHORT).show();
                }
            }
        });
        String lName = editTextLName.getText().toString();
        String email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();
        if (customValidations.nameValidation(fName) && customValidations.stringContainNumber(fName)) {
            if (customValidations.nameValidation(lName) && customValidations.stringContainNumber(lName)) {
                if (!email.isEmpty() && customValidations.isValidEmail(email)) {
                    if (customValidations.isPasswordValid(password)) {
                        if (customValidations.isPasswordValid(confirmPassword) && isConfirmPasswordMatch(confirmPassword)) {
                            if (!phoneNumber.isEmpty() && customValidations.isPhoneNumberValid(phoneNumber)) {
                                /*take value from the user and insert into the database*/
                                if (DataContractor.getInstance().insertUserInput(fName, lName, email, password, phoneNumber, confirmPassword)) {
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    Toast.makeText(this, "value stored", Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(this, "value is not stored", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                editTextPhoneNumber.setError("Phone Number  is must");
                                //Toast.makeText(this, "Phone Number is must", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            editTextConfirmPassword.setError("Confirm Password is must is must");
                            //Toast.makeText(this, "Confirm Password is must", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        editTextPassword.setError("Password is must");
                        //Toast.makeText(this, "Password is must", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    editTextEmail.setError("Email is must");
                    //Toast.makeText(this, "Email is must", Toast.LENGTH_SHORT).show();

                }
            } else {
                editTextLName.setError("LastName is must");
                //Toast.makeText(this, "LastName is must", Toast.LENGTH_SHORT).show();

            }
        } else {
            editTextFName.setError("FirstName is must");
            // Toast.makeText(this, "FirstName is must"+customValidations.nameValidation(fName), Toast.LENGTH_SHORT).show();
        }

    }

    private void intItId() {
        insertRecord = findViewById(R.id.btn_create_account);
        editTextFName = findViewById(R.id.et_fName);
        editTextLName = findViewById(R.id.et_lName);
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);
        editTextPhoneNumber = findViewById(R.id.et_phoneNumber);
        editTextConfirmPassword = findViewById(R.id.et_confirmPassword);

    }
    /*to check password is match with confirm password*/
    private boolean isConfirmPasswordMatch(String userInput) {
        return userInput.equals(password);
    }

}

