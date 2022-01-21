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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingFragment extends Fragment {

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    View RankingFragment;
    FirebaseDatabase database;
    DatabaseReference rankingTable;
    int sum = 0;
    List<Ranking> najlepsi;
    RankingAdapter ad;

    public RankingFragment() {
    }

    public static RankingFragment newInstance() {
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        rankingTable = database.getReference("/Ranking/najlepsi");
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

        layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        rankingList.setLayoutManager(layoutManager);

        database = FirebaseDatabase.getInstance();

        rankingTable.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                najlepsi = new ArrayList<>();
                if (snapshot.hasChildren()) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String user = data.getKey();
                        Object d = data.getValue();
                        String score = d.toString();
                        int scoreVal = Integer.valueOf(score);
                        Ranking local = new Ranking(user, scoreVal);
                        najlepsi.add(local);
                    }
                }
                if (najlepsi.size() <= 0) {//jeśli nie ma danych na liście
                    Ranking local = new Ranking("<NO DATA>", 0);
                    najlepsi.add(local);
                } else {//jeśli są dane
                    Collections.sort(najlepsi, new Comparator<Ranking>() {
                        @Override
                        public int compare(Ranking ranking, Ranking t1) {
                            return ranking.getScore() - t1.getScore();
                        }
                    });
                }

                ad = new RankingAdapter(najlepsi);
                rankingList.setAdapter(ad);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String e = error.toString();
                String m = error.getMessage();
                String d = error.getDetails();
            }
        });
    }
}