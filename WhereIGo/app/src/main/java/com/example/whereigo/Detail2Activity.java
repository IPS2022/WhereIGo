package com.example.whereigo;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Detail2Activity extends AppCompatActivity {
    private FragmentSearch fragmentSearch = new FragmentSearch();
    TextView diseasename;
    TextView content11,content12,content13,content14,content15;

    FirebaseDatabase database4;
    DatabaseReference databaseReference4;
    String content1,content2,content3,content4,content5; //원인, 정의, 증상, 진료과,질병이름,치료

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);


        //disease 받기
        Intent intent=getIntent();
        String detail_disease=intent.getStringExtra("detail_disease");

        //질병명 입력
        diseasename=findViewById(R.id.detail_disease);
        diseasename.setText(detail_disease);

        content14=findViewById(R.id.detail_doctor); //진료과
        content12=findViewById(R.id.detail_disease_content1); //정의
        content11=findViewById(R.id.detail_disease_content2); //원인
        content13=findViewById(R.id.detail_disease_content3) ;//증상
        content15=findViewById(R.id.detail_disease_content4); //치료


        //database
        database4= FirebaseDatabase.getInstance();
        String part1=((Detail1Activity)Detail1Activity.context_detail1).part;
        String disease1=((Detail1Activity)Detail1Activity.context_detail1).disease;

        databaseReference4=database4.getReference();
        DatabaseReference newRef=databaseReference4.child(part1).child(disease1).child(detail_disease);
        newRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                content1=dataSnapshot.child("원인").getValue().toString();
                content2=dataSnapshot.child("정의").getValue().toString();
                content3=dataSnapshot.child("증상").getValue().toString();
                content4=dataSnapshot.child("진료과").getValue().toString();
                content5=dataSnapshot.child("치료").getValue().toString();

                //내용 연결
                content11.setText(content1);
                content12.setText(content2);
                content13.setText(content3);
                content14.setText(content4);
                content15.setText(content5);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

       /* Button mapButton = (Button) findViewById(R.id.gotomap);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.google_map);
               // Intent intent = new Intent(getApplicationContext(), FragmentSearch.class);
                //startActivity(intent);
            }
        });*/
        Button mapButton = (Button) findViewById(R.id.gotomap);
        mapButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.replace(R.id.menu_frame_layout,fragmentSearch ).commit();
                //(MainActivity)getActivity().ItemSelectedListener(R.id.menu_search);
                /*class ItemSelectedListener {

                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        transaction.replace(R.id.menu_frame_layout, new FragmentSearch()).commitAllowingStateLoss();
                        return true;
                    }
                }*/
                //getSupportFragmentManager().findFragmentById(R.id.google_map);
                // Intent intent = new Intent(getApplicationContext(), FragmentSearch.class);
                //startActivity(intent);
            }
        });

    }


}