package com.example.learnwordsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.ViewHolder> {

    private List<Ranking> _elements;
    FirebaseDatabase database;
    DatabaseReference rankingTable;

    public RankingAdapter(List<Ranking> elements) {
        this._elements = elements;
    }

    @NonNull
    @Override
    public RankingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.ViewHolder holder, int position) {

        String txtUserName = _elements.get(position).getUserName();
        int score = _elements.get(position).getScore();

        holder.setData(txtUserName, score);
    }

    @Override
    public int getItemCount() {
        return _elements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView _txt_name;
        public TextView _txt_score;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            _txt_name = itemView.findViewById(R.id.txt_name);
            _txt_score = itemView.findViewById(R.id.txt_score);

        }

        public void setData(String txtUserName, int score) {

            Integer s = new Integer(score);
            _txt_name.setText(txtUserName);
            _txt_score.setText(s.toString());
        }
    }
}
