package com.example.loginactivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginactivity.utils.HandlePassword;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity {
    MaterialButton login, signup;
    EditText username, password;
    DBConnect dbConnect;
    Context context;
    HandlePassword handlePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.loginbtn);
        signup = findViewById(R.id.signup);

        context = this;
        dbConnect = new DBConnect(this);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                username = findViewById(R.id.userName);
                password = findViewById(R.id.password);

                String usernameInput = username.getText().toString();
                String passwordInput = password.getText().toString();

                User user = dbConnect.findUserByUserName(usernameInput);
                System.out.println("user name: " +user.getUsername());

                try {
                    handlePassword = new HandlePassword();
                    String pw = user.getPassword();
                    String decryptedPassword = handlePassword.decrypt(pw);
                    System.out.println("bbbb");
                    System.out.println("decrypt password :" + decryptedPassword);

                    if(decryptedPassword.equals(passwordInput)) {
                        System.out.println("password correct");
                        Toast toast = Toast.makeText(getApplicationContext(), "Login Success" , Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        System.out.println("sssss");
                        Toast toast = Toast.makeText(getApplicationContext(), "Login Failed" , Toast.LENGTH_LONG);
                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}