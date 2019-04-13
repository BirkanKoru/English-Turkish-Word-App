package com.example.wordsapp;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeMap;

public class grammerNotesFragment extends Fragment {

    private ListView listGrammerNotes;
    private FloatingActionButton btnAddNoteFragment;

    private TreeMap<String, String> dictionary=new TreeMap<String,String>();
    private fileOperations fo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.grammernotes_fragment, container, false);

        //File Operations Class
        fo = new fileOperations();

        //Read grammerNotes.txt file (False means: grammerNotes.txt file)
        dictionary = fo.Load(false);

        if (dictionary != null) {
            listGrammerNotes = (ListView) view.findViewById(R.id.listNotes);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    new ArrayList<String>(dictionary.keySet())
            );

            listGrammerNotes.setAdapter(adapter);
            listGrammerNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String word = parent.getItemAtPosition(position).toString();
                    String defn = dictionary.get(word);

                    //Information Box
                    AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                    alert.setTitle("Information");
                    alert.setMessage(defn);
                    alert.show();
                }
            });
        }

        btnAddNoteFragment = (FloatingActionButton) view.findViewById(R.id.btnaddNoteFragment);
        btnAddNoteFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAddNoteClick();
            }
        });

        return view;
    }

    public void btnAddNoteClick()
    {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentById(R.id.content_frame);
        if(prev != null){
            ft.remove(prev);
        }
        ft.add(R.id.content_frame, new addNoteFragment()).commit();
    }

}
