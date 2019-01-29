package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddPlantMenuActivity extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static final String MY_PREFS_NAME = "MyPrefs";

    SearchView searchView;
    ImageButton cancelIcon;
    TextView addPlantsTitle;

    Toolbar addPlantMenuToolbar;

    String value = "";
    int stickNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_menu);
        searchView = findViewById(R.id.searchview);
        searchView.setQueryHint("Search your plant");

        // handle the go back button by adding a function to the cancel icon in the layout
        addPlantMenuToolbar = findViewById(R.id.addPlantMenuToolbar);
        cancelIcon = addPlantMenuToolbar.findViewById(R.id.btn_cancel);
        cancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        addPlantsTitle = addPlantMenuToolbar.findViewById(R.id.text_view_add_plants);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        addPlantsTitle.setTypeface(myCustomFont);

        // initialize the database so we can get data from it
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plantmDatabase = mDatabase.child("Plants");

        final DatabaseReference userPinDatabase = mDatabase.child("Pins");

        // get the pin so it doest get saved in the app and recognizable by the app
        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pinString = preferences.getString("pin", null);

        assert pinString != null;
        final DatabaseReference childUserPin = userPinDatabase.child(pinString);
        final DatabaseReference allPlants = childUserPin.child("Plants");

        ListView listView = findViewById(R.id.database_list_view);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        listView.setAdapter(adapter);

        // create an ArrayAdapter from List
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.text_listview_left, arrayList) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                // get the Item from ListView
                View view = super.getView(position, convertView, parent);

                // initialize a TextView for ListView each Item
                TextView tv = view.findViewById(android.R.id.text1);

                // set the text color of TextView (ListView Item)
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));

                Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_regular.ttf");
                tv.setTypeface(myCustomFont);

                // generate ListView Item using TextView
                return view;
            }
        };

        // dataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        // searchQuery for SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        // go to next activity when item in ListView is pressed
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 value = "";
                 value = arrayList.get(i);

                    allPlants.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            // get all the stick numbers using count
                            stickNumber = (int) dataSnapshot.getChildrenCount();
                            allPlants.child("Stick"+stickNumber).child("Water").setValue("0");
                            allPlants.child("Stick"+stickNumber).child("Light").setValue("0");
                            allPlants.child("Stick"+stickNumber).child("Temperature").setValue("0");
                            allPlants.child("Stick"+stickNumber).child("Duration").setValue("0");
                            allPlants.child("Stick"+stickNumber).child("Name").setValue(value);
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                // give the value to the main activity to set the right plant names
                Intent intent = new Intent(AddPlantMenuActivity.this, MainActivity.class);
                intent.putExtra("plant", value);
                finish();
                startActivity(intent);
            }

        });

        plantmDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // get the plant names and put them in a list view
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                // remove the plant names in the list view when the plants are removed in the fire base
                String string = dataSnapshot.getValue(String.class);
                arrayList.remove(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        }
    }

