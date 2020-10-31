package com.example.cropinsurance.Apple;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.cropinsurance.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link appleAttack#newInstance} factory method to
 * create an instance of this fragment.
 */
public class appleAttack extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public appleAttack() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment appleAttack.
     */
    // TODO: Rename and change types and number of parameters
    public static appleAttack newInstance(String param1, String param2) {
        appleAttack fragment = new appleAttack();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myfragment = inflater.inflate(R.layout.fragment_apple_attack, container, false);

        LinearLayout aphids = (LinearLayout) myfragment.findViewById(R.id.aphids);
        aphids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), aphids.class);
                startActivity(intent);
            }
        });

        LinearLayout scab = (LinearLayout) myfragment.findViewById(R.id.applescab);
        scab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), scab.class);
                startActivity(intent);
            }
        });

        LinearLayout maggot = (LinearLayout) myfragment.findViewById(R.id.applemaggot);
        maggot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), maggot.class);
                startActivity(intent);
            }
        });

        LinearLayout rot = (LinearLayout) myfragment.findViewById(R.id.blackrot);
        rot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), rot.class);
                startActivity(intent);
            }
        });

        LinearLayout moth = (LinearLayout) myfragment.findViewById(R.id.codlingmoth);
        moth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), moth.class);
                startActivity(intent);
            }
        });
        return myfragment;
    }
}