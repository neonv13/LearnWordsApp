package com.example.learnwordsapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class HomeMenuFragment extends Fragment {

    View root;

    LinearLayout quizBtn;
    LinearLayout flashcardsBtn;
    LinearLayout completeBtn;

    public HomeMenuFragment() {
        // Required empty public constructor
    }

    public static HomeMenuFragment newInstance() {
        HomeMenuFragment fragment = new HomeMenuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home_menu, container, false);
        quizBtn = root.findViewById(R.id.QuizBtn);
        flashcardsBtn = root.findViewById(R.id.FlashcardsBtn);
        completeBtn = root.findViewById(R.id.CompleteBtn);
        //nieskończone jeszcze
        quizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), QuizMainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        flashcardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), FlashcardsMainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getActivity(), SentenceMainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }
}