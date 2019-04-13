package com.example.wordsapp;

import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class fileOperations {

    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordApp";
    private File dir;
    private File file;

    public void Save(String data,boolean whichOne)
    {
        dir = new File(path);
        dir.mkdirs();

        if(whichOne == true)
        {
            file = new File(path + "/Words.txt");
        }
        else
        {
            file = new File(path + "/grammerNotes.txt");
        }

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file,true);
            fos.write(data.getBytes());
            fos.write("\n".getBytes());
            fos.close();
        }
        catch (Exception e) {e.printStackTrace();}
    }

    public TreeMap<String,String> Load(boolean whichOne)
    {
        dir = new File(path);
        dir.mkdirs();
        if(whichOne == true)
        {
            file = new File(path + "/Words.txt");
        }
        else
        {
            file = new File(path + "/grammerNotes.txt");
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            fis.getChannel().position(0);
            TreeMap<String,String> array = new TreeMap<String,String>();

            String line;
            try {
                while ((line = br.readLine()) != null) {
                    // Transfer data which is read in each line to TreeMap<>.
                    array.put(line.split("-")[0] , line.split("-")[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return array;

        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }
    }
}
