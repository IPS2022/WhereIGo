package com.example.whereigo;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentHome extends Fragment implements View.OnClickListener  {

    ImageButton head, neck, shoulder, stomache, stomache2,stomache3,leftarm, rightarm, leftleg, rightleg;
    ArrayList<String> mSelectedItems;
    AlertDialog.Builder builder;

    //database
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    Map<String, Object> map;
    List<String> list;
    String[] items;
    String searchpart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        head = view.findViewById(R.id.head);
        neck = view.findViewById(R.id.neck);
        shoulder = view.findViewById(R.id.shoulder);
        stomache = view.findViewById(R.id.stomache);
        stomache2=view.findViewById(R.id.stomache2);
        stomache3=view.findViewById(R.id.stomache3);
        leftarm = view.findViewById(R.id.leftarm);
        rightarm = view.findViewById(R.id.rightarm);
        leftleg = view.findViewById(R.id.leftleg);
        rightleg = view.findViewById(R.id.rightleg);

        head.setOnClickListener(this);
        neck.setOnClickListener(this);
        shoulder.setOnClickListener(this);
        stomache.setOnClickListener(this);
        stomache2.setOnClickListener(this);
        stomache3.setOnClickListener(this);
        leftarm.setOnClickListener(this);
        rightarm.setOnClickListener(this);
        leftleg.setOnClickListener(this);
        rightleg.setOnClickListener(this);


        //database
        database=FirebaseDatabase.getInstance();

        return view;
    }


    public void startdatabaseEvent(String part){
        databaseReference=database.getReference(part);
        searchpart=part;
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map = (Map<String,java.lang.Object>) dataSnapshot.getValue();
                list = new ArrayList<String>(map.keySet());
                items = list.stream().toArray(String[]::new);
                Log.d(TAG, "Value is: " + list);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.head) {
            startdatabaseEvent("머리");
        }else if (view.getId() == R.id.neck){
            startdatabaseEvent("목");
        }else if (view.getId() == R.id.shoulder){
            startdatabaseEvent("가슴");
        }else if (view.getId() == R.id.stomache){
            startdatabaseEvent("배");
        }else if (view.getId() == R.id.stomache2){
            startdatabaseEvent("배");
        }else if (view.getId() == R.id.stomache3){
            startdatabaseEvent("배");
        }else if (view.getId() == R.id.leftarm){
            startdatabaseEvent("팔");
        }else if (view.getId() == R.id.rightarm){
            startdatabaseEvent("팔");
        }else if (view.getId() == R.id.leftleg){
            startdatabaseEvent("다리");
        }else if (view.getId() == R.id.rightleg){
            startdatabaseEvent("다리");
        }
        beforeshowDialog();

    }

    public void beforeshowDialog(){
        ProgressDialog TempDialog;
        CountDownTimer mCountDownTimer;
        int i=0;

        TempDialog = new ProgressDialog(getActivity());
        TempDialog.setMessage("Please wait...");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        TempDialog.show();
        mCountDownTimer = new CountDownTimer(2000, 1000)
        {
            public void onTick(long millisUntilFinished)
            {
                TempDialog.setMessage("Please wait..");
            }

            public void onFinish()
            {
                TempDialog.dismiss();
                //database 찐
                showDialog();

            }
        }.start();
    }

    public void showDialog() {
        mSelectedItems = new ArrayList<>();

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("증상을 선택하세요");

        builder.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
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
                    final_selection = final_selection+"\n"+item;
                }
                Toast.makeText(getActivity().getApplicationContext(), "선택된 증상은" + final_selection, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(),Detail1Activity.class);
                intent.putExtra("part",searchpart);
                String diseases=final_selection.trim();
                intent.putExtra("disease",diseases);
                startActivity(intent);
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