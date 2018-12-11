package com.example.joelruhe.myapplication.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelruhe.myapplication.R;
import com.example.joelruhe.myapplication.authentication.firebase.FireBaseLoginActivity;
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

    public static final String MY_PREFS_NAME = "MyPrefs";

    EditText editTextpin;
    String pin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new android.transition.Explode());
        setContentView(R.layout.activity_enter_pin);

        editTextpin = (EditText) findViewById(R.id.edittextPin);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredPin = pref.getString("pin", null);

        if (restoredPin == null) {
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
                                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                    editor.putString("pin", pin);
                                    editor.apply();

                                    refPin.child("verify").setValue("verified");
                                    refPin.child("Plants").setValue("0");
                                    finish();
                                    startActivity(new Intent(EnterPinActivity.this, AddPlantMenuActivity.class));
                                    //startActivity(new Intent(EnterPinActivity.this, MainActivity.class));
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
            Intent i = new Intent(EnterPinActivity.this, MainActivity.class);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(EnterPinActivity.this).toBundle());
            finish();

            /*
            Intent i = new Intent(EnterPinActivity.this, AddPlantMenuActivity.class);
            startActivity(i,
                    ActivityOptions.makeSceneTransitionAnimation(EnterPinActivity.this).toBundle());
            finish();*/
        }
    }
}

