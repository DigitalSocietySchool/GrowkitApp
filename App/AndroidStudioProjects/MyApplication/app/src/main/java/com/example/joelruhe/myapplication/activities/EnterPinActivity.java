package com.example.joelruhe.myapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EnterPinActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefs";

    EditText editTextpin;
    String pin = "";

    TextView textHeaderPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.activity_enter_pin);

        textHeaderPin = findViewById(R.id.textHeader);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/open_sans_bold.ttf");
        textHeaderPin.setTypeface(myCustomFont);

        editTextpin = findViewById(R.id.edittextPin);

        // get the user pin to store in the shared{references so the app remembers it
        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredPin = pref.getString("pin", null);

        if (restoredPin == null) {
            ImageButton nextButton = findViewById(R.id.buttonNext);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            final DatabaseReference plantmDatabase = mDatabase.child("Pins");

            FirebaseAuth.getInstance();

            nextButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    pin = editTextpin.getText().toString();
                    final DatabaseReference refPin = plantmDatabase.child(pin);
                    refPin.child("verified");

                    plantmDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (pin.matches("")) {
                                Toast.makeText(EnterPinActivity.this, "Fill in pin", Toast.LENGTH_SHORT).show();
                            } else if (!dataSnapshot.hasChild(pin)) {
                                Toast.makeText(EnterPinActivity.this, "Pin does not exist", Toast.LENGTH_SHORT).show();
                            } else if (dataSnapshot.hasChild(pin)) {
                                // skip this activity when the pin value is the same as the pin value from the user
                                String pinValue = Objects.requireNonNull(dataSnapshot.child(pin).child("verify").getValue()).toString();
                                if (pinValue.equals("verified")) {
                                    Toast.makeText(EnterPinActivity.this, "Pin is already in use", Toast.LENGTH_SHORT).show();
                                } else {
                                    // store the pin number in the shared preferences
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("pin", pin);
                                    editor.apply();

                                    // set the pin to verified when a user logs in
                                    refPin.child("verify").setValue("verified");

                                    // finish the activity so you can't go back
                                    finish();
                                    startActivity(new Intent(EnterPinActivity.this, MainActivity.class));
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            });
        }
        else {
            // start the activity
            Intent i = new Intent(EnterPinActivity.this, MainActivity.class);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(EnterPinActivity.this).toBundle());
            finish();
        }
    }
}

