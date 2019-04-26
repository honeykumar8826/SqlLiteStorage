package com.sqlStorage.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sqlStorage.R;
import com.sqlStorage.Utils.CustomValidations;
import com.sqlStorage.database.DataContractor;

public class LoginActivity extends AppCompatActivity {
    private final String TAG = "TAG";
    private Button buttonLogin, signUp;
    private String uEmail, uPassword;
    private EditText editTextEmail, editTextPassword;
    private TextInputLayout textInputLayout;
    private TextView tv;
    private final CustomValidations customValidations = new CustomValidations();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: "+savedInstanceState);
        setContentView(R.layout.activity_login);
        /*initialization of id*/
        intItId();
        /* listener for login */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*get the value from the user*/
                getUserInput();
                if (customValidations.isValidEmail(uEmail)) {
                    if (customValidations.isPasswordValid(uPassword)) {  /*to check user credential is correct or not*/
                        isAuthenticate(uEmail, uPassword);
                    } else {

                        Toast.makeText(LoginActivity.this, "Password is must", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(LoginActivity.this, "Email is must", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*listener for signUp*/
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        //listener for textview
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // editTextEmail.setTransformationMethod(PasswordTransformationMethod.getInstance());
                editTextEmail.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                Toast.makeText(LoginActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void isAuthenticate(String formalEmail, String formalPassword) {
        Log.i(TAG, "isAuthenticate: " + formalEmail);
        Cursor cursor = DataContractor.getInstance().getValueFromTheDatabase(formalEmail, formalPassword);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String fName = cursor.getString(0);
                String lName = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                String phoneNumber = cursor.getString(4);
                String confirmPassword = cursor.getString(5);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle extras = new Bundle();
                extras.putString("fName", fName);
                extras.putString("lName", lName);
                extras.putString("email", email);
                extras.putString("password", password);
                extras.putString("confirmPassword", confirmPassword);
                extras.putString("phoneNumber", phoneNumber);
                intent.putExtras(extras);
                startActivity(intent);
                Log.i(TAG, "getValueFromTheDatabase: " + cursor.getString(2));
                //cursor.getString(cursor.getColumnIndex("FirstName"));
                //cursor.getColumnIndex(COL_2);
            }
        } else {
            textInputLayout.setError("credential is not match");
            //  Toast.makeText(this, "credential is not match", Toast.LENGTH_SHORT).show();
        }
    }

    private void getUserInput() {
        uEmail = editTextEmail.getText().toString();
        uPassword = editTextPassword.getText().toString();
        Log.i(TAG, "getUserInput: " + uEmail + "" + uPassword);
    }

    private void intItId() {
        editTextEmail = findViewById(R.id.et_email);
        editTextPassword = findViewById(R.id.et_password);
        buttonLogin = findViewById(R.id.btn_login);
        signUp = findViewById(R.id.btn_signUp);
        textInputLayout = findViewById(R.id.til_password);
        tv = findViewById(R.id.tv_hello);

        /*listener on editText*/
        onTextChange();
    }

    private void onTextChange() {
        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i(TAG, "beforeTextChanged:before ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG, "onTextChanged: on");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(TAG, "afterTextChanged: after");
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: ");
    }

}
