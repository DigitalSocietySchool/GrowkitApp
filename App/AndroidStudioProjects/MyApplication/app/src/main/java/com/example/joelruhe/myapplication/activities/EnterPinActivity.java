package com.example.joelruhe.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;

public class EnterPinActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth auth;

    EditText editTextpin;
    //@BindView(R.id.edittextPin)

    String pin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        editTextpin = (EditText) findViewById(R.id.edittextPin);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        SharedPreferences.Editor editor = pref.edit();
        //SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         //String sharedPin = pref.getString(pin, pin);

        //if (sharedPin == null && sharedPin.isEmpty()) {

            Button nextButton = (Button) findViewById(R.id.buttonNext);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference plantmDatabase = mDatabase.child("Pins");

            auth = FirebaseAuth.getInstance();

            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pin = editTextpin.getText().toString();
                    final DatabaseReference refPin = plantmDatabase.child(pin);
                    final DatabaseReference pinVerify = refPin.child("verified");

                    plantmDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (pin.matches("")) {
                                Toast.makeText(EnterPinActivity.this, "Fill in pin", Toast.LENGTH_SHORT).show();
                            } else if (!dataSnapshot.hasChild(pin)) {
                                Toast.makeText(EnterPinActivity.this, "Pin does not exist", Toast.LENGTH_SHORT).show();
                            } else if (dataSnapshot.hasChild(pin)) {
                                String pinValue = dataSnapshot.child(pin).child("verify").getValue().toString();
                                if (pinValue.equals("verified")) {
                                    Toast.makeText(EnterPinActivity.this, "Pin is already in use", Toast.LENGTH_SHORT).show();
                                } else {
                                    //SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                                    //SharedPreferences.Editor editor = pref.edit();
                                    //editor.putString("pin", pin);
                                    //editor.commit();

                                    refPin.child("verify").setValue("verified");
                                    finish();
                                    startActivity(new Intent(EnterPinActivity.this, MainActivity.class));

                                }
                            /*pinVerify.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    pinVerify.setValue()
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });*/
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });


                }
            });
    }
}

