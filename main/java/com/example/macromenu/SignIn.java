package com.example.macromenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignIn extends AppCompatActivity {

    ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();

    EditText userEmail;
    EditText userPassword;

    DatabaseHelper macroDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        macroDB = new DatabaseHelper(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(exampleBroadcastReceiver, filter);
    }

    @Override
    protected void onStop(){
        super.onStop();
        unregisterReceiver(exampleBroadcastReceiver);
    }

    public void RedirectToSignUpPage(View v){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void SignInButton(View v){
        macroDB.getWritableDatabase();

        String email;
        String pass;

        userEmail = findViewById(R.id.SignInEmail);
        userPassword = findViewById(R.id.SignInPassword);

        email = userEmail.getText().toString();
        pass = userPassword.getText().toString();

        Cursor cursor = macroDB.checkSignInCredentials(email, pass);

        if (cursor.getCount() == 0){
            Toast.makeText(this, "Credentials Incorrect.", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            Toast.makeText(this, "Log In Successful.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }


    }
}
