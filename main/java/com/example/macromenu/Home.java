package com.example.macromenu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private AdView mAdView;

    DatabaseHelper db;

    ListView itemsList;
    ArrayList<String> itemName = new ArrayList<>();
    ArrayList<String> itemDesc = new ArrayList<>();
    ArrayList<String> itemPrice = new ArrayList<>();
    ArrayList<String> itemTypes = new ArrayList<>();
    //ArrayList<Integer> imageIDs = new ArrayList<>();

    Integer[] imageIDs = {  R.drawable.beefburger,
                            R.drawable.beefitybeef,
                            R.drawable.brownieicecream,
                            R.drawable.chickenburger,
                            R.drawable.icecream,
                            R.drawable.wafficebanana,
                            R.drawable.waffles  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //ActionBar bar = getActionBar();
        //bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#290071")));

        itemsList = findViewById(R.id.listview);

        db = new DatabaseHelper(this);

        Cursor cursor = db.getItemTypes();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            itemTypes.add(cursor.getString(cursor.getColumnIndex("Type")));

        }
        cursor.close();

        CustomAdaptor customAdaptor = new CustomAdaptor();
        itemsList.setAdapter(customAdaptor);

        itemsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String categorySelected = itemTypes.get(position);

                Intent intent = new Intent(Home.this, SelectedCategory.class);
                intent.putExtra("Category", categorySelected);
                startActivity(intent);
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    class CustomAdaptor extends BaseAdapter{

        @Override
        public int getCount() {
            return itemTypes.size();
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

            View view = getLayoutInflater().inflate(R.layout.listviewcategory_layout, null);

            TextView foodType = view.findViewById(R.id.foodType);

            foodType.setText(itemTypes.get(position));


            return view;
        }
    }

    public void openCart(View v){
        Intent intent = new Intent(Home.this, Cart.class);
        startActivity(intent);

    }

    public void showConnectivity(View v){
        Intent intent = new Intent(Home.this, WebConnectivity.class);
        startActivity(intent);
    }


}
