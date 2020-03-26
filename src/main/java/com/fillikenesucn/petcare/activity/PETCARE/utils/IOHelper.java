package com.fillikenesucn.petcare.activity.PETCARE.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOHelper {

    public static Boolean CheckFile(Context context){
        try {
            FileInputStream fis = context.openFileInput("petcare.txt");
            if (fis != null) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e){
            return false;
        }
    }

    public static Boolean InitJson(Context context, String filename, String objStr){
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(objStr.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

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
            json = json.replace("\\", "");
            json = json.substring(1);
            json = json.substring(0, json.length() - 1);
            return json;

        }catch (FileNotFoundException e){
            //
            return "FileNotFoundException";
        }catch (IOException e){
            //
            return "IOException";
        }
    }

    public static String WriteJson(Context context, String filename, String objStr){
        FileOutputStream outputStream;
        try {
            // Get JSON from APP
            String sxml = ReadFileString(context);
            // Parse the string to json-array
            JSONArray json = new JSONArray(sxml);
            // Parse the object-string to json-object
            JSONObject object = new JSONObject(objStr);
            // Check if the EPC is used by an active pet
            if (CheckActiveEPC(json, object)){
                // Add the object to array
                json.put(object);
                // Parse json-array to string again
                Gson gson = new Gson();

                // Write the new json
                outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(gson.toJson(json.toString()).getBytes());
                outputStream.close();
                Log.d("DORAT", "DONE");
            } else {
                Toast.makeText(context, "TAG OCUPADO", Toast.LENGTH_SHORT).show();
            }

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "ERROR AL REGISTRAR", Toast.LENGTH_SHORT).show();
            return "";
        }
    }

    public static Boolean CheckActiveEPC(JSONArray json, JSONObject object){
        try {
            for (int i = 0; i < json.length(); i++) {
                JSONObject curr = json.getJSONObject(i);
                if (curr.getString("EPC").equals(object.getString("EPC")) && object.getBoolean("active")) return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DORAT", e.toString());
            return false;
        }
    }

}
