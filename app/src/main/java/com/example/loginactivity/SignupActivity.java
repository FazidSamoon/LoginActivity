package com.example.loginactivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

public class SignupActivity extends AppCompatActivity {

    MaterialButton button;
    EditText password, username, firstName, lastName, address;
    DBConnect dbConnect;
    Context context;
    HandlePassword handlePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        button = findViewById(R.id.signup);
        password = findViewById(R.id.password);
        username = findViewById(R.id.userName);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        address = findViewById(R.id.editTextTextPostalAddress);

        context =  this;

        dbConnect = new DBConnect(this);

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                User user = new User();
                try {
                    handlePassword = new HandlePassword();
                    String hashedPassword = handlePassword.encrypt(password.getText().toString());
                    user.setUsername(username.getText().toString());
                    user.setAddress(address.getText().toString());
                    user.setLastName(lastName.getText().toString());
                    user.setFirstName(firstName.getText().toString());
                    user.setPassword(hashedPassword);

                    dbConnect.registerUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }
}