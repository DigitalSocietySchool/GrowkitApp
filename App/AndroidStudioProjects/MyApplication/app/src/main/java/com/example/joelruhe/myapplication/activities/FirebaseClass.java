package com.example.joelruhe.myapplication.activities;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseClass {
    private final String USERS = "Users";

    /**
     * Inserts new user into the realtime database
     * @param pEmail
     * @param pPassword
     */
    public void insertNewUser(String pEmail, String pPassword){
        DatabaseReference rootRef, demoRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        demoRef = rootRef.child(USERS);
        demoRef.child(getFirebaseEmail(pEmail)).setValue(pPassword);
    }

    /**
     * Updates the users email (key) in the realtime database
     * @param pOldEmail
     * @param pNewEmail
     */
    public void updateFirebaseEmail(final String pOldEmail, final String pNewEmail){
        final DatabaseReference rootRef, userRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child(USERS);
        DatabaseReference childDemoRef = userRef.child(getFirebaseEmail(pOldEmail));

        childDemoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String password = dataSnapshot.getValue().toString();
                userRef.child(getFirebaseEmail(pOldEmail)).removeValue();
                userRef.child(getFirebaseEmail(pNewEmail)).setValue(password);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Insert updated password user in realtime database
     * @param email
     * @param password
     */
    public void updateFirebasePassword(String email, String password){
        DatabaseReference rootRef, userRef;
        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Users");
        userRef.child(getFirebaseEmail(email)).setValue(password);
    }

    private String getFirebaseEmail(String email){
        email = email.replace("@", "-");
        email = email.replace(".", "_");
        return email;
    }
}
