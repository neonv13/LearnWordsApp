package com.example.learnwordsapp;

import static com.example.learnwordsapp.R.layout.activity_flashcards_main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class FlashcardsMainActivity extends AppCompatActivity {

    public static ArrayList<ModelClass> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_flashcards_main);

        list = new ArrayList<>();

        list.add (new ModelClass("Baza Danych","✓","✗","✓", "Database"));
        list.add (new ModelClass("Głośnik","✓","✗","✓", "Loud Speaker"));
        list.add (new ModelClass("Kopiować","✓","✗","✓", "Copy"));
        list.add (new ModelClass("Przeglądarka","✓","✗","✓", "Browser"));
        list.add (new ModelClass("Pilot","✓","✗","✓", "Remote control"));
        list.add (new ModelClass("Interaktywna Tablica","✓","✗","✓", "interactive whiteboard"));
        list.add (new ModelClass("Drukarka","✓","✗","✓", "Printer"));
        list.add (new ModelClass("Klawiatura","✓","✗","✓", "Keyboard"));
        list.add (new ModelClass("Pobierać","✓","✗","✓", "Downland"));
        list.add (new ModelClass("Myszka","✓","✗","✓", "Mouse"));
        list.add (new ModelClass("Ekran","✓","✗","✓", "Screen"));
        list.add (new ModelClass("Aparat cyfrowy","✓","✗","✓", "Digital camera"));
        list.add (new ModelClass("Ładować","✓","✗","✓", "Charge"));
        list.add (new ModelClass("Konsola gier","✓","✗","✓", "Games console"));
        list.add (new ModelClass("Sprzęt komputerowy","✓","✗","✓", "Hardware"));
        list.add (new ModelClass("Telefon komórkowy","✓","✗","✓", "Mobile phone"));
        list.add (new ModelClass("Kalkulator","✓","✗","✓", "Calculator"));
        list.add (new ModelClass("Komputer","✓","✗","✓", "Computer"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(FlashcardsMainActivity.this, FlashcardsDashboardActivity.class);
                startActivity(intent);
            }
        }, 1500);
    }
}