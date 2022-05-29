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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class SignInFragment extends Fragment {

    private TextInputEditText mobileNumber,password;
    Button logInBtn;
    private TextView dontHaveAnAccount;
    FirebaseFirestore dB;
    boolean choosed=false;
    SharedPreferences sharedPreferences;

    public SignInFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount=view.findViewById(R.id.do_not_have_an_account);
        mobileNumber=view.findViewById(R.id.mobileNumber);
        password=view.findViewById(R.id.password_signIn);
        logInBtn=view.findViewById(R.id.logInBtn);
        dB=FirebaseFirestore.getInstance();
         sharedPreferences=getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        String mobileNumber=sharedPreferences.getString("mobileNum",null);
        if(mobileNumber!=null){
            Intent intent=new Intent(getContext(),MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.register_frame_layout,new SignUpFragment()).commit();
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
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmLogin();
            }
        });

    }
    void confirmLogin(){
        String mobileNum=mobileNumber.getText().toString().trim();
        String passwordNo=password.getText().toString().trim();
        if(!choosed){
            choosed=true;
            dB.collection("USERS").document(mobileNum).collection("Profile").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(!task.getResult().isEmpty()){
                        if(task.isSuccessful()){
                            for(DocumentSnapshot snapshot:task.getResult()) {
                                String passWordStored = snapshot.getString("password");
                                if(passwordNo.equals(passWordStored)){
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("mobileNum",mobileNum);
                                    editor.apply();
                                    Intent intent=new Intent(getContext(),MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }else{
                                    Toast.makeText(getContext(),"Wrong Password Entered",Toast.LENGTH_SHORT).show();
                                    choosed=false;
                                }
                            }
                        }else{
                            Toast.makeText(getContext(),"Error in Log In",Toast.LENGTH_SHORT).show();
                            choosed=false;
                        }
                    }else{
                        Toast.makeText(getContext(),"User Not Registered",Toast.LENGTH_SHORT).show();
                        choosed=false;
                    }
                }
            });
        }
    }
    private void checkInputs(){
        if(!TextUtils.isEmpty(mobileNumber.getText())){
            if(!TextUtils.isEmpty(password.getText())){
                logInBtn.setEnabled(true);
                logInBtn.setTextColor(getResources().getColor(R.color.white));

            }else{
                logInBtn.setEnabled(false);
                logInBtn.setTextColor(Color.argb(50,255,255,255));
            }
        } else {
            logInBtn.setEnabled(false);
            logInBtn.setTextColor(Color.argb(50,255,255,255));
        }

    }
}