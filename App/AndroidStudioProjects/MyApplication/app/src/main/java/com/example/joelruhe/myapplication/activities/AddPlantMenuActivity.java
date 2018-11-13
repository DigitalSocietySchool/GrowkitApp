package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.SearchView;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.activities.SidebarActivity.NavigationMenu;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddPlantMenuActivity extends AppCompatActivity {

    int i = 0;

    private ListView listView;
    private DatabaseReference mDatabase;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_menu);

        searchView = (SearchView)findViewById(R.id.searchview);
        searchView.setQueryHint("Search your plant");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plantmDatabase = mDatabase.child("Plants");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listView = (ListView)findViewById(R.id.database_list_view);

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
                i = 1;
                Intent intent = new Intent(AddPlantMenuActivity.this, MainActivity.class);
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });

        //To add something into the database (in this case it is Hey):
        /*
        btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               plantmDatabase.push().setValue("Hey");
           }
       });*/

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
