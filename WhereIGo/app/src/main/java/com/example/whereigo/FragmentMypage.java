package com.example.whereigo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class FragmentMypage extends Fragment {

    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_mypage, container, false);

        Button logoutBtn=(Button)rootView.findViewById(R.id.mypage_logout_btn);
        TextView tv_username=(TextView)rootView.findViewById(R.id.loginNickname);
        TextView tv_useremail=(TextView)rootView.findViewById(R.id.loginEmail);


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
        return rootView;
    }
}