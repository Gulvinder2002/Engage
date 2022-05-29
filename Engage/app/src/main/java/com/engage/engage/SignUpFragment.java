package com.engage.engage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SignUpFragment extends Fragment {


    private TextInputEditText mobileNumber,password,confirmPassword;
    Button signUpBtn;
    private TextView alreadyHaveAnAccount;
    FirebaseFirestore dB;
    boolean choosed=false;
    SharedPreferences sharedPreferences;

    public SignUpFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAnAccount=view.findViewById(R.id.alreadyHaveAnAccount);
        mobileNumber=view.findViewById(R.id.signUpMobileNumber);
        password=view.findViewById(R.id.password_signUp);
        confirmPassword=view.findViewById(R.id.confirm_password_signUp);
        signUpBtn=view.findViewById(R.id.signUpBtn);
        dB=FirebaseFirestore.getInstance();
        sharedPreferences=getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_frame_layout,new SignInFragment()).commit();
            }
        });
        mobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        confirmPassword.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

    }
    void signUpUser(){
        Map<String,Object> newUserData=new HashMap<>();
        String mobileNum=mobileNumber.getText().toString().trim();
        String passwordNo=password.getText().toString().trim();
        String confirmPass=confirmPassword.getText().toString().trim();
        if(!choosed){
            if(mobileNum.length()==10){
                if(passwordNo.equals(confirmPass)){
                    newUserData.put("password",passwordNo);
                    newUserData.put("mobileNum",mobileNum);

                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("mobileNum",mobileNum);

                    choosed=true;
                    dB.collection("USERS").document(mobileNum).collection("Profile").add(newUserData).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(getContext(),"Profile Created Successfully",Toast.LENGTH_LONG).show();
                            editor.putString("mobileNum",mobileNum);
                            editor.apply();
                            Map<String,Object> data=new HashMap<>();
                            List<String> strings=new ArrayList<>();

                            data.put("history",strings);
                            Map<String,Object> data1=new HashMap<>();

                            dB.collection("USERS").document(mobileNum).collection("History").document("History").set(data);
                            dB.collection("USERS").document(mobileNum).collection("PlayHistory").add(data1);
                            getFragmentManager().beginTransaction().replace(R.id.register_frame_layout,new SignInFragment()).commit();
                        }
                    });
                }else{
                    Toast.makeText(getContext(),"Enter the same password in both fields",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getContext(),"Enter Valid Mobile Number",Toast.LENGTH_SHORT).show();
            }

        }
    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(mobileNumber.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                if(!TextUtils.isEmpty(confirmPassword.getText())){
                    signUpBtn.setEnabled(true);
                    signUpBtn.setTextColor(getResources().getColor(R.color.white));
                }else{
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50,255,255,255));
                }
            }else{
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50,255,255,255));
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50,255,255,255));
        }

    }
}