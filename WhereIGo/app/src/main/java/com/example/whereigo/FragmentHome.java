package com.example.whereigo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements View.OnClickListener  {

    ImageButton head, neck, shoulder, stomache, leftarm, rightarm, leftleg, rightleg;
    ArrayList<String> mSelectedItems;
    AlertDialog.Builder builder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        head = view.findViewById(R.id.head);
        neck = view.findViewById(R.id.neck);
        shoulder = view.findViewById(R.id.shoulder);
        stomache = view.findViewById(R.id.stomache);
        leftarm = view.findViewById(R.id.leftarm);
        rightarm = view.findViewById(R.id.rightarm);
        leftleg = view.findViewById(R.id.leftleg);
        rightleg = view.findViewById(R.id.rightleg);

        head.setOnClickListener(this);
        neck.setOnClickListener(this);
        shoulder.setOnClickListener(this);
        stomache.setOnClickListener(this);
        leftarm.setOnClickListener(this);
        rightarm.setOnClickListener(this);
        leftleg.setOnClickListener(this);
        rightleg.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.head) {
            showDialog();
        }



    }

    public void showDialog() {
        mSelectedItems = new ArrayList<>();
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("증상을 선택하세요");

        builder.setMultiChoiceItems(R.array.disease, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                //데이터 리스트 담기
                String[] items = getResources().getStringArray(R.array.disease);

                if (isChecked) {
                    mSelectedItems.add(items[which]);
                } else if (mSelectedItems.contains(items[which])) {
                    mSelectedItems.remove(items[which]);
                }
            }

        });
        //ok 이벤트

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String final_selection = "";
                for (String item : mSelectedItems) {
                    final_selection = final_selection + "\n" + item;
                }
                Toast.makeText(getActivity().getApplicationContext(), "선택된 증상은" + final_selection, Toast.LENGTH_SHORT).show();
            }

        });
        //취소 이벤트
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

}