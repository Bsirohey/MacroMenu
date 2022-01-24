package com.example.macromenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectedCategory extends AppCompatActivity {

    DatabaseHelper db;
    String categorySelected;

    ListView itemsList;
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> itemDesc = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();

    Integer[] imageIDsBurgers = {R.drawable.beefburger, R.drawable.beefitybeef, R.drawable.chickenburger};
    Integer[] imageIDsPizzas = {R.drawable.fajitapizza, R.drawable.beefpizza, R.drawable.pepperonipizza, R.drawable.allstarpizza};
    Integer[] imageIDsDesserts = {R.drawable.brownieicecream, R.drawable.icecream, R.drawable.wafficebanana, R.drawable.waffles};
    Integer[] imageIDs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_category);

        Intent intent = getIntent();
        categorySelected = intent.getStringExtra("Category");

        switch (categorySelected){
            case "Burger":
                imageIDs = imageIDsBurgers.clone();
                break;
            case "Pizza":
                imageIDs = imageIDsPizzas.clone();
                break;
            case "Dessert":
                imageIDs = imageIDsDesserts.clone();
                break;
        }

        itemsList = findViewById(R.id.listviewCategory);
        db = new DatabaseHelper(this);

        Cursor cursor = db.getItemsByType(categorySelected);

        if (cursor != null){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                itemName.add(cursor.getString(cursor.getColumnIndex("Name")));
                itemDesc.add(cursor.getString(cursor.getColumnIndex("Description")));
                itemPrice.add(cursor.getString(cursor.getColumnIndex("Price")));

            }
        }
        cursor.close();

        CustomAdaptor customAdaptor = new CustomAdaptor();
        itemsList.setAdapter(customAdaptor);

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SelectedCategory.this, "Item Added in cart: " + itemName.get(position), Toast.LENGTH_SHORT).show();

                db.insertDataIntoCart(itemName.get(position), itemPrice.get(position));

                switch(itemName.get(position)){
                    case "Chicken burger":
                    case "Beefity Beef Burger":
                    case "Beef Burger":
                        AlertDialog.Builder altdial = new AlertDialog.Builder(SelectedCategory.this);
                        altdial.setMessage("Do you want to make it a meal?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.insertDataIntoCart("Fries and Drink", "100");
                                        Toast.makeText(SelectedCategory.this, "Fries and Drink Added!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert = altdial.create();
                        alert.setTitle("Make it a Meal!");
                        alert.show();
                        break;
                    case "Beef Pizza":
                    case "Fajita Pizza":
                    case "Pepperoni Pizza":
                    case "AllStar Pizza":
                        AlertDialog.Builder altdial1 = new AlertDialog.Builder(SelectedCategory.this);
                        altdial1.setMessage("Make the pizza Large?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.insertDataIntoCart("Pizza Size: Large", "400");
                                        Toast.makeText(SelectedCategory.this, "Large Pizza Added!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert1 = altdial1.create();
                        alert1.setTitle("Make it a Meal!");
                        alert1.show();
                        break;
                    case "Ice Cream":
                        AlertDialog.Builder altdial2 = new AlertDialog.Builder(SelectedCategory.this);
                        altdial2.setMessage("Add Extra Scoop?").setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.insertDataIntoCart("IceCream: Extra Scope", "70");
                                        Toast.makeText(SelectedCategory.this, "Mega Icecream!", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert2 = altdial2.create();
                        alert2.setTitle("Add an Extra scoop!");
                        alert2.show();
                        break;

                }

            }
        });

    }



    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return itemName.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.listview_layout, null);

            ImageView foodImage = view.findViewById(R.id.foodImage);
            TextView foodName = view.findViewById(R.id.foodName);
            TextView foodDesc = view.findViewById(R.id.foodDesc);
            TextView foodPrice = view.findViewById(R.id.foodPrice);

            foodImage.setImageResource(imageIDs[position]);
            foodName.setText(itemName.get(position));
            foodDesc.setText(itemDesc.get(position));
            foodPrice.setText(itemPrice.get(position));

            return view;
        }
    }


}
