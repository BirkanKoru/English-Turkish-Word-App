package com.example.wordsapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

public class wordQuizFragment extends Fragment {

    private TextView txtWord;
    private ListView listAnswers;
    private TextView txtScore;
    private TextView txtQuestNum;

    private TreeMap<String, String> dictionary=new TreeMap<String,String>();
    private fileOperations fo;

    private int score=0;
    private int leftQuest = 10;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.wordquiz_fragment, container, false);

        txtWord = (TextView) view.findViewById(R.id.txtWord);
        listAnswers = (ListView) view.findViewById(R.id.listAnswers);
        txtScore = (TextView) view.findViewById(R.id.txtScore);
        txtQuestNum = (TextView) view.findViewById(R.id.txtQuestNum);
        txtScore.setText("Score:"+score);
        txtQuestNum.setText("Left:"+leftQuest);

        //File Operations Class
        fo = new fileOperations();

        //Read Words.txt file (True means: Words.txt file)
        dictionary = fo.Load(true);

        try{
            if(dictionary.size() >= 15){
                chooseWord();
            } else if(dictionary.size() < 15){
                throw new Exception();
            }
        } catch (Exception e){
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setMessage("Please, add at least 15 word !!");
            alert.show();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentById(R.id.content_frame);
            if(prev != null){
                ft.remove(prev);
            }
            ft.add(R.id.content_frame, new addWordFragment()).commit();
        }


        listAnswers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String defnClicked = parent.getItemAtPosition(position).toString();
                String wordEnglish = txtWord.getText().toString().trim();
                String wordTurkish = dictionary.get(wordEnglish);

                if(defnClicked.equals(wordTurkish)){
                    Toast.makeText(getActivity(), "That's right :)", Toast.LENGTH_SHORT).show();
                    score+= 10;
                }else{
                    Toast.makeText(getActivity(), "Nooo, that's not true :(", Toast.LENGTH_SHORT).show();
                }

                leftQuest--;
                txtScore.setText("Score:"+score);
                txtQuestNum.setText("Left:"+leftQuest);

                if(leftQuest > 0)
                {
                    if(dictionary.size() > 5){
                        //Blocking the same word from coming.
                        dictionary.remove(wordEnglish);

                        chooseWord();

                    } else if(dictionary.size() <= 5){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment prev = getFragmentManager().findFragmentById(R.id.content_frame);

                        if(prev != null){
                            ft.remove(prev);
                        }

                        ft.add(R.id.content_frame, new addWordFragment()).commit();
                    }
                }else{

                    //GAME OVER
                    txtQuestNum.setTextColor(getResources().getColor(R.color.red));
                    txtQuestNum.setText("Game finished.");
                    listAnswers.setEnabled(false);
                    listAnswers.setOnItemClickListener(null);
                }

            }
        });

        return view;
    }


    private void chooseWord(){

        Random rand = new Random();
        List<String> keys = new ArrayList<String>(dictionary.keySet());
        String wordEnglish = keys.get(rand.nextInt(keys.size()));
        String wordTurkish = dictionary.get(wordEnglish);

        List<String> defns = new ArrayList<String>(dictionary.values());
        defns.remove(wordTurkish);
        Collections.shuffle(defns);
        defns = defns.subList(0, 4);
        defns.add(wordTurkish);
        Collections.shuffle(defns);

        txtWord.setText(wordEnglish);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                new ArrayList<String>(defns)
        );
        listAnswers.setAdapter(adapter);
    }
}
