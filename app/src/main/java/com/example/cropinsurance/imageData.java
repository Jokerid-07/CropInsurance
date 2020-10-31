 package com.example.cropinsurance;

 import android.content.ContentResolver;
 import android.content.Context;
 import android.content.DialogInterface;
 import android.content.Intent;
 import android.content.pm.PackageManager;
 import android.icu.text.DateFormat;
 import android.location.Address;
 import android.location.Geocoder;
 import android.location.LocationListener;
 import android.location.LocationManager;
 import android.net.Uri;
 import android.os.Bundle;
 import android.provider.Settings;
 import android.view.View;
 import android.webkit.MimeTypeMap;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ImageView;
 import android.widget.ProgressBar;
 import android.widget.TextView;
 import android.widget.Toast;

 import androidx.appcompat.app.AlertDialog;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.app.ActivityCompat;
 import androidx.core.content.ContextCompat;

 import com.google.android.gms.tasks.OnSuccessListener;
 import com.google.android.gms.tasks.Task;
 import com.google.firebase.auth.FirebaseAuth;
 import com.google.firebase.auth.FirebaseUser;
 import com.google.firebase.database.DatabaseReference;
 import com.google.firebase.database.FirebaseDatabase;
 import com.google.firebase.storage.FirebaseStorage;
 import com.google.firebase.storage.StorageReference;
 import com.google.firebase.storage.StorageTask;
 import com.google.firebase.storage.UploadTask;
 import com.squareup.picasso.Picasso;

 import java.util.Calendar;
 import java.util.Date;
 import java.util.List;
 import java.util.Locale;

public class imageData extends AppCompatActivity implements LocationListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private final String process = "Pending";

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName, mInsuranceId;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    private Uri mImageUri;

    private FirebaseUser mAuth;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    private String mAddress = null;
    private String timestamp = null;
    private String latitude = null;
    private String longitude = null;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_data);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mInsuranceId = findViewById(R.id.InsuranceId);

        mAuth = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        Date currentTime = Calendar.getInstance().getTime();
        timestamp = java.text.DateFormat.getDateInstance(DateFormat.FULL).format(currentTime).toString();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(imageData.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
    }

    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        try {
            latitude = String.valueOf(location.getLatitude());
            longitude = String.valueOf(location.getLongitude());
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            mAddress = addresses.get(0).getAddressLine(0);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            mProgressBar.setVisibility(View.VISIBLE);

            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                    Uri downloadUrl = urlTask.getResult();

                    Toast.makeText(imageData.this, "Upload successful", Toast.LENGTH_LONG).show();
                    Upload upload = new Upload(mEditTextFileName.getText().toString().trim()+ " ",
                            downloadUrl.toString(),mAuth.getEmail(),mAddress,timestamp,process,mInsuranceId.getText().toString().trim()+ " ",latitude,longitude);
                    String uploadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadId).setValue(upload);
                    mProgressBar.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void openImagesActivity() {
        Intent intent = new Intent(imageData.this, ImagesActivity.class);
        startActivity(intent);
    }
}