package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IOHelper {

    public static String ReadFileString(Context context){
        try {
            FileInputStream fis = context.openFileInput("petcare.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            String json = sb.toString();
            return json;

        }catch (FileNotFoundException e){
            //
            return "FileNotFoundException";
        }catch (IOException e){
            //
            return "IOEXCEPTION E";
        }
    }

    public static void WriteJson(Context context, String filename, String objStr){
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(objStr.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
