package com.example.cropinsurance;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropinsurance.API.JsonPlaceHolderApi;
import com.example.cropinsurance.API.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsuranceDetails extends AppCompatActivity {
    private TextView textViewResult;
    private EditText edittext;
    private Button button;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        textViewResult = findViewById(R.id.text_view_result);
        edittext = findViewById(R.id.editText);
        button =  findViewById(R.id.data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edittext.getText().toString())){
                    Toast.makeText(InsuranceDetails.this,"Insurance Id Required",Toast.LENGTH_SHORT).show();
                }
                else{
                    getData();
                }
            }
        });

    }

    private void getData() {
        textViewResult.setText("");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/Jokerid-07/CropData/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts) {
                    id = edittext.getText().toString();
                    if (id.equals(post.getId())) {
                        String content = "";
                        content += "ID : " + post.getId() + "\n";
                        content += "Name : " + post.getName() + "\n";
                        content += "Email Id : " + post.getEmail() + "\n";
                        content += "Address : " + post.getAddress() + "\n";
                        content += "Phone Number : " + post.getPhone() + "\n";
                        content += "Land (In Square Feet): " + post.getLand() + "\n";
                        content += "Soil Type : " + post.getSoil() + "\n";
                        content += "Crop Detail : " + post.getCrop() + "\n";
                        content += "Loan Amount : " + post.getLoanAmount() + "\n";
                        content += "Insurance Amount : " + post.getInsuranceAmount() + "\n";
                        content += "Start Date : " + post.getLoanStartDate() + "\n";
                        content += "End Date : " + post.getLoanEndData() + "\n";
                        content += "Validity : " + post.getLoanValidity() + "\n";

                        textViewResult.append(content);
                    }
                    else{
                        Toast.makeText(InsuranceDetails.this,"Invalid Insurance id",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}