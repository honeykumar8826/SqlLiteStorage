package com.sqlStorage.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sqlStorage.R;
import com.sqlStorage.Utils.CustomValidations;
import com.sqlStorage.database.DataContractor;

public class MainActivity extends AppCompatActivity {
    private final CustomValidations customValidations = new CustomValidations();
    private Button signUp;
    private EditText editTextFName, editTextLName, editTextPassword, editTextConfirmPassword, editTextEmail, editTextPhoneNumber;
    private String password;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*initialization of id*/
        inItId();
//        set the toolbar
        setToolbar();
        if (isBundleEmptyForIntentData()) {
            getDataFromIntent();
        }
        /*listener for button */
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValueFromUser();
            }
        });
    }


    private void setToolbar() {
      toolbar.setNavigationIcon(R.drawable.ic_dehaze_black_24dp);
//      anything that you set before setSupportActionbar that will become fix
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Editable Page");
//      getSupportActionBar().setSubtitle("update record");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setBackgroundColor(Color.WHITE);
//        toolbar.setTitleTextColor(getColor(R.color.colorPrimary)); //min api>=23 for this.
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void inItId() {
        editTextFName = findViewById(R.id.et_fName);
        editTextLName = findViewById(R.id.et_lName);
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);
        editTextPhoneNumber = findViewById(R.id.et_phoneNumber);
        editTextConfirmPassword = findViewById(R.id.et_confirmPassword);
        signUp = findViewById(R.id.btn_signUp);
        toolbar = findViewById(R.id.customToolBar);
    }

    private void getValueFromUser() {
        String fName = editTextFName.getText().toString();
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
                                if (DataContractor.getInstance().updateTableData(fName, lName, email, password, phoneNumber, confirmPassword)) {
                                    Toast.makeText(this, "value stored", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(this, "value is not stored", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(this, "Phone Number is must", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Confirm Password is must", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(this, "Password is must", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(this, "Email is must", Toast.LENGTH_SHORT).show();

                }
            } else {
                Toast.makeText(this, "LastName is must", Toast.LENGTH_SHORT).show();

            }
        } else {

            Toast.makeText(this, "FirstName is must" + customValidations.nameValidation(fName), Toast.LENGTH_SHORT).show();
        }

    }

    /*to check password is match with confirm password*/
    private boolean isConfirmPasswordMatch(String userInput) {
        return userInput.equals(password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit_mode:
                enableEditMode();
                return true;
            case R.id.delete_record:
                DataContractor.getInstance().deleteRecordFromDatabase();
                return true;
            case R.id.content_provider:
                Intent intent = new Intent(MainActivity.this, ContentProviderActivity.class);
                startActivity(intent);
                return true;
            default:
                Toast.makeText(this, "Do Right Selection", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    private void enableEditMode() {
        editTextFName.setEnabled(true);
        editTextLName.setEnabled(true);
        editTextEmail.setEnabled(true);
        editTextPassword.setEnabled(true);
        editTextConfirmPassword.setEnabled(true);
        editTextPhoneNumber.setEnabled(true);
        signUp.setVisibility(View.VISIBLE);

    }

    private boolean isBundleEmptyForIntentData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        return extras != null;
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(!extras.isEmpty())
        {
            String fName = extras.getString("fName", "");
            String TAG = "TAG";
            Log.i(TAG, "getDataFromIntent: " + fName);
            String lName = extras.getString("lName", "");
            String email = extras.getString("email");
            String password = extras.getString("password");
            String confirmPassword = extras.getString("confirmPassword");
            String phoneNumber = extras.getString("phoneNumber");
            editTextFName.setText(fName);
            editTextLName.setText(lName);
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            editTextConfirmPassword.setText(confirmPassword);
            editTextPhoneNumber.setText(phoneNumber);
        }


    }

}

