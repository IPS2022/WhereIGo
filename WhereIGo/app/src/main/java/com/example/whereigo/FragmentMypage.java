package com.example.whereigo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FragmentMypage extends Fragment {
    public String fname=null;
    public String str=null;
    public String readDay = null;
    private FirebaseAuth mAuth;
    public CalendarView calendarView;
    public Button cha_Btn,del_Btn,save_Btn;
    public TextView diaryTextView,textView22,textView3;
    public EditText contextEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);
        //super.onCreate(savedInstanceState);
        Button logoutBtn = (Button) rootView.findViewById(R.id.mypage_logout_btn);
        TextView tv_username = (TextView) rootView.findViewById(R.id.loginNickname);
        TextView tv_useremail = (TextView) rootView.findViewById(R.id.loginEmail);
        calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        diaryTextView = (TextView) rootView.findViewById(R.id.diaryTextView);
        save_Btn = (Button) rootView.findViewById(R.id.save_Btn);
        del_Btn = (Button) rootView.findViewById(R.id.del_Btn);
        cha_Btn = (Button) rootView.findViewById(R.id.cha_Btn);
        textView22 = (TextView) rootView.findViewById(R.id.textView22);
        contextEditText = (EditText) rootView.findViewById(R.id.contextEditText);


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "sign out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=mAuth.getCurrentUser();
        tv_username.setText(user.getDisplayName());
        tv_useremail.setText(user.getEmail());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //@SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
               // textView22.setVisibility(View.INVISIBLE);
               // cha_Btn.setVisibility(View.INVISIBLE);
               // del_Btn.setVisibility(View.INVISIBLE);
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                checkDay(year, month, dayOfMonth);
                //checkDay(year,month,dayOfMonth,tv_username);
            }
        });
        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str = contextEditText.getText().toString();
                textView22.setText(str);
                //save_Btn.setVisibility(View.INVISIBLE);
                cha_Btn.setVisibility(View.VISIBLE);
                del_Btn.setVisibility(View.VISIBLE);
                //contextEditText.setVisibility(View.INVISIBLE);
                textView22.setVisibility(View.VISIBLE);

            }
        });

        return rootView;
    }

    public void checkDay(int year, int month, int dayOfMonth) {
        fname=""+year+"-"+(month+1)+""+"-"+dayOfMonth+".txt";//저장할 파일 이름설정
        FileInputStream fis=null;//FileStream fis 변수
        try{
            fis = getContext().openFileInput(fname);

            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();
            str=new String(fileData);

            //contextEditText.setVisibility(View.INVISIBLE);
            textView22.setVisibility(View.VISIBLE);
            textView22.setText(str);

            //save_Btn.setVisibility(View.INVISIBLE);
            cha_Btn.setVisibility(View.VISIBLE);
            del_Btn.setVisibility(View.VISIBLE);

            cha_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    contextEditText.setVisibility(View.VISIBLE);
                   // textView22.setVisibility(View.INVISIBLE);
                    contextEditText.setText(str);

                    save_Btn.setVisibility(View.VISIBLE);
                    //cha_Btn.setVisibility(View.INVISIBLE);
                  //  del_Btn.setVisibility(View.INVISIBLE);
                    textView22.setText(contextEditText.getText());
                }

            });
            del_Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // textView22.setVisibility(View.INVISIBLE);
                    contextEditText.setText("");
                    contextEditText.setVisibility(View.VISIBLE);
                    save_Btn.setVisibility(View.VISIBLE);
                  //  cha_Btn.setVisibility(View.INVISIBLE);
                   // del_Btn.setVisibility(View.INVISIBLE);
                    removeDiary(fname);
                }
            });
            if(textView22.getText()==null){
                //textView22.setVisibility(View.INVISIBLE);
                diaryTextView.setVisibility(View.VISIBLE);
                save_Btn.setVisibility(View.VISIBLE);
               // cha_Btn.setVisibility(View.INVISIBLE);
               // del_Btn.setVisibility(View.INVISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay){
        FileOutputStream fos;

        try{
            fos = getContext().openFileOutput(readDay, Context.MODE_PRIVATE);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay){
        FileOutputStream fos;

        try{
            fos = getContext().openFileOutput(readDay, Context.MODE_PRIVATE);
            String content=contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}

