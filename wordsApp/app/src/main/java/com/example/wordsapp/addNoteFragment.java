package com.example.wordsapp;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class addNoteFragment extends Fragment {

    private EditText title;
    private EditText note;
    private Button btnAddNote;

    private fileOperations fo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.addnote_fragment, container, false);

        title = (EditText) view.findViewById(R.id.wordTitle);
        note = (EditText) view.findViewById(R.id.wordNote);
        btnAddNote = (Button) view.findViewById(R.id.btnAddNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //File Operations Class
                fo = new fileOperations();

                try{
                    //False means: Adds to the grammerNotes.txt file.
                    fo.Save(title.getText().toString().trim().toLowerCase() + "-"
                            + note.getText().toString().trim().toLowerCase(), false);

                    Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                    //Clear EditText
                    title.getText().clear();
                    note.getText().clear();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return view;
    }
}
