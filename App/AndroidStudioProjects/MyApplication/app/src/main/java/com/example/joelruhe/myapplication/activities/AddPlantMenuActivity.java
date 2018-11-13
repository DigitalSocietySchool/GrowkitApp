package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

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
    private Button btnAdd;
    private DatabaseReference mDatabase;
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant_menu);

        Button button = (Button)findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your code in here!
                i = 1;
                Intent intent = new Intent(AddPlantMenuActivity.this,MainActivity.class);
                intent.putExtra("number", i);
                startActivity(intent);
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference plantmDatabase = mDatabase.child("Plants").child("emgpiPu6R9kYeEibLlox");


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listView = (ListView)findViewById(R.id.database_list_view);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        listView.setAdapter(adapter);

       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v){
               //plantmDatabase.push().setValue("Hey");
               String userId = mDatabase.push().getKey();
               plantmDatabase.child(userId).setValue("test");


           }
       });

        mDatabase.addChildEventListener(new ChildEventListener() {
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
