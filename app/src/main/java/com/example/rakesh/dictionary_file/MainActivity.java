package com.example.rakesh.dictionary_file;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private HashMap<String,String> dictionary;
    private ArrayList<String> fword;
    private ArrayList<String> sixword;
    private ArrayList<String> sixdefn;
    private String displaytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fword = new ArrayList<>();
        dictionary = new HashMap<>();
        Scanner scan = new Scanner(getResources().openRawResource(R.raw.grewords2));
        while(scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] word = line.split("\t");
            if (word.length >= 2) {
                fword.add(word[0]);
                dictionary.put(word[0], word[1]);
            }
        }
        wordgenerator();
    }
        public void wordgenerator() {
            sixword = new ArrayList<>();
            sixdefn = new ArrayList<>();
            Collections.shuffle(fword);
            for (int i = 0; i < 6; i++) {
                sixword.add(fword.get(i));
                sixdefn.add(dictionary.get(fword.get(i)));
            }
            Collections.shuffle(sixdefn);
            displaytext = sixword.get(0);
            resetter();
        }
            public void resetter() {
                TextView showtext = (TextView) findViewById(R.id.text_view);
                showtext.setText(displaytext);
                ListView list = (ListView) findViewById(R.id.list_view);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        sixdefn
                );
                list.setAdapter(adapter);
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String defnclicked = adapterView.getItemAtPosition(i).toString();
                        String defin = dictionary.get(displaytext);
                        if (defin == defnclicked) {
                            Toast.makeText(MainActivity.this, "Awesome", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Oops\n Right answer is : " + defin, Toast.LENGTH_SHORT).show();
                        }
                        wordgenerator();
                    }
                });
            }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("sixword",sixword);
        outState.putStringArrayList("sixdefn",sixdefn);
        outState.putString("displaytext",displaytext);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey("sixword")&&savedInstanceState.containsKey("sixdefn")&&savedInstanceState.containsKey("displaytext"))
        {
            sixword = savedInstanceState.getStringArrayList("sixword");
            sixdefn = savedInstanceState.getStringArrayList("sixdefn");
            displaytext = savedInstanceState.getString("displaytext");
            resetter();
        }

    }
}
