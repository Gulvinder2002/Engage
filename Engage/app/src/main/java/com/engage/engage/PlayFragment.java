package com.engage.engage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class PlayFragment extends Fragment implements RecyclerViewInterface {


    FirebaseFirestore dB;
    ArrayList<SongModel> songList;
    ViewPager2 viewPager2;
    RecyclerView mostStreamedSongsRecyclerView,trendingRecyclerView;
    ArrayList<SongModel> songModelArrayList=new ArrayList<>();
    ArrayList<SongModel> trendingList=new ArrayList<>();
    FirebaseFirestore db;
    SendDataInterface sendDataInterface;
    SharedPreferences sharedPreferences;
    String mobileNum;
    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onItemClick(int position, String name, String link) {
        sendDataInterface.sendData(name,link);
    }

    public interface SendDataInterface{
        public void sendData(String name,String link);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_play, container, false);
        mostStreamedSongsRecyclerView=view.findViewById(R.id.streamed_recyclerView);
        trendingRecyclerView=view.findViewById(R.id.trending_recyclerView);

        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(view.getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        db=FirebaseFirestore.getInstance();

        mostStreamedSongsRecyclerView.setLayoutManager(layoutManager);
        trendingRecyclerView.setLayoutManager(layoutManager1);
        sharedPreferences=getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        mobileNum=sharedPreferences.getString("mobileNum",null);

        db.collection("USERS").document(mobileNum).collection("PlayHistory").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.getResult().size()!=0){
                    if(task.isSuccessful()){
                        for(DocumentSnapshot snapshot:task.getResult()){
                            songModelArrayList.add(new SongModel(snapshot.getString("image"),snapshot.getString("link"),snapshot.getString("name")));
                        }
                        SongAdpater songAdpater =new SongAdpater(songModelArrayList, PlayFragment.this::onItemClick);
                        songAdpater.notifyDataSetChanged();
                        mostStreamedSongsRecyclerView.setAdapter(songAdpater);
                    }
                }
            }
        });



        db.collection("Trending").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot snapshot:task.getResult()){
                        String name=snapshot.getString("name");
                        String link=snapshot.getString("link");
                        String image=snapshot.getString("image");

                        trendingList.add(new SongModel(image,link,name));
                    }
                    SongAdpater songAdpater1 =new SongAdpater(trendingList,PlayFragment.this::onItemClick );
                    songAdpater1.notifyDataSetChanged();
                    trendingRecyclerView.setAdapter(songAdpater1);
                }
            }


        });
//        db.collection("Most_Streamed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if(task.isSuccessful()){
//                    for(DocumentSnapshot snapshot:task.getResult()){
//                        String name=snapshot.getString("name");
//                        String link=snapshot.getString("link");
//                        String image=snapshot.getString("image");
//
//                        songModelArrayList.add(new SongModel(image,link,name));
//                    }
//
//                }
//            }
//
//
//        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try{
            sendDataInterface=(SendDataInterface)activity;
        }catch(RuntimeException re){
            throw new RuntimeException(activity.toString()+"Must Implement Method");
        }
    }

}