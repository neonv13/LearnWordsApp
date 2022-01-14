package com.example.learnwordsapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment {

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    View RankingFragment;
    FirebaseDatabase database;
    DatabaseReference rankingTable;
    int sum=0;
    List<Ranking> najlepsi;

    public RankingFragment() {
    }

    public static RankingFragment newInstance() {
        RankingFragment rankingFragment=new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        rankingTable=database.getReference("/Ranking/najlepsi");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RankingFragment = inflater.inflate(R.layout.ranking_fragment, container, false);
        //setData();
        initRecyclerView();
        return RankingFragment;
    }

   /* private void setData() {
        najlepsi = new ArrayList<Ranking>();
        najlepsi.add(new Ranking("Janek", 30));
        najlepsi.add(new Ranking("Walek", 29));
        najlepsi.add(new Ranking("Jurek", 28));
        najlepsi.add(new Ranking("Ziuta", 20));

    }*/


    private void initRecyclerView() {
        rankingList = RankingFragment.findViewById(R.id.rankingList);
        rankingList.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rankingList.setLayoutManager(layoutManager);
        rankingTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                najlepsi=new ArrayList<>();
                if(snapshot.hasChildren()){
                    for(DataSnapshot data:snapshot.getChildren()){
                        String k=data.getKey();
                        Object d =  data.getValue();
                        String s = d.toString();
                        int v= Integer.valueOf(s);
                        Ranking local=new Ranking(k,v);
                        najlepsi.add(local);
                    }
                }
                RankingAdapter ad = new RankingAdapter(najlepsi);
                rankingList.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String e=error.toString();
                String m= error.getMessage();
                String d= error.getDetails();
            }
        });


        //  ad.notifyDataSetChanged();

    }




}