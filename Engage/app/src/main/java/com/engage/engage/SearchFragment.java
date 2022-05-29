package com.engage.engage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class SearchFragment extends Fragment implements RecyclerViewInterface {
    RecyclerView favSongList,searchResultRecyclerView;
    ArrayList<SongModel> songModelArrayList=new ArrayList<>();
    SearchView searchView;
    FirebaseFirestore db;
    SendDataInterface sendDataInterface;
    TextView searchTextView;
    RecyclerView searchHistoryRecyclerView;
    LinearLayout categoryCardLayout;
    TextView punjabi,hindi,dance,sad,romance,workout;
    ArrayList<SongModel> queryListt=new ArrayList<>();
    SharedPreferences sharedPreferences;
    String mobileNum;

    public SearchFragment() {
        // Required empty public constructor
    }
    public interface SendDataInterface{
        public void sendData(String name,String link);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);
        searchView=view.findViewById(R.id.searchView);
        db=FirebaseFirestore.getInstance();
        searchTextView=view.findViewById(R.id.textView_searchHistory);
        searchHistoryRecyclerView=view.findViewById(R.id.searchHistory_recyclerView);
        categoryCardLayout=view.findViewById(R.id.category_cards);
        searchResultRecyclerView=view.findViewById(R.id.search_result_recyclerView);

        searchTextView.setVisibility(View.VISIBLE);
        searchHistoryRecyclerView.setVisibility(View.VISIBLE);
        categoryCardLayout.setVisibility(View.VISIBLE);
        searchResultRecyclerView.setVisibility(View.GONE);

        sad=view.findViewById(R.id.sad);
        workout=view.findViewById(R.id.workout);
        punjabi=view.findViewById(R.id.punjabi);
        hindi=view.findViewById(R.id.hindi);
        dance=view.findViewById(R.id.dance);
        romance=view.findViewById(R.id.romance);
        sharedPreferences=getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        mobileNum=sharedPreferences.getString("mobileNum",null);


       getHistory();

        GridLayoutManager gridLayoutManager=new GridLayoutManager(view.getContext(),2);
        searchResultRecyclerView.setLayoutManager(gridLayoutManager);


        sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","sad").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));

                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);


                        }
                    }
                });


            }
        });
        dance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","dance").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));
                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);


                        }
                    }
                });

            }
        });
        punjabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","punjabi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));
                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);

                        }
                    }
                });

            }
        });
        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","hindi").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));
                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);

                        }
                    }
                });

            }
        });
        romance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","romance").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));
                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);

                        }
                    }
                });

            }
        });
        workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Songs").whereArrayContains("Tags","workout").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful() ){
                            searchTextView.setVisibility(View.GONE);
                            searchHistoryRecyclerView.setVisibility(View.GONE);
                            categoryCardLayout.setVisibility(View.GONE);
                            searchResultRecyclerView.setVisibility(View.VISIBLE);
                            for(DocumentSnapshot snapshot:task.getResult()){
                                String link=snapshot.getString("link");
                                String image=snapshot.getString("image");
                                String name=snapshot.getString("name");
                                queryListt.add(new SongModel(image,link,name));
                            }
                            SongAdpater adpater=new SongAdpater(queryListt,SearchFragment.this::onItemClick);
                            adpater.notifyDataSetChanged();
                            searchResultRecyclerView.setAdapter(adpater);

                        }
                    }
                });

            }
        });





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               addToHistory(query);
                searchTextView.setVisibility(View.GONE);
                searchHistoryRecyclerView.setVisibility(View.GONE);
                categoryCardLayout.setVisibility(View.GONE);
                searchResultRecyclerView.setVisibility(View.VISIBLE);
                songModelArrayList.clear();
                String[] tags=query.toLowerCase().split(" ");
                for(String tag:tags){
                    tag.trim();
                    db.collection("Songs").whereArrayContains("Tags",tag.toLowerCase()).limit(20).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for(DocumentSnapshot snapshot:task.getResult()){
                                    String link=snapshot.getString("link");
                                    String image=snapshot.getString("image");
                                    String name=snapshot.getString("name");
                                    songModelArrayList.add(new SongModel(image,link,name));
                                }
                                SongAdpater songAdpater=new SongAdpater(songModelArrayList,SearchFragment.this::onItemClick);
                                songAdpater.notifyDataSetChanged();
                                searchResultRecyclerView.setAdapter(songAdpater);
                            }
                        }



                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return view;
    }

    @Override
    public void onItemClick(int position, String name, String link) {
        sendDataInterface.sendData(name,link);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity=(Activity)context;
        try{
            sendDataInterface=(SearchFragment.SendDataInterface)activity;
        }catch(RuntimeException re){
            throw new RuntimeException(activity.toString()+"Must Implement Method");
        }
    }
    void addToHistory(String s){
        if(mobileNum!=null){
            db.collection("USERS").document(mobileNum).collection("History").document("History").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot snapshot=task.getResult();
                        List<String> group = (List<String>) snapshot.get("history");
                        group.add(s);
                        Map<String,Object> data=new HashMap<>();
                        data.put("history",group);
                        db.collection("USERS").document("8901284370").collection("History").document("History").set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                            }
                        });
                    }



                }
            });
        }

    }
    void getHistory(){
        if(mobileNum!=null){
            db.collection("USERS").document(mobileNum).collection("History").document("History").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot snapshot=task.getResult();
                        List<String> group = (List<String>) snapshot.get("history");
                        GridLayoutManager gridLayoutManager1=new GridLayoutManager(getContext(),3);
                        searchHistoryRecyclerView.setLayoutManager(gridLayoutManager1);

                        HistoryAdapter historyAdapter=new HistoryAdapter(group);
                        historyAdapter.notifyDataSetChanged();
                        searchHistoryRecyclerView.setAdapter(historyAdapter);
                    }

                }

            });
        }

    }

}