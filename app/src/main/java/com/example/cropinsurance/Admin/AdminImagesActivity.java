package com.example.cropinsurance.Admin;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cropinsurance.R;
import com.example.cropinsurance.Upload;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminImagesActivity extends AppCompatActivity implements AdminImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private AdminImageAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;
    FirebaseUser currentUser;

    private List<Upload> mUploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_images);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new AdminImageAdapter(AdminImagesActivity.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(AdminImagesActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Long Press For More Details", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Upload uploadCurrent = mUploads.get(position);

        new AlertDialog.Builder(AdminImagesActivity.this)
                .setTitle("Image Details")
                .setMessage("Insurance Id : "+ uploadCurrent.getInsuranceId() + "\nName : "+ uploadCurrent.getName()
                        +"\nEmail : " + uploadCurrent.getEmail() +"\nDate : " + uploadCurrent.getTimeStamp()
                        + "\nLatitude : " + uploadCurrent.getLatitude() + "\nLongitude : " + uploadCurrent.getLongitude()
                        + "\nAddress : " + uploadCurrent.getAddress()).show();
    }

    @Override
    public void onDeleteClick(int position) {
        Upload uploadCurrent = mUploads.get(position);
        final String Name = uploadCurrent.getName();
        final String Email = uploadCurrent.getEmail();
        final String Image = uploadCurrent.getImageUrl();
        final String Address = uploadCurrent.getAddress();
        final String Timestamp = uploadCurrent.getTimeStamp();
        final String Id = uploadCurrent.getInsuranceId();
        final String longitude = uploadCurrent.getLongitude();
        final  String latitude = uploadCurrent.getLatitude();
        final String Process =  "Approved";

        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
            }
        });

        Upload upload = new Upload(Name,Image,Email,Address,Timestamp,Process,Id,longitude,latitude);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);

        mAdapter.removeAt(position);
    }

    @Override
    public void onRejectClick(int position) {
        Upload uploadCurrent = mUploads.get(position);
        final String Name = uploadCurrent.getName();
        final String Email = uploadCurrent.getEmail();
        final String Image = uploadCurrent.getImageUrl();
        final String Address = uploadCurrent.getAddress();
        final String Timestamp = uploadCurrent.getTimeStamp();
        final String Id = uploadCurrent.getInsuranceId();
        final String longitude = uploadCurrent.getLongitude();
        final  String latitude = uploadCurrent.getLatitude();
        final String Process =  "Rejected";

        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getKey();
        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDatabaseRef.child(selectedKey).removeValue();
            }
        });



        Upload upload = new Upload(Name,Image,Email,Address,Timestamp,Process,Id,longitude,latitude);
        String uploadId = mDatabaseRef.push().getKey();
        mDatabaseRef.child(uploadId).setValue(upload);

        mAdapter.removeAt(position);
    }

    @Override
    public void onRemoveClick(int position) {
        mAdapter.removeAt(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }
}