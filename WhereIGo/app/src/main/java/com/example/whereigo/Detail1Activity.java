package com.example.whereigo;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.whereigo.recyclerview.Data;
import com.example.whereigo.recyclerview.MyRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Detail1Activity extends AppCompatActivity {

    TextView hash_disease;
    RecyclerView mRecyclerView;
    MyRecyclerAdapter mRecyclerAdapter;
    ArrayList<Data> mdataItems;

    FirebaseDatabase database2;
    DatabaseReference databaseReference2;

    public static Context context_detail1;
    public String part, disease;
    Map<String, Object> map2;
    List<String> list2;
    String[] items2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail1);


        //disease 받기
        Intent intent=getIntent();
        part=intent.getStringExtra("part");
        disease=intent.getStringExtra("disease");
        context_detail1=this; //전역변수로 사용

        //# 뒤에 증상 입력
        hash_disease=findViewById(R.id.hash_disease);
        hash_disease.setText(disease);

        //recyclerview list data 만들기
        mdataItems=new ArrayList<>();
        //recyclerview와 layout 연결
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerAdapter = new MyRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //recyclerview에 들어갈 data 넣기
        database2= FirebaseDatabase.getInstance();

        databaseReference2=database2.getReference();
        DatabaseReference Ref=databaseReference2.child(part).child(disease);
        Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                map2 = (Map<String,java.lang.Object>) dataSnapshot.getValue();
                list2 = new ArrayList<String>(map2.keySet());
                items2 = list2.stream().toArray(String[]::new);
                Log.d(TAG, "Value is1111111 " + list2);


                for (String item : list2){
                    mdataItems.add(new Data(item,item)); //disease(item), doctor_part 제대로 된 값으로,..
                }

                mRecyclerAdapter.setData(mdataItems);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}