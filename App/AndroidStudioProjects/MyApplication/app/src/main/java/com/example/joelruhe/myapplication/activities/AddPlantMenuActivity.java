package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddPlantMenuActivity extends AppCompatActivity {

    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    public static final String MY_PREFS_NAME = "MyPrefs";

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_menu);
        searchView = findViewById(R.id.searchview);
        searchView.setQueryHint("Search your plant");

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plantmDatabase = mDatabase.child("Plants");

        final DatabaseReference userPinDatabase = mDatabase.child("Pins");

        SharedPreferences preferences = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String pinString = preferences.getString("pin", null);

        assert pinString != null;
        final DatabaseReference childUserPin = userPinDatabase.child(pinString);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        ListView listView = findViewById(R.id.database_list_view);

        listView.setAdapter(adapter);

        //SearchQuery for SearchView
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

        //Go to next activity when item in ListView is pressed
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = arrayList.get(i);
                final DatabaseReference plantUserPin = childUserPin.child("Plants");
                plantUserPin.push().setValue(value);
                Intent intent = new Intent(AddPlantMenuActivity.this, MainActivity.class);
                intent.putExtra("plant", value);
                finish();
                startActivity(intent);
            }
        });

        plantmDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                arrayList.add(string);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
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
