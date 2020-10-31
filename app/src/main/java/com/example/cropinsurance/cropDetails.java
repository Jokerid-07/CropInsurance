package com.example.cropinsurance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cropinsurance.Apple.Apple;

public class cropDetails extends AppCompatActivity {
    GridView gridView;

    String[] fruitNames = {"Apple"};
    int[] fruitImages = {R.drawable.apple};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        gridView = findViewById(R.id.grid);

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch(position){
                        case 0:
                            Intent a = new Intent(view.getContext(), Apple.class);
                            view.getContext().startActivity(a);
                            finish();
                            break;
                        default:
                            break;
                    }

            }

        });
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),dashboard.class));
        finish();
    }

    private class CustomAdapter extends BaseAdapter {
            @Override
            public int getCount() {
                return fruitImages.length;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                view = getLayoutInflater().inflate(R.layout.row_data, null);
                //getting view in row_data
                TextView name = view.findViewById(R.id.text);
                ImageView image = view.findViewById(R.id.image);
                name.setText(fruitNames[i]);
                image.setImageResource(fruitImages[i]);
                return view;
            }
    }
}