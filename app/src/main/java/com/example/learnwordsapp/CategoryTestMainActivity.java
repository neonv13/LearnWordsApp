package com.example.learnwordsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class CategoryTestMainActivity extends AppCompatActivity {

    public static ArrayList<Model> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_test_main);

        list = new ArrayList<>();

        list.add(new Model("The instructions that control what a computer does; computer programs", "Machine learing", "Machine code", "Software", "Hardware", "Software"));
        list.add(new Model("The physical and electronic parts of a computer, rather than the instructions it follows", "Software", "Hardware", "Embedded system", "Abandonware", "Hardware"));
        list.add(new Model("Computer software that is designed so that it can be used by a lot of different people or companies", "Software", "Hardware", "Packaged hardware", "Packaged software", "Packaged software"));
        list.add(new Model("The study of how to produce machines that have some of the qualities that the human mind has, such as the ability to understand language, recognize pictures, solve problems, and learn", "Artificial intellect", "Artificial intelligence", "Artificial quickness", "Artificial cleverness", "Artificial intelligence"));
        list.add(new Model("Internet protocol: the technical rules that control communication on the internet", "IP", "AP", "RP", "PP", "IP"));
        list.add(new Model("The service of keeping and managing websites on a server (= large central computer)", "Web service", "Web application", "Web hosting", "Web browser", "Web hosting"));
        list.add(new Model("A dot followed by three letters, such as .doc or .jpg, that forms the end of the name of a computer document", "Dot", "File extension", "Enlargement", "Amplification", "File extension"));
        list.add(new Model("The study of computers and how they can be us", "Computer science", "Informatic", "IT", "Science about society", "Computer science"));
        list.add(new Model("A small picture of an image or page on a computer screen", "Icon", "Cursor", "Thumbnail", "Mouse", "Thumbnail"));
        list.add(new Model("The practice of getting a large number of people to each give small amounts of money in order to provide the finance for a project, typically using the internet", "Crowdfunding", "Government funding", "Samecrunding", "Funding", "Crowdfunding"));
        list.add(new Model("He loves the latest version of his operating system as it has" + "  ＿  " + "new software ", "much", "a few", "lots of", "many", "lots of"));
        list.add(new Model("'Artifcial intelligence will never be more powerful than humar intekkigence.' The speaker is  ＿.", "advising", "giving an opinion", "saying he has doubts", "describing", "giving an opinion"));
        list.add(new Model("Some people feel more comfortable doing the work in Word first an then copying and  ＿  into Excel afterwards.", "pasting", "fastening", "fixing", "sticking", "pasting"));
        list.add(new Model("  ＿  has the air conditioning stopped working? I don't understand it.", "What", "Which", "Why", "When", "Why"));
        list.add(new Model("I couldn't stop laughing at the robot cat - it was really  ＿.", "amusing", "brief", "casual", "miserable", "amusing"));
        list.add(new Model("  ＿  a problem with the electronic display boards at the airport, several passengers were late for their flights.", "Therefore", "Due to", "Despite", "Because", "Due to"));
        list.add(new Model("'My screen isn't working again - I'll have to replace it.' 'Borrow one of mine. I've got two.' The second speaker is  ＿.", "offering help", "giving instructions", "asking for information", "giving advice", "offering help"));
        list.add(new Model("Now  ＿  return and the data will be saved", "post", "point", "press", "push", "press"));
        list.add(new Model("In hot countries, people need  ＿  to keep buildings cool.", "engies", "temperatures", "heaters", "air conditioning", "air conditioning"));
        list.add(new Model("I'm tired of my smartphone always restarting. The speaker is  ＿.", "amused", "annoyed", "suprised", "excited", "annoyed"));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent intent= new Intent(CategoryTestMainActivity.this, CategoryTestDashboardActivity.class);
                //startActivity(intent);
            }
        }, 1500);
    }
}