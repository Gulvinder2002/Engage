package com.engage.engage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PlayFragment.SendDataInterface, SearchFragment.SendDataInterface {

    JcPlayerView jcplayerView;
    FirebaseFirestore db;
    SharedPreferences sharedPreferences;
    String mobileNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jcplayerView=findViewById(R.id.jcplayer);
        db=FirebaseFirestore.getInstance();

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationListner);
         sharedPreferences=getSharedPreferences("user_data", Context.MODE_PRIVATE);
         mobileNum=sharedPreferences.getString("mobileNum",null);


        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,new PlayFragment()).commit();



    }

    @Override
    public void sendData(String name, String link) {
        ArrayList<JcAudio> jcAudios = new ArrayList<>();
        jcAudios.add(JcAudio.createFromURL(name,link));
        jcplayerView.initPlaylist(jcAudios, null);
        jcplayerView.createNotification();
        jcplayerView.playAudio(JcAudio.createFromURL(name,link));
        Map<String,Object> data=new HashMap<>();
        data.put("name",name);
        data.put("link",link);
        data.put("image","https://firebasestorage.googleapis.com/v0/b/engage-a2dd2.appspot.com/o/Images%2FGroup%201.png?alt=media&token=d144b946-9a81-4cc3-be5c-93e5d87e520f");

        db.collection("USERS").document(mobileNum).collection("PlayHistory").whereEqualTo("name",name).whereEqualTo("link",link).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().isEmpty()){
                    db.collection("USERS").document(mobileNum).collection("PlayHistory").add(data);
                }
            }
        });


    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationListner=new BottomNavigationView.OnNavigationItemSelectedListener(){

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment=null;
            switch(item.getItemId()){
                case R.id.play_song:
                    selectedFragment= new PlayFragment();
                    jcplayerView.setVisibility(View.VISIBLE);
                    break;
                case R.id.search_song:
                    selectedFragment= new SearchFragment();
                    jcplayerView.setVisibility(View.VISIBLE);
                    break;
//                case R.id.profile:
//                    selectedFragment= new ProfileFragment();
//                    jcplayerView.setVisibility(View.GONE);
//                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,selectedFragment).commit();
            return true;
        }
    };

}