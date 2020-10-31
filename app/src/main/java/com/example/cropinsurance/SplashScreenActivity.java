package com.example.cropinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropinsurance.Admin.adminDashboard;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_left);
        Animation slideRigt = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_right);
        ImageView icon = findViewById(R.id.icon);
        TextView name = findViewById(R.id.textView);

        icon.setAnimation(slideLeft);
        name.setAnimation(slideRigt);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser!=null){
            new Handler().postDelayed(new  Runnable() {
                @Override
                public void run() {
                    onStart();
                }
            }, 800);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, Login.class));
                    finish();
                }
            }, 800);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            final DocumentReference df = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isAdmin") != null) {
                        startActivity(new Intent(getApplicationContext(), adminDashboard.class));
                        finish();
                    }
                    if(documentSnapshot.getString("isUser") != null){
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
                        finish();
                    }
                }
            });
        }
    }
}
