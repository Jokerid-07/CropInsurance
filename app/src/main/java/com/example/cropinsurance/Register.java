package com.example.cropinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cropinsurance.Admin.adminDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText fullName,email,password,phone,cpassword,admin;
    Button registerBtn;
    TextView goToLogin, policy;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    CheckBox isAdminBox, isUserBox, terms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fullName = findViewById(R.id.Name);
        policy = findViewById(R.id.privacy);
        terms = findViewById(R.id.policy);
        cpassword = findViewById(R.id.password2);
        admin = findViewById(R.id.adminid);

        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        registerBtn = findViewById(R.id.registerBtn);
        goToLogin = findViewById(R.id.createText);

        isAdminBox = findViewById(R.id.isAdmin);
        isUserBox = findViewById(R.id.isUser);

        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,progress.class));
            }
        });

        isUserBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked()){
                    isAdminBox.setChecked(false);
                    admin.setVisibility(View.INVISIBLE);
                    policy.setVisibility(View.VISIBLE);
                    terms.setVisibility(View.VISIBLE);
                }
            }
        });

        isAdminBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked()){
                    isUserBox.setChecked(false);
                    admin.setVisibility(View.VISIBLE);
                    policy.setVisibility(View.INVISIBLE);
                    terms.setVisibility(View.INVISIBLE);
                }
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                checkField(phone);
                checkField(cpassword);

                if (!(isAdminBox.isChecked() || isUserBox.isChecked())) {
                    Toast.makeText(Register.this, "Select The Account Type.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isUserBox.isChecked()) {
                    if(terms.isChecked()){
                        auth();
                        startActivity(new Intent(getApplicationContext(), dashboard.class));
                        finish();
                    }
                    else {
                        Toast.makeText(Register.this, "Accept Terms & Condition", Toast.LENGTH_SHORT).show();
                    }
                }
                if (isAdminBox.isChecked()) {
                    checkField(admin);
                    String tAdmin = admin.getText().toString();
                    if(tAdmin.equals("1732J06")) {
                        auth();
                        startActivity(new Intent(getApplicationContext(), adminDashboard.class));
                        finish();
                    }
                    else {
                        Toast.makeText(Register.this, "Type Proper Admin Id", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }
        return valid;
    }

    public void auth() {
        String tPassword = password.getText().toString();
        String tCPassword = cpassword.getText().toString();
        if (tPassword.equals(tCPassword)) {
            if (valid) {
                fAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = fAuth.getCurrentUser();
                        Toast.makeText(Register.this, "Account Created", Toast.LENGTH_SHORT).show();
                        DocumentReference df = fStore.collection("Users").document(user.getUid());
                        Map<String, Object> userInfo = new HashMap<>();
                        userInfo.put("FullName", fullName.getText().toString());
                        userInfo.put("UserEmail", email.getText().toString());
                        userInfo.put("PhoneNumber", phone.getText().toString());

                        if (isAdminBox.isChecked()) {
                            userInfo.put("isAdmin", "1");
                        }
                        if (isUserBox.isChecked()) {
                            userInfo.put("isUser", "1");
                        }

                        df.set(userInfo);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(Register.this, "Password Mismatch", Toast.LENGTH_SHORT).show();

        }
    }
}