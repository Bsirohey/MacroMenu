package com.example.macromenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    DatabaseHelper db;

    ListView cartItemsList;
    ArrayList<String> cartItemNames = new ArrayList<>();
    ArrayList<String> cartItemPrices = new ArrayList<>();

    int tempSum = 0;
    TextView totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartItemsList = findViewById(R.id.listviewCart);

        db = new DatabaseHelper(this);

        Cursor cursor = db.getDataFromCart();
        if (cursor != null){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                cartItemNames.add(cursor.getString(cursor.getColumnIndex("FoodName")));
                cartItemPrices.add(cursor.getString(cursor.getColumnIndex("FoodPrice")));
            }
            cursor.close();
        }

        CustomAdaptor customAdaptor = new CustomAdaptor();
        cartItemsList.setAdapter(customAdaptor);

        totalAmount = findViewById(R.id.totalAmount);
        for (int counter=0; counter<cartItemPrices.size(); counter++){
            tempSum = tempSum + Integer.parseInt(cartItemPrices.get(counter));
        }
        totalAmount.setText(Integer.toString(tempSum));

    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return cartItemNames.size();
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

            View view = getLayoutInflater().inflate(R.layout.listviewcart_layout, null);

            TextView foodName = view.findViewById(R.id.cartItemName);
            TextView foodPrice = view.findViewById(R.id.cartItemPrice);

            foodName.setText(cartItemNames.get(position));
            foodPrice.setText(cartItemPrices.get(position));

            return view;
        }
    }

    public void orderDone(View v){
        Toast.makeText(this, "ORDER PLACED! PLEASE GO TO THE COUNTER", Toast.LENGTH_LONG).show();

        db.deleteDataFromCart();

        Intent intent = new Intent(Cart.this, Home.class);
        startActivity(intent);
    }

}
