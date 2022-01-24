package com.example.macromenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    ExampleBroadcastReceiver exampleBroadcastReceiver = new ExampleBroadcastReceiver();

    DatabaseHelper macroDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        macroDB = new DatabaseHelper(this);

        macroDB.deleteDataFromCart();

        Log.d("kek", "kek");

//        macroDB.insertDataIntoProduct("Beef Burger", "Burger with Beef Patty", "350", "Burger");
//        macroDB.insertDataIntoProduct("Beefity Beef Burger", "Burger with double beef patty", "450", "Burger");
//        macroDB.insertDataIntoProduct("Brownie&Ice Cream","Hot and Cold Combo that melts in your mouth", "250", "Dessert");
//        macroDB.insertDataIntoProduct("Chicken burger", "Burger with Chicken patty", "300", "Burger");
//        macroDB.insertDataIntoProduct("Ice Cream", "Flavors available", "70", "Dessert");
//        macroDB.insertDataIntoProduct("Fajita Pizza", "Pizza with Fajita", "500", "Pizza");
//        macroDB.insertDataIntoProduct("Beef Pizza", "Pizza with Beef", "550", "Pizza");
//        macroDB.insertDataIntoProduct("Pepperoni Pizza", "Pizza with Pepperoni", "550", "Pizza");
//        macroDB.insertDataIntoProduct("AllStar Pizza", "Pizza with Everything", "650", "Pizza");
//        macroDB.insertDataIntoProduct("WaffIceBanans", "Bananas + Waffles + Icecream. Da best", "375", "Dessert");
//        macroDB.insertDataIntoProduct("Waffles", "Owners personal favourite dessert", "300", "Dessert");

        //ca-app-pub-8813395075888202~4854170698

        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
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
}
