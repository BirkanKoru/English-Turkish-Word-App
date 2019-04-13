package com.example.wordsapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class listWordsFragment extends Fragment {

    private ListView listWords;
    private FloatingActionButton btnaddWordFragment;

    private TreeMap<String, String> dictionary = new TreeMap<String,String>();
    private fileOperations fo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.listwords_fragment, container, false);

        //File Operations Class
        fo = new fileOperations();

        //Read Words.txt file (True means: Words.txt file)
        dictionary = fo.Load(true);

        if (dictionary != null)
        {
            listWords = (ListView) view.findViewById(R.id.listWords);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    new ArrayList<String>(dictionary.keySet())
            );

            listWords.setAdapter(adapter);
            listWords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String word = parent.getItemAtPosition(position).toString();
                    String defn = dictionary.get(word);

                    //Meaning Box
                    AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                    String[] defnn = defn.split("!");
                    alert.setMessage("meaning: "+ defnn[0] +"\n\n" +
                                     "sample sentence:\n"
                                     + defnn[1]);
                    alert.show();
                }
            });
        }

        btnaddWordFragment = (FloatingActionButton) view.findViewById(R.id.btnaddWordFragment);
        btnaddWordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddClick();
            }
        });

        return view;
    }

    public void btnAddClick(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentById(R.id.content_frame);
        if(prev != null){
            ft.remove(prev);
        }
        ft.add(R.id.content_frame, new addWordFragment()).commit();
    }
}
