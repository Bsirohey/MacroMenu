package com.example.macromenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    DatabaseHelper macroDb;

    EditText userName;
    EditText userEmail;
    EditText userPassword;
    EditText userConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        macroDb = new DatabaseHelper(this);
    }

    public void RedirectToSignInPage(View v){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }

    public void SignUpButton(View v){
        macroDb.getWritableDatabase();

        userName = findViewById(R.id.SignUpName);
        userEmail = findViewById(R.id.SignUpEmail);
        userPassword = findViewById(R.id.SignUpPassword);
        userConfirmPassword = findViewById(R.id.SignUpConfirmPassword);

        String Name = userName.getText().toString();
        String Email = userEmail.getText().toString();
        String Password = userPassword.getText().toString();
        String ConfirmPassword = userConfirmPassword.getText().toString();

        if (Name.isEmpty()){
            Toast.makeText(this, "Name Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (Email.isEmpty()){
            Toast.makeText(this, "Email Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (Password.isEmpty()){
            Toast.makeText(this, "Password Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (ConfirmPassword.isEmpty()){
            Toast.makeText(this, "Confirm Password Field Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!ConfirmPassword.equals(Password)){
            Toast.makeText(this,"Password and Confirm Password dont Match", Toast.LENGTH_SHORT).show();
            return;
        }

        Cursor cursor = macroDb.checkForRepetition(userEmail.getText().toString());

        if (cursor.getCount() == 0){
            macroDb.insertData(Name, Email, Password);
            Toast.makeText(this,"Sign Up Successful!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this,"User already present, try logging in", Toast.LENGTH_SHORT).show();
            return;
        }

    }
}
