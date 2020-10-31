package com.example.cropinsurance.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropinsurance.ImagesActivity;
import com.example.cropinsurance.InsuranceDetails;
import com.example.cropinsurance.Login;
import com.example.cropinsurance.R;
import com.example.cropinsurance.profile;
import com.example.cropinsurance.progress;
import com.google.firebase.auth.FirebaseAuth;

public class adminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void insuranceapplication(View view) {
        startActivity(new Intent(getApplicationContext(), AdminImagesActivity.class));
    }


    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(), profile.class));
    }

    public void insuranceDetails(View view) {
        startActivity(new Intent(getApplicationContext(), InsuranceDetails.class));
    }
}