package com.example.wordsapp;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class addWordFragment extends Fragment {
    private EditText wordEnglish;
    private EditText wordTurkish;
    private EditText sampleSentence;
    private Button btnAddWord;

    private fileOperations fo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.addword_fragment, container, false);

        wordEnglish = (EditText) view.findViewById(R.id.wordEnglish);
        wordTurkish = (EditText) view.findViewById(R.id.wordTurkish);
        sampleSentence = (EditText) view.findViewById(R.id.sampleSentence);
        btnAddWord = (Button) view.findViewById(R.id.btnAddWord);

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                //File Operations Class
                fo = new fileOperations();

                try{
                    //True means: Adds to the word.txt file.
                    fo.Save(wordEnglish.getText().toString().trim().toLowerCase() + "-"
                            + wordTurkish.getText().toString().trim().toLowerCase() + "!" + sampleSentence.getText().toString().trim().toLowerCase(),true);

                    Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                    //Clear EditText
                    wordTurkish.getText().clear();
                    wordEnglish.getText().clear();
                    sampleSentence.getText().clear();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
